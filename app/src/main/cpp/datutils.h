//
// Created by MOKO on 2024/5/23.
//

#ifndef DEVICEADDRESSTWEAKER_DATUTILS_H
#define DEVICEADDRESSTWEAKER_DATUTILS_H

#include <jni.h>
#include <android/log.h>

#define  DATUTILS_TAG    "[linearity-datsr-Native]"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,DATUTILS_TAG,__VA_ARGS__)

namespace datutils {
    char* getRandomCharPointer(int length);
    void setRandomCharPointer(char* charPointer, int length);
    void setCharPointerFromCharPointer(char* charPointer, int length, const char* fromCharPointer);
    void setRandomCharPointerWithInt(char* charPointer, int length);
    bool strStartWith(const char* target,const char* expectStart);
    bool strEndWith(const char* target,const char* expectEnd);
    bool strEqual(const char* A,const char* B);
    jbyteArray getRandomByteArray(JNIEnv *env,int size);
    jboolean* toJavaBoolean(bool value);

} // datutils

#endif //DEVICEADDRESSTWEAKER_DATUTILS_H
