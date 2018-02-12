LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)  


LOCAL_SRC_FILES:= \
	 core.c \
	 descriptor.c \
	 io.c \
	 sync.c \
	 os/linux_usbfs.c \
	 os/threads_posix.c \
	 jinterface.c
			
LOCAL_LDLIBS := -llog

LOCAL_MODULE := usbnok 


include $(BUILD_SHARED_LIBRARY)  
