LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := xyz
LOCAL_SRC_FILES := endless.c

## for third part so libraries
#LOCAL_C_INCLUDES := $(LOCAL_PATH)/include $(LOCAL_PATH)/../jniLibs/include
#LOCAL_LDFLAGS := -L$(LOCAL_PATH)/../jniLibs/armeabi-v7a  -lx264

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)

LOCAL_MODULE    := abc
LOCAL_SRC_FILES := nothing.cpp

include $(BUILD_SHARED_LIBRARY)

