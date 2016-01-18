//
// Created by NUSW on 2016/1/18.
//

#ifndef TEST01_ENDLESS_H
#define TEST01_ENDLESS_H

#include <jni.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_abc_bookcl_test01_TSUnit_UnitMain_getStringFormC
        (JNIEnv *env, jobject obj);

#ifdef __cplusplus
}
#endif

#endif //TEST01_ENDLESS_H
