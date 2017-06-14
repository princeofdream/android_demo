LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := usbcam
LOCAL_SRC_FILES := cam_jni.cpp

include $(BUILD_SHARED_LIBRARY)


