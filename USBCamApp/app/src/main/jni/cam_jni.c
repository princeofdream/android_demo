#include <jni.h>
#include <string.h>
#include "cam_jni.h"


JNIEXPORT jstring JNICALL Java_com_hgsoft_usbcam_MainActivity_stringFromJNI
        (JNIEnv *env, jobject obj){
    char* get_str = test_char();
    return (*env)->NewStringUTF(env,get_str);
//    return (*env)->NewStringUTF(env,"[Native-C] By JamesL");
}
