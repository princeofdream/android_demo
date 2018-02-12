//
// Created by NUSW on 2016/1/18.
//

#include "endless.h"
#include "../usb/libusb.h"
#include "hidapi.h"
#include "../../../../../../../../../Android/sdk/ndk-bundle/platforms/android-19/arch-arm/usr/include/android/log.h"


JNIEXPORT jstring JNICALL Java_com_vstech_irext_MainActivity_stringFromJNI
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"[Native-C] By JamesL");
}
JNIEXPORT jstring JNICALL Java_com_vstech_irext_MainActivity_sethid
        (JNIEnv *env, jobject obj,jint fd){
    //libusb_open(NULL,NULL);
    __android_log_print(ANDROID_LOG_ERROR,"JamesL_JNI","<%s:%d>,get fd: %d",__FUNCTION__,__LINE__,fd);
    test_main(fd);
    return (*env)->NewStringUTF(env,"[Native-C] By JamesL");
}