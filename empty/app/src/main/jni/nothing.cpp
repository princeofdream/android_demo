//
// Created by NUSW on 2016/1/18.
//


#include "nothing.h"

extern "C"

JNIEXPORT jstring JNICALL
Java_com_bookcl_empty_MainActivity_stringFromCPP(
        JNIEnv *env,
        jobject /* this */) {
    char str_ret[1024];
    memset(str_ret,0x0,sizeof(str_ret));
    sprintf(str_ret,"%s","[Native-CPP] By JamesL");
    return env->NewStringUTF("[Native-CPP] By JamesL");
}

