#include <jni.h>
#include <stdio.h>
#include <BasicUsageEnvironment.hh>
#include <GroupsockHelper.hh>
#include <DynamicRTSPServer.hh>
#include <version.hh>
#include <liveMedia.hh>
#include "com_example_live555test2_MainActivity.h"
#include <string.h>
#include <android/log.h>
#define LOG_TAG "myRTSPServer"
#define LOGW(a )  __android_log_write(ANDROID_LOG_WARN,LOG_TAG,a)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

UsageEnvironment* env;
Boolean reuseFirstSource = False;

static void announceStream(RTSPServer* rtspServer, ServerMediaSession* sms,
		char const* streamName, char const* inputFileName); // fwd

extern "C" JNIEXPORT jstring JNICALL Java_com_example_live555test2_MainActivity_RtspServer(
		JNIEnv *env1, jclass js) {
	TaskScheduler* scheduler = BasicTaskScheduler::createNew();
	env = BasicUsageEnvironment::createNew(*scheduler);

	UserAuthenticationDatabase* authDB = NULL;

	RTSPServer* rtspServer = RTSPServer::createNew(*env, 8554, authDB);
	if (rtspServer == NULL) {
		LOGI("Failed to create RTSP server:%s", env->getResultMsg());
		exit(1);
	} else {
		LOGI("create RTSP server successful in 8554!");
	}

	char const* descriptionString =
			"Session streamed by \"testOnDemandRTSPServer\"";
	// A H.264 video elementary stream:
	{
		char const* streamName = "h264test";
		char const* inputFileName = "/sdcard/test.264";
		ServerMediaSession* sms = ServerMediaSession::createNew(*env,
				streamName, streamName, descriptionString);
		sms->addSubsession(
				H264VideoFileServerMediaSubsession::createNew(*env,
						inputFileName, reuseFirstSource));
		rtspServer->addServerMediaSession(sms);

		announceStream(rtspServer, sms, streamName, inputFileName);
	}

	{
		char const* streamName = "ts";
		char const* inputFileName = "/sdcard/H264_720p.ts";
		char const* indexFileName = "test.tsx";
		ServerMediaSession* sms = ServerMediaSession::createNew(*env,
				streamName, streamName, descriptionString);
		sms->addSubsession(
				MPEG2TransportFileServerMediaSubsession::createNew(*env,
						inputFileName, indexFileName, reuseFirstSource));
		rtspServer->addServerMediaSession(sms);

		announceStream(rtspServer, sms, streamName, inputFileName);
	}

	if (rtspServer->setUpTunnelingOverHTTP(80)
			|| rtspServer->setUpTunnelingOverHTTP(8000)
			|| rtspServer->setUpTunnelingOverHTTP(8080)) {
		LOGI("we use port-->%d", rtspServer->httpServerPortNum());
	} else {
		LOGI("RTSP-over-HTTP tunneling is not available.");
	}
	env->taskScheduler().doEventLoop();

	return 0;
}

static void announceStream(RTSPServer* rtspServer, ServerMediaSession* sms,
		char const* streamName, char const* inputFileName) {
	char* url = rtspServer->rtspURL(sms);
	char buf[255] = { 0 };
	UsageEnvironment& env = rtspServer->envir();
	sprintf(buf,
			"%s-->stream, from the file-->%s,Play this stream using the URL:%s",
			streamName, inputFileName, url);
	LOGW(buf);
	delete[] url;
}


