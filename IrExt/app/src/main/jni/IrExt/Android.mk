LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)


LOCAL_SRC_FILES:= \
	 endless.c

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../usb

LOCAL_LDLIBS := -llog
LOCAL_SHARED_LIBRARIES := libusbnok

LOCAL_MODULE := irext


include $(BUILD_SHARED_LIBRARY)