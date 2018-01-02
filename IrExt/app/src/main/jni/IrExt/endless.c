//
// Created by NUSW on 2016/1/18.
//

#include "endless.h"
#include "../usb/libusb.h"


JNIEXPORT jstring JNICALL Java_com_vstech_irext_MainActivity_stringFromJNI
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"[Native-C] By JamesL");
}
JNIEXPORT jstring JNICALL Java_com_vstech_irext_MainActivity_sethid
        (JNIEnv *env, jobject obj,jint fd){
    libusb_open(NULL,NULL);
    return (*env)->NewStringUTF(env,"[Native-C] By JamesL");
}