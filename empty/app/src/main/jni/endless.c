//
// Created by NUSW on 2016/1/18.
//

#include "endless.h"


JNIEXPORT jstring JNICALL Java_com_bookcl_empty_MainActivity_stringFromJNI
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"[Native-C] By JamesL");
}
