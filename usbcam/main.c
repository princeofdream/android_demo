#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <errno.h>
#include <malloc.h>
#include "video_capture.h"

#include <sys/select.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/select.h>

struct camera *cam,*cam1;
pthread_t mythread;
int isEndEncode = 0;
int isWaitEncode = 0;


void capture_encode_thread(void) {
	printf("thread created \n");
	int count = 1;
	for (;;) {
		//printf("\n\n-->this is the %dth frame\n", count);
		if (isEndEncode) //end Encode
				{
			printf("exit from thread \n");
			break;
		}
		if (isWaitEncode) {//short time wait !!
			continue;
		}
		cam1->frame_number = count;
		fd_set fds;
		struct timeval tv;
		int r;

		FD_ZERO(&fds);
		FD_SET(cam1->fd, &fds);

		/* Timeout. */
		tv.tv_sec = 2;
		tv.tv_usec = 0;

		r = select(cam1->fd + 1, &fds, NULL, NULL, &tv);

		if (-1 == r) {
			if (EINTR == errno)
				continue;

			errno_exit("select");
		}

		if (0 == r) {
			fprintf(stderr, "select timeout\n");
			exit(EXIT_FAILURE);
		}

		if (read_and_encode_frame(cam1) != 1) {
			fprintf(stderr, "read_fram fail in thread\n");
			break;
		}
	}
}

int main(int argc, char **argv) {
	int num = 0;
	int isEndMain = 0;
	cam = (struct camera *) malloc(sizeof(struct camera));
	if (!cam) {
		printf("malloc camera failure!\n");
		exit(1);
	}
	cam->device_name = "/dev/video0";
	cam->buffers = NULL;
	cam->width = 640;
	cam->height = 480;
	cam->frame_rate = 25;
	cam->display_depth = 5; /* RGB24 */

	cam1 = (struct camera *) malloc(sizeof(struct camera));
	if (!cam1) {
		printf("malloc camera1 failure!\n");
		exit(1);
	}
	cam1->device_name = "/dev/video1";
	cam1->buffers = NULL;
	cam1->width = 640;
	cam1->height = 480;
	cam1->frame_rate = 25;
	cam1->display_depth = 5; /* RGB24 */
	
    while(1){
		printf("0.End 1:testFPS 2:startRecord 3.stopRecord 4.switchFile 5.takePicture \n Enter a number: ");
    	scanf("%d",&num);
		//printf("selected num:%d \n",num);
		switch(num){
			case 1:{//testFPS
				v4l2_getFPS(cam1);
				break;
			}
			case 2:{//startRecord
				isEndEncode = 0;
				v4l2_init(cam1);
				if (0 != pthread_create(&mythread, NULL, (void *) capture_encode_thread, NULL)) {
					fprintf(stderr, "thread create fail\n");
				}
				break;
			}
			case 3:{//stopRecord
				isEndEncode = 1;
				sleep(1);
				v4l2_close(cam1);
				break;
			}
			case 4:{//switchFile
				isWaitEncode = 1;
				close_file();
				init_file();
				isWaitEncode = 0;
				break;
			}
			case 5:{//takePicture
				v4l2_capture(cam);
				break;
			}
			default:{
				isEndEncode = 1;
				sleep(1);
				v4l2_close(cam);
				v4l2_close(cam1);
				free(cam);
				free(cam1);
				isEndMain = 1;
				break;
			}
		}
		if(isEndMain) break;
		
    }
	printf("-----------end program------------\n");
	return 0;

	
}
