#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "h264encoder.h"

void compress_begin(Encoder *en, int width, int height,int frameRate) {
	en->param = (x264_param_t *) malloc(sizeof(x264_param_t));
	en->picture = (x264_picture_t *) malloc(sizeof(x264_picture_t));
	x264_param_default(en->param); //set default param

	en->param->i_width = width; //set frame width
	en->param->i_height = height; //set frame height
	en->param->i_csp = X264_CSP_I422; //格式422
	en->param->i_threads = 1;  //并行编码          
	//en->param->i_fps_num = frameRate; //帧率         
	//en->param->i_fps_den = 1;
	en->param->i_keyint_max = 60;//关键帧
	en->param->b_repeat_headers = 1;  // 重复SPS/PPS 放到关键帧前面 
        en->param->b_cabac = 1;//熵编码          


	// en->param->i_log_level = X264_LOG_NONE;

	//rc
	en->param->rc.i_rc_method = X264_RC_ABR; //参数i_rc_method表示码率控制，CQP(恒定质量)，CRF(恒定码率)，ABR(平均码率)
	en->param->rc.i_lookahead = 0; //表示i帧向前缓冲区
	en->param->rc.i_bitrate = 1024 * 3;//rate 为3 kbps
	en->param->rc.i_vbv_max_bitrate=1024 * 3 * 1.2;
 	en->param->rc.f_rf_constant = 25; //质量控制（越小越清）
	en->param->rc.f_rf_constant_max = 45; 

	en->param->i_level_idc=30;//编码复杂度
	
	x264_param_apply_profile(en->param, "high422"); //使用profile "baseline", "main", "high", "high10", "high422", "high444", 0

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

	//序列为YU YV YU YV，一个yuv422帧的长度 width * height * 2 个字节
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
