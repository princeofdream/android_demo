LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := xyz
LOCAL_SRC_FILES := endless.c

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE    := abc
LOCAL_SRC_FILES := nothing.cpp

include $(BUILD_SHARED_LIBRARY)

