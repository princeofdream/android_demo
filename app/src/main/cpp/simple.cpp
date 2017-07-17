#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bookcl_empty_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "[Native-CPP] By JamesL";
    return env->NewStringUTF(hello.c_str());
}
