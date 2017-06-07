TOP_DIR := $(shell pwd)
APP = $(TOP_DIR)/bin/camera_h264encode

# CC = /home/lijin/Environment/toolchain/arm-linux-androideabi/bin/arm-linux-androideabi-gcc
CC = gcc
CFLAGS = -g
LIBS = -lx264 -lm -ldl
LIBS += -lpthread
DEP_LIBS = -L$(TOP_DIR)/lib
HEADER =
OBJS = main.o video_capture.o h264encoder.o

all:  $(OBJS)
	$(CC) -g -o $(APP) $(OBJS) $(LIBS) $(DEP_LIBS)

clean:
	rm -f *.o a.out $(APP) core *~
