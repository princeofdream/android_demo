LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := xyz
LOCAL_SRC_FILES := endless.cpp

include $(BUILD_SHARED_LIBRARY)