
/*
 * =====================================================================================
 *
 *       Filename:  rknative.cpp
 *
 *    Description:
 *
 *        Version:  1.0
 *        Created:  2021年09月28日 11时07分28秒
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  James Lee (JamesL), princeofdream@outlook.com
 *   Organization:  BookCL
 *
 * =====================================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <jni.h>
// #include <log/log_main.h>
#include <unistd.h>
#include <fcntl.h>

#include <android/log.h>
static const char *TAG="serial_port";
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG, fmt, ##args)


extern "C" JNIEXPORT jstring JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    char *hello = "Hello from C++";
    int fd;
    char val[32];

    LOGE("================== [%s:%d]: byJames export [%d]",
         __FUNCTION__, __LINE__, 92);

    memset(val, 0x0, sizeof(val));
    sprintf(val, "%d", 92);
    fd=open("/sys/class/gpio/export", O_WRONLY);
    if (fd < 0) {
        return env->NewStringUTF(hello);
    }

    write(fd, val, strlen(val));
    close(fd);

    return env->NewStringUTF(hello);
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_getStringFormC
        (JNIEnv *env, jobject obj) {
    return env->NewStringUTF("Test By JamesL");
}


extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_exportGPIOPin
        (JNIEnv *env, jobject obj, jint pin)
{
    int fd;
    char val[32];

    LOGE("================== [%s:%d]: byJames export [%d]",
         __FUNCTION__, __LINE__, pin);

    memset(val, 0x0, sizeof(val));
    sprintf(val, "%d", pin);
    fd=open("/sys/class/gpio/export", O_WRONLY);
    if (fd < 0) {
        return 1;
    }

    write(fd, val, strlen(val));
    close(fd);

    return 0;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_checkpath
        (JNIEnv *env, jobject obj, jstring path_str)
{
    int ret;
    const char *str_val = env->GetStringUTFChars( path_str, JNI_FALSE);

    LOGE("================== [%s:%d]: byJames export [%s]",
         __FUNCTION__, __LINE__, str_val);

    if (strlen(str_val) == 0) {
        return -1;
    }
    ret = access(str_val, F_OK);

    return ret;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_unexportGPIOPin
        (JNIEnv *env, jobject obj, jint pin)
{
    int fd;
    char val[32];

    LOGE("================== [%s:%d]: byJames unexport [%d]",
         __FUNCTION__, __LINE__, pin);

    memset(val, 0x0, sizeof(val));
    sprintf(val, "%d", pin);
    fd=open("/sys/class/gpio/unexport", O_WRONLY);
    if (fd < 0) {
        return 1;
    }

    write(fd, val, strlen(val));
    close(fd);

    return 0;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_setGPIOValue
        (JNIEnv *env, jobject obj, jint pin, jint value)
{
    int fd;
    char val[32];
    char gpio_path[256];

    LOGE("================== [%s:%d]: byJames set pin [%d: %d]",
         __FUNCTION__, __LINE__, pin, value);

    memset(val, 0x0, sizeof(val));
    memset(gpio_path, 0x0, sizeof(gpio_path));

    sprintf(val, "%d", value);
    sprintf(gpio_path, "/sys/class/gpio/gpio%d/value", pin);


    fd=open(gpio_path, O_WRONLY);
    if (fd < 0) {
        return 1;
    }

    write(fd, val, strlen(val));
    close(fd);

    return 0;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_getGPIOValue
        (JNIEnv *env, jobject obj, jint pin)
{
    int fd;
    char val[32];
    char gpio_path[256];

    LOGE("================== [%s:%d]: byJames set pin [%d]",
         __FUNCTION__, __LINE__, pin);

    memset(val, 0x0, sizeof(val));
    memset(gpio_path, 0x0, sizeof(gpio_path));

    sprintf(gpio_path, "/sys/class/gpio/gpio%d/value", pin);


    fd=open(gpio_path, O_RDONLY);
    if (fd < 0) {
        return 1;
    }

    read(fd, val, sizeof(val));
    close(fd);

    LOGE("================== [%s:%d]: byJames get pin [%d] val <%d>",
         __FUNCTION__, __LINE__, pin, atoi(val));

    return atoi(val);
}

extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_setGPIODirection
        (JNIEnv *env, jobject obj, jint pin, jint direction)
{
    int fd;
    char val[32];
    char gpio_path[256];

    LOGE("================== [%s:%d]: byJames set pin [%d] output [%d]",
         __FUNCTION__, __LINE__, pin, direction);

    memset(val, 0x0, sizeof(val));
    memset(gpio_path, 0x0, sizeof(gpio_path));

    if (direction == 0) {
        sprintf(val, "%s", "in");
    } else {
        sprintf(val, "%s", "out");
    }
    sprintf(gpio_path, "/sys/class/gpio/gpio%d/direction", pin);


    fd=open(gpio_path, O_WRONLY);
    if (fd < 0) {
        return 1;
    }

    write(fd, val, strlen(val));
    close(fd);

    return atoi(val);
}

extern "C" JNIEXPORT jint JNICALL
Java_com_rkdemo_gpiodemo_HardwareControler_getGPIODirection
        (JNIEnv *env, jobject obj, jint pin)
{
    int fd;
    char val[32];
    char gpio_path[256];

    LOGE("================== [%s:%d]: byJames get pin [%d] output",
         __FUNCTION__, __LINE__, pin);

    memset(val, 0x0, sizeof(val));
    memset(gpio_path, 0x0, sizeof(gpio_path));

    sprintf(gpio_path, "/sys/class/gpio/gpio%d/direction", pin);


    fd=open(gpio_path, O_RDONLY);
    if (fd < 0) {
        return -1;
    }

    read(fd, val, sizeof(val));
    close(fd);

    LOGE("================== [%s:%d]: byJames get pin [%d] output <%s>",
         __FUNCTION__, __LINE__, pin, val);

    if (strncasecmp(val, "in", strlen("in")) == 0) {
        LOGE("=====: byJames get pin [%d] output <in>",__FUNCTION__, __LINE__, pin);
        return 0;
    } else if (strncasecmp(val, "out", strlen("out")) == 0) {
        LOGE("=====: byJames get pin [%d] output <out>",__FUNCTION__, __LINE__, pin);
        return 1;
    }

    return -1;
}

