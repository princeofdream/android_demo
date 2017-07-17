#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "h264encoder.h"

typedef int LONG;  
typedef unsigned int DWORD;  
typedef unsigned short WORD;  


void compress_begin(Encoder *en, int width, int height,int frameRate) {
	en->param = (x264_param_t *) malloc(sizeof(x264_param_t));
	en->picture = (x264_picture_t *) malloc(sizeof(x264_picture_t));
	x264_param_default(en->param); //set default param

	en->param->i_width = width; //set frame width
	en->param->i_height = height; //set frame height
	en->param->i_csp = X264_CSP_I422; //æ ¼å¼422
	en->param->i_threads = 1;  //å¹¶è¡Œç¼–ç           
	en->param->i_fps_num = frameRate; //å¸§ç‡         
	en->param->i_fps_den = 1;
	en->param->i_keyint_max = frameRate * 2;//å…³é”®å¸§
	en->param->b_repeat_headers = 1;  // é‡å¤SPS/PPS æ”¾åˆ°å…³é”®å¸§å‰é¢ 
    en->param->b_cabac = 1;//ç†µç¼–ç           
	en->param->i_log_level = X264_LOG_NONE;

	//rc
	en->param->rc.i_rc_method = X264_RC_ABR; //å‚æ•°i_rc_methodè¡¨ç¤ºç ç‡æ§åˆ¶ï¼ŒCQP(æ’å®šè´¨é‡)ï¼ŒCRF(æ’å®šç ç‡)ï¼ŒABR(å¹³å‡ç ç‡)
	en->param->rc.i_lookahead = 0; //è¡¨ç¤ºiå¸§å‘å‰ç¼“å†²åŒº
	en->param->rc.i_bitrate = 1024 * 3;//rate ä¸º3 kbps
	en->param->rc.i_vbv_max_bitrate=1024 * 3 * 1.2;
 	en->param->rc.f_rf_constant = 25; //è´¨é‡æ§åˆ¶ï¼ˆè¶Šå°è¶Šæ¸…ï¼‰
	en->param->rc.f_rf_constant_max = 45; 

	en->param->i_level_idc=30;//ç¼–ç å¤æ‚åº¦
	
	x264_param_apply_profile(en->param, "high422"); //ä½¿ç”¨profile "baseline", "main", "high", "high10", "high422", "high444", 0

	if ((en->handle = x264_encoder_open(en->param)) == 0) {
		return;
	}
	/* Create a new pic */
	x264_picture_alloc(en->picture, X264_CSP_I422, en->param->i_width,
			en->param->i_height);
	en->picture->img.i_csp = X264_CSP_I422;
	en->picture->img.i_plane = 3;
}

int compress_frame(Encoder *en, int type, uint8_t *in, uint8_t *out) {
	x264_picture_t pic_out;
	int nNal = -1;
	int result = 0;
	int i = 0;
	uint8_t *p_out = out;

	char *y = en->picture->img.plane[0];
	char *u = en->picture->img.plane[1];
	char *v = en->picture->img.plane[2];

	int is_y = 1, is_u = 1;
	int y_index = 0, u_index = 0, v_index = 0;

	int yuv422_length = 2 * en->param->i_width * en->param->i_height;

	//åºåˆ—ä¸ºYU YV YU YVï¼Œä¸€ä¸ªyuv422å¸§çš„é•¿åº¦ width * height * 2 ä¸ªå­—èŠ‚
	for (i = 0; i < yuv422_length; ++i) {
		if (is_y) {
			*(y + y_index) = *(in + i);
			++y_index;
			is_y = 0;
		} else {
			if (is_u) {
				*(u + u_index) = *(in + i);
				++u_index;
				is_u = 0;
			} else {
				*(v + v_index) = *(in + i);
				++v_index;
				is_u = 1;
			}
			is_y = 1;
		}
	}

	switch (type) {
	case 0:
		en->picture->i_type = X264_TYPE_P;
		break;
	case 1:
		en->picture->i_type = X264_TYPE_IDR;
		break;
	case 2:
		en->picture->i_type = X264_TYPE_I;
		break;
	default:
		en->picture->i_type = X264_TYPE_AUTO;
		break;
	}

	en->picture->i_pts += 1;

	
	if (x264_encoder_encode(en->handle, &(en->nal), &nNal, en->picture,
			&pic_out) < 0) {
		return -1;
	}

	for (i = 0; i < nNal; i++) {
		memcpy(p_out, en->nal[i].p_payload, en->nal[i].i_payload);
		p_out += en->nal[i].i_payload;
		result += en->nal[i].i_payload;
	}

	return result;
}

void compress_end(Encoder *en) {
	if (en->picture) {
		x264_picture_clean(en->picture);
		free(en->picture);
		en->picture = 0;
	}
	if (en->param) {
		free(en->param);
		en->param = 0;
	}
	if (en->handle) {
		x264_encoder_close(en->handle);
	}
	//free(en);
}
     
long int getCurrentTime()    
{    
   struct timeval tv;    
   gettimeofday(&tv,NULL);    
   return tv.tv_sec * 1000 + tv.tv_usec / 1000;    
} 

int sign3 = 0;

int yuvtorgb0(unsigned char *yuv, unsigned char *rgb, unsigned int width, unsigned int height)
{
     unsigned int in, out;
     int y0, u, y1, v;
     unsigned int pixel24;
     unsigned char *pixel = (unsigned char *)&pixel24;
     unsigned int size = width*height*2;

     for(in = 0, out = 0; in < size; in += 4, out += 6)
     {
          y0 = yuv[in+0];
          u  = yuv[in+1];
          y1 = yuv[in+2];
          v  = yuv[in+3];

		  sign3 = 1;
          pixel24 = yuvtorgb(y0, u, v);
          rgb[out+0] = pixel[0];    //for QT
          rgb[out+1] = pixel[1];
          rgb[out+2] = pixel[2];
          //rgb[out+0] = pixel[2];  //for iplimage
          //rgb[out+1] = pixel[1];
          //rgb[out+2] = pixel[0];

          //sign3 = true;
          pixel24 = yuvtorgb(y1, u, v);
          rgb[out+3] = pixel[0];
          rgb[out+4] = pixel[1];
          rgb[out+5] = pixel[2];
          //rgb[out+3] = pixel[2];
          //rgb[out+4] = pixel[1];
          //rgb[out+5] = pixel[0];
     }
     return 0;
}

int yuvtorgb(int y, int u, int v)
{
     unsigned int pixel24 = 0;
     unsigned char *pixel = (unsigned char *)&pixel24;
     int r, g, b;
     static long int ruv, guv, buv;

     if(sign3)
     {
         sign3 = 0;
         ruv = 1159*(v-128);
         guv = 380*(u-128) + 813*(v-128);
         buv = 2018*(u-128);
     }

     r = (1164*(y-16) + ruv) / 1000;
     g = (1164*(y-16) - guv) / 1000;
     b = (1164*(y-16) + buv) / 1000;

     if(r > 255) r = 255;
     if(g > 255) g = 255;
     if(b > 255) b = 255;
     if(r < 0) r = 0;
     if(g < 0) g = 0;
     if(b < 0) b = 0;

     pixel[0] = r;
     pixel[1] = g;
     pixel[2] = b;

     return pixel24;
}

  
typedef struct {  
        WORD    bfType;  
        DWORD   bfSize;  
        WORD    bfReserved1;  
        WORD    bfReserved2;  
        DWORD   bfOffBits;  
} BMPFILEHEADER_T;  
  
typedef struct{  
        DWORD      biSize;  
        LONG       biWidth;  
        LONG       biHeight;  
        WORD       biPlanes;  
        WORD       biBitCount;  
        DWORD      biCompression;  
        DWORD      biSizeImage;  
        LONG       biXPelsPerMeter;  
        LONG       biYPelsPerMeter;  
        DWORD      biClrUsed;  
        DWORD      biClrImportant;  
} BMPINFOHEADER_T;  
  
void savebmp(unsigned char * pdata,FILE *bmp_file, int width, int height )  
{      //·Ö±ğÎªrgbÊı¾İ£¬Òª±£´æµÄbmpÎÄ¼şÃû£¬Í¼Æ¬³¤¿í  
       int size = width*height*3*sizeof(unsigned char); // Ã¿¸öÏñËØµã3¸ö×Ö½Ú  
       // Î»Í¼µÚÒ»²¿·Ö£¬ÎÄ¼şĞÅÏ¢  
       BMPFILEHEADER_T bfh;  
       bfh.bfType = (WORD)0x4d42;  //bm  
       bfh.bfSize = size  // data size  
              + 14 // first section size  
              + 40 // second section size  
              ;  
       bfh.bfReserved1 = 0; // reserved  
       bfh.bfReserved2 = 0; // reserved  
       bfh.bfOffBits = (DWORD)0x36;
//ÕæÕıµÄÊı¾İµÄÎ»ÖÃ  
  
       // Î»Í¼µÚ¶ş²¿·Ö£¬Êı¾İĞÅÏ¢  
       BMPINFOHEADER_T bih;  
       bih.biSize = 40;  
       bih.biWidth = width;  
       bih.biHeight = -height;//BMP Í¼Æ¬´Ó×îºóÒ»¸öµã¿ªÊ¼É¨Ãè£¬ÏÔÊ¾Ê±Í¼Æ¬ÊÇµ¹×ÅµÄ£¬ËùÒÔÓÃ-height£¬ÕâÑùÍ¼Æ¬¾ÍÕıÁË  
       bih.biPlanes = 1;//Îª1£¬²»ÓÃ¸Ä  
       bih.biBitCount = 24;  
       bih.biCompression = 0;//²»Ñ¹Ëõ  
       bih.biSizeImage = size;  
       bih.biXPelsPerMeter = 2835 ;//ÏñËØÃ¿Ã×  
       bih.biYPelsPerMeter = 2835 ;  
       bih.biClrUsed = 0;//ÒÑÓÃ¹ıµÄÑÕÉ«£¬24Î»µÄÎª0  
       bih.biClrImportant = 0;//Ã¿¸öÏñËØ¶¼ÖØÒª  
       if( !bmp_file ) return;  
       fwrite(&bfh.bfType, sizeof(bfh.bfType), 1,  bmp_file );
	   fwrite(&bfh.bfSize, sizeof(bfh.bfSize), 1,  bmp_file );
	   fwrite(&bfh.bfReserved1, sizeof(bfh.bfReserved1), 1,  bmp_file );
       fwrite(&bfh.bfReserved2, sizeof(bfh.bfReserved2), 1, bmp_file);  
       fwrite(&bfh.bfOffBits, sizeof(bfh.bfOffBits), 1, bmp_file);
	   fwrite(&bih.biSize, sizeof(bih.biSize), 1, bmp_file);
	   fwrite(&bih.biWidth, sizeof(bih.biWidth), 1, bmp_file);
	   fwrite(&bih.biHeight, sizeof(bih.biHeight), 1, bmp_file);
	   fwrite(&bih.biPlanes, sizeof(bih.biPlanes), 1, bmp_file);
	   fwrite(&bih.biBitCount, sizeof(bih.biBitCount), 1, bmp_file);
	   fwrite(&bih.biCompression, sizeof(bih.biCompression), 1, bmp_file);
	   fwrite(&bih.biSizeImage, sizeof(bih.biSizeImage), 1, bmp_file);
	   fwrite(&bih.biXPelsPerMeter, sizeof(bih.biXPelsPerMeter), 1, bmp_file);
	   fwrite(&bih.biYPelsPerMeter, sizeof(bih.biYPelsPerMeter), 1, bmp_file);
	   fwrite(&bih.biClrUsed, sizeof(bih.biClrUsed), 1, bmp_file);
	   fwrite(&bih.biClrImportant, sizeof(bih.biClrImportant), 1, bmp_file);
       //fwrite( &bih, 40,1,bmp_file );  
       fwrite(pdata,size,1,bmp_file);
	   fclose( bmp_file );  
}

