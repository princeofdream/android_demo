//
// Created by NUSW on 2016/1/18.
//


#include <jni.h>
#include <string.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bookcl_empty_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    //std::string hello = "[Native-CPP] By JamesL";
    //return env->NewStringUTF(hello.c_str());
    return env->NewStringUTF("[Native-CPP] By JamesL with Android.mk");
}

