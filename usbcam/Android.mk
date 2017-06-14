LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE        := libx264
LOCAL_SRC_FILES     := lib/libx264.a
include $(BUILD_STATIC_LIBRARY)

# include $(CLEAR_VARS)
# LOCAL_MODULE        := libx264
# LOCAL_SRC_FILES     := libs/libx264.so
# LOCAL_MODULE_SUFFIX := .so
# LOCAL_MODULE_PATH   := $(PRODUCT_OUT)/system/lib
# LOCAL_MODULE_CLASS  := SHARED_LIBRARIES
# include $(BUILD_PREBUILT)



include $(CLEAR_VARS)

LOCAL_MODULE    := usbcam
LOCAL_SRC_FILES := cam_jni.cpp \
	main.c video_capture.c h264encoder.c

LOCAL_C_INCLUDES := $(LOCAL_PATH)/include

LOCAL_STATIC_LIBRARIES := libx264

LOCAL_LDLIBS := -lx264
# LOCAL_SHARE_LIBRARIES := libx264


include $(BUILD_SHARED_LIBRARY)


