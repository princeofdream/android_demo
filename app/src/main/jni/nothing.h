//
// Created by lijin on 2017/6/13.
//

#ifndef EMPTY_ENDLESS_H
#define EMPTY_ENDLESS_H

#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>


#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL
Java_com_bookcl_empty_MainActivity_stringFromCPP(JNIEnv *env, jobject /* this */);

#ifdef __cplusplus
}
#endif


#endif //EMPTY_ENDLESS_H
