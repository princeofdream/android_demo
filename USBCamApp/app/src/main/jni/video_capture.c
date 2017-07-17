#include <asm/types.h>          /* for videodev2.h */
#include <fcntl.h>              /* low-level i/o */
#include <unistd.h>
#include <errno.h>
#include <malloc.h>
#include <sys/stat.h>
#include <sys/types.h>
//#include <sys/time.h>
#include <time.h>
#include <sys/mman.h>
#include <sys/ioctl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <linux/videodev2.h>
#include <dirent.h>
#include "video_capture.h"
#include "h264encoder.h"

#define CLEAR(x) memset (&(x), 0, sizeof (x))

typedef unsigned char uint8_t;

char h264_file_name[100] ;
FILE *h264_fp;
FILE *bmp_fp;
uint8_t *h264_buf;

//char yuv_file_name[100] = "usbCam.yuv\0";
//FILE *yuv_fp;

unsigned int n_buffers = 0;
DIR *dirp;
Encoder en;

int cnt = 0;

void errno_exit(const char *s) {
	fprintf(stderr, "%s error %d, %s\n", s, errno, strerror(errno));
	exit(EXIT_FAILURE);
}

int xioctl(int fd, int request, void *arg) {
	int r = 0;
	do {
		r = ioctl(fd, request, arg);
	} while (-1 == r && EINTR == errno);

	return r;
}

void open_camera(struct camera *cam) {
	struct stat st;

	if (-1 == stat(cam->device_name, &st)) {
		fprintf(stderr, "Cannot identify '%s': %d, %s\n", cam->device_name,
				errno, strerror(errno));
		exit(EXIT_FAILURE);
	}

	if (!S_ISCHR(st.st_mode)) {
		fprintf(stderr, "%s is no device\n", cam->device_name);
		exit(EXIT_FAILURE);
	}

	cam->fd = open(cam->device_name, O_RDWR, 0); //  | O_NONBLOCK

	if (-1 == cam->fd) {
		fprintf(stderr, "Cannot open '%s': %d, %s\n", cam->device_name, errno,
				strerror(errno));
		exit(EXIT_FAILURE);
	}
}

void close_camera(struct camera *cam) {
	if (-1 == close(cam->fd))
		errno_exit("close");

	cam->fd = -1;
}

char* get_h264_file_name(int isphoto){
	time_t rawtime;
	struct tm * timeinfo;
	time ( &rawtime );
	timeinfo = localtime ( &rawtime );
	if(isphoto) strftime(h264_file_name,100,"./photo/%Y_%m_%d-%I_%M_%S.jpeg",timeinfo);
	else strftime(h264_file_name,100,"./video/%Y_%m_%d-%I_%M_%S.h264",timeinfo);
	printf("new file name :%s \n",h264_file_name);
	return h264_file_name;
}

void init_file() {
	h264_fp = fopen(get_h264_file_name(0), "wa+");
	//yuv_fp = fopen(yuv_file_name, "wa+");
}

void init_photo() {
	bmp_fp = fopen(get_h264_file_name(1), "wa+");
	//yuv_fp = fopen(yuv_file_name, "wa+");
}


void close_file() {
	fclose(h264_fp);
	//fclose(yuv_fp);
}

void init_encoder(struct camera *cam) {
	compress_begin(&en, cam->width, cam->height,cam->frame_rate);
	h264_buf = (uint8_t *) malloc(
			sizeof(uint8_t) * cam->width * cam->height * 3); // 设置缓冲区
}

void close_encoder() {
	compress_end(&en);
	if(h264_buf != NULL)
	free(h264_buf);
}

void encode_frame(uint8_t *yuv_frame, size_t yuv_length) {
	int h264_length = 0;

	//这里有一个问题，通过测试发现前6帧都是0，所以这里我跳过了为0的帧
	if (yuv_frame[0] == '\0')
		return;
	h264_length = compress_frame(&en, -1, yuv_frame, h264_buf);
	//printf("h264_length = %d\n",h264_length);
	if (h264_length > 0) {
		//写h264文件
		fwrite(h264_buf, h264_length, 1, h264_fp);
	}

	//写yuv文件
	//fwrite(yuv_frame, yuv_length, 1, yuv_fp);
}

int read_and_encode_frame(struct camera *cam) {
	struct v4l2_buffer buf;

	//printf("in read_frame\n");

	CLEAR(buf);

	buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	buf.memory = V4L2_MEMORY_MMAP;

	//this operator below will change buf.index and (0 <= buf.index <= 3)
	if (-1 == xioctl(cam->fd, VIDIOC_DQBUF, &buf)) {
		switch (errno) {
		case EAGAIN:
			return 0;
		case EIO:
			/* Could ignore EIO, see spec. */
			/* fall through */
		default:
			errno_exit("VIDIOC_DQBUF");
		}
	}
	fwrite(cam->buffers[buf.index].start, buf.length, 1, h264_fp);
	//encode_frame(cam->buffers[buf.index].start, buf.length);

	if (-1 == xioctl(cam->fd, VIDIOC_QBUF, &buf))
		errno_exit("VIDIOC_QBUF");

	return 1;
}

int get_and_save_photo(struct camera *cam) {
	unsigned int width = cam->width;
	unsigned int height = cam->height;
	unsigned char rgb[width*height*3];
	printf("get_and_save_photo rgb width=%d height=%d length=%d \n",width,height,sizeof(rgb));
	struct v4l2_buffer buf;

	//printf("in read_frame\n");
	CLEAR(rgb);
	CLEAR(buf);

	buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	buf.memory = V4L2_MEMORY_MMAP;

	//this operator below will change buf.index and (0 <= buf.index <= 3)
	if (-1 == xioctl(cam->fd, VIDIOC_DQBUF, &buf)) {
		switch (errno) {
		case EAGAIN:
			return 0;
		case EIO:
			/* Could ignore EIO, see spec. */
			/* fall through */
		default:
			errno_exit("VIDIOC_DQBUF");
		}
	}
	//yuvtorgb0(cam->buffers[buf.index].start , rgb,width,height);
	//savebmp(rgb,bmp_fp, width, height );
	 fwrite(cam->buffers[buf.index].start,buf.length,1,bmp_fp);

	if (-1 == xioctl(cam->fd, VIDIOC_QBUF, &buf))
		errno_exit("VIDIOC_QBUF");

	return 1;
}



void start_capturing(struct camera *cam) {
	unsigned int i;
	enum v4l2_buf_type type;

	for (i = 0; i < n_buffers; ++i) {
		struct v4l2_buffer buf;

		CLEAR(buf);

		buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		buf.memory = V4L2_MEMORY_MMAP;
		buf.index = i;

		if (-1 == xioctl(cam->fd, VIDIOC_QBUF, &buf))
			errno_exit("VIDIOC_QBUF");
	}

	type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	if (-1 == xioctl(cam->fd, VIDIOC_STREAMON, &type))
		errno_exit("VIDIOC_STREAMON");

}

void stop_capturing(struct camera *cam) {
	enum v4l2_buf_type type;
	type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	if (-1 == xioctl(cam->fd, VIDIOC_STREAMOFF, &type)){
		errno_exit("VIDIOC_STREAMOFF");
	}

}
void uninit_camera(struct camera *cam) {
	unsigned int i;
	
	for (i = 0; i < n_buffers; ++i)
		if (-1 == munmap(cam->buffers[i].start, cam->buffers[i].length))
			errno_exit("munmap");

	free(cam->buffers);
}

void init_mmap(struct camera *cam) {
	struct v4l2_requestbuffers req;

	CLEAR(req);

	req.count = 4;
	req.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	req.memory = V4L2_MEMORY_MMAP;

	//分配内存
	if (-1 == xioctl(cam->fd, VIDIOC_REQBUFS, &req)) {
		if (EINVAL == errno) {
			fprintf(stderr, "%s does not support "
					"memory mapping\n", cam->device_name);
			exit(EXIT_FAILURE);
		} else {
			errno_exit("VIDIOC_REQBUFS");
		}
	}

	if (req.count < 2) {
		fprintf(stderr, "Insufficient buffer memory on %s\n", cam->device_name);
		exit(EXIT_FAILURE);
	}

	cam->buffers = calloc(req.count, sizeof(*(cam->buffers)));

	if (!cam->buffers) {
		fprintf(stderr, "Out of memory\n");
		exit(EXIT_FAILURE);
	}

	for (n_buffers = 0; n_buffers < req.count; ++n_buffers) {
		struct v4l2_buffer buf;

		CLEAR(buf);

		buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
		buf.memory = V4L2_MEMORY_MMAP;
		buf.index = n_buffers;

		//将VIDIOC_REQBUFS中分配的数据缓存转换成物理地址
		if (-1 == xioctl(cam->fd, VIDIOC_QUERYBUF, &buf))
			errno_exit("VIDIOC_QUERYBUF");

		cam->buffers[n_buffers].length = buf.length;
		cam->buffers[n_buffers].start = mmap(NULL /* start anywhere */,
				buf.length, PROT_READ | PROT_WRITE /* required */,
				MAP_SHARED /* recommended */, cam->fd, buf.m.offset);

		if (MAP_FAILED == cam->buffers[n_buffers].start)
			errno_exit("mmap");
	}
}

void init_camera(struct camera *cam) {
	struct v4l2_capability *cap = &(cam->v4l2_cap);
	struct v4l2_cropcap *cropcap = &(cam->v4l2_cropcap);
	struct v4l2_crop *crop = &(cam->crop);
	struct v4l2_format *fmt = &(cam->v4l2_fmt);
	unsigned int min;
	struct v4l2_fmtdesc fmtdesc;  

	fmtdesc.index=0;  
	fmtdesc.type=V4L2_BUF_TYPE_VIDEO_CAPTURE;  
	printf("---------------------\nSupportformat:\n");  
	while(-1 != xioctl(cam->fd,VIDIOC_ENUM_FMT,&fmtdesc)) {  
		printf("\t%d.%s\n",fmtdesc.index+1,fmtdesc.description);  
		fmtdesc.index++;  
	} 
	printf("----------------------\n"); 

	if (-1 == xioctl(cam->fd, VIDIOC_QUERYCAP, cap)) {
		if (EINVAL == errno) {
			fprintf(stderr, "%s is no V4L2 device\n", cam->device_name);
			exit(EXIT_FAILURE);
		} else {
			errno_exit("VIDIOC_QUERYCAP");
		}
	}

	if (!(cap->capabilities & V4L2_CAP_VIDEO_CAPTURE)) {
		fprintf(stderr, "%s is no video capture device\n", cam->device_name);
		exit(EXIT_FAILURE);
	}

	if (!(cap->capabilities & V4L2_CAP_STREAMING)) {
		fprintf(stderr, "%s does not support streaming i/o\n",
				cam->device_name);
		exit(EXIT_FAILURE);
	}

	//#ifdef DEBUG_CAM
	printf("\nVIDOOC_QUERYCAP\n");
	printf("the camera driver is %s\n", cap->driver);
	printf("the camera card is %s\n", cap->card);
	printf("the camera bus info is %s\n", cap->bus_info);
	printf("the version is %d\n", cap->version);
	//#endif
	/* Select video input, video standard and tune here. */

	CLEAR(*cropcap);

	cropcap->type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	crop->c.width = cam->width;
	crop->c.height = cam->height;
	crop->c.left = 0;
	crop->c.top = 0;
	crop->type = V4L2_BUF_TYPE_VIDEO_CAPTURE;

	CLEAR(*fmt);

	fmt->type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
	fmt->fmt.pix.width = cam->width;
	fmt->fmt.pix.height = cam->height;
	//fmt->fmt.pix.pixelformat = V4L2_PIX_FMT_YUYV; //yuv422
	fmt->fmt.pix.pixelformat = V4L2_PIX_FMT_MJPEG; //
	//  fmt->fmt.pix.pixelformat = V4L2_PIX_FMT_YUV420;  //yuv420 但是我电脑不支持
	fmt->fmt.pix.field = V4L2_FIELD_INTERLACED; //隔行扫描

	if (-1 == xioctl(cam->fd, VIDIOC_S_FMT, fmt))
		errno_exit("VIDIOC_S_FMT");

	/* Note VIDIOC_S_FMT may change width and height. */

	/* Buggy driver paranoia. */
	min = fmt->fmt.pix.width * 2;
	if (fmt->fmt.pix.bytesperline < min)
		fmt->fmt.pix.bytesperline = min;
	min = fmt->fmt.pix.bytesperline * fmt->fmt.pix.height;
	if (fmt->fmt.pix.sizeimage < min)
		fmt->fmt.pix.sizeimage = min;

	init_mmap(cam);

}

void v4l2_getFPS(struct camera *cam){
	printf("in v4l2_getFPS");
	int okCounter = 0;
	long int timeB4 = 0 ;
	long int timeFt = 0 ;
	double usedTime = 0;
	open_camera(cam);
	init_camera(cam);
	start_capturing(cam);
	init_encoder(cam);
	init_file();
	printf("b4 get time");
	timeB4 = getCurrentTime();
	int i=0;
	while(i<130){
		fd_set fds;
		struct timeval tv;
		int r;

		i++;
		FD_ZERO(&fds);
		FD_SET(cam->fd, &fds);

		/* Timeout. */
		tv.tv_sec = 2;
		tv.tv_usec = 0;

		r = select(cam->fd + 1, &fds, NULL, NULL, &tv);

		if (-1 == r) {
			if (EINTR == errno)
				continue;

			errno_exit("select");
		}

		if (0 == r) {
			fprintf(stderr, "select timeout\n");
			exit(EXIT_FAILURE);
		}

		if(1 == read_and_encode_frame(cam)){
			okCounter++;
		}
	}
	timeFt = getCurrentTime();
	usedTime = ((double)(timeFt - timeB4))/1000;
	cam->frame_rate = (int)(okCounter/usedTime);
	printf("v4l2_getFPS:\n okCounter=%d \n usedTime = %lf \n frameRate = %d \n",okCounter,usedTime,cam->frame_rate);
	stop_capturing(cam);
	uninit_camera(cam);
	close_camera(cam);
	close_file();
	close_encoder();
	printf("\n v4l2_getFPS End ! \n");
}


void v4l2_init(struct camera *cam) {
	open_camera(cam);
	printf("open_camera \n");
	init_camera(cam);
	printf("init_camera \n");
	start_capturing(cam);
	printf("start_capturing \n");
	init_encoder(cam);
	printf("init_encoder \n");
	init_file();
	printf("init_file \n");
}

void v4l2_capture(struct camera *cam) {
	open_camera(cam);
	printf("open_camera \n");
	init_camera(cam);
	printf("init_camera \n");
	start_capturing(cam);
	printf("start_capturing \n");
	init_photo();
	printf("init_photo \n");
	get_and_save_photo(cam);
	printf("get_and_save_photo \n");
	stop_capturing(cam);
	printf("stop_capturing \n");
	uninit_camera(cam);
	printf("uninit_camera \n");
	close_camera(cam);
	printf("close_camera \n");
}



void v4l2_close(struct camera *cam) {
	if(cam->fd > 0){
		stop_capturing(cam);
		printf("stop_capturing \n");
		uninit_camera(cam);
		printf("uninit_camera \n");
		close_camera(cam);
		printf("close_camera \n");
		close_file();
		printf("close_file \n");
		close_encoder();
		printf("close_encoder \n");
	}
}

