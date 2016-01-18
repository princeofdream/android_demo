//
// Created by NUSW on 2016/1/18.
//

#include "endless.h"


JNIEXPORT jstring JNICALL Java_abc_bookcl_test01_TSUnit_UnitMain_getStringFormC
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"Test By JamesL");
}
