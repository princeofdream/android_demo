LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE    := usbcam

LOCAL_SRC_FILES := cam_jni.c \
	main.c \
	video_capture.c \
	h264encoder.c

LOCAL_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_LDFLAGS := -L./ -L./jniLibs -L./src/main/jniLibs/armeabi-v7a -L./main/jniLibs/armeabi-v7a -L./jniLibs/armeabi-v7a  -lx264
include $(BUILD_SHARED_LIBRARY)


