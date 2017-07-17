#include <jni.h>
#include <string.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hgsoft_usbcam_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    char* hello = "Hello from C++";
    return env->NewStringUTF(hello);
}
