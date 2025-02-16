//
// Created by MOKO on 2024/5/23.
//

#include <android/log.h>
#include <dlfcn.h>
#include <string>
#include <jni.h>
#include <cstdio>
#include "sys/system_properties.h"
#include "datutils.h"

//JNIEnv *env = nullptr;
const char* fromChar = "1234567890zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP";

namespace datutils {
    char* getRandomCharPointer(int length){
        char* result = (char *)(malloc(sizeof(char) * (length+1)));
        for (int i=0;i<length;i++){
            result[i] = fromChar[abs(rand()%62)];
        }result[length]='\0';

        return result;
    }

    void setRandomCharPointer(char* charPointer, int length){
        for (int i=0;i<length;i++){
            charPointer[i] = fromChar[abs(rand()%62)];
        }charPointer[length]='\0';

    }

    void setCharPointerFromCharPointer(char* charPointer, int length, const char* fromCharPointer){
        for (int i=0;i<length;i++){
            charPointer[i] = fromCharPointer[i];
        }charPointer[length]='\0';

    }
    void setRandomCharPointerWithInt(char* charPointer, int length){
        for (int i=0;i<length;i++){
            charPointer[i] = fromChar[abs(rand()%10)];
        }charPointer[length]='\0';

    }

    jbyteArray getRandomByteArray(JNIEnv *env,int size){
        jbyte a[size];
        for (int i=0;i<size;i++){
            a[i]=(char)abs(rand()%255);
        }
        jbyteArray ret = env->NewByteArray(size);
        env->SetByteArrayRegion (ret, 0, size, a);
        return ret;
    }
    jboolean* jtrue = static_cast<jboolean *>(calloc(1, sizeof(jboolean)));
    jboolean* jfalse = static_cast<jboolean *>(calloc(1, sizeof(jboolean)));
    jboolean* toJavaBoolean(bool value){
        *jtrue = 1;
        *jfalse = 0;
        return (value?jtrue:jfalse);
    }

    bool strStartWith(const char* target,const char* expectStart){
        unsigned int length = strlen(expectStart);
        if (strlen(target) < length){return false;}
        for (unsigned int i=0;i<length;i++){
            if (target[i] != expectStart[i]){return false;}
        }
        return true;
    }
    bool strEndWith(const char* target,const char* expectEnd){
        unsigned int length = strlen(expectEnd);
        unsigned int targetLength = strlen(target);
        if (targetLength < length){return false;}
        for (unsigned int i=0;i<length;i++){
            if (target[targetLength-length+i] != expectEnd[i]){return false;}
        }
        return true;
    }
    bool strEqual(const char* A,const char* B){
        unsigned int length = strlen(A);
        if (length != strlen(B)){return false;}
        for (unsigned int i=0;i<length;i++){
            if (A[i] != B[i]){return false;}
        }
        return true;
    }
} // datutils