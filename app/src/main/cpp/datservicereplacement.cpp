// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("datservicereplacement");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("datservicereplacement")
//      }
//    }


#include <jni.h>
#include <string>
#include "datservicereplacement.h"
#include "datutils.h"

static HookFunType hook_func = nullptr;
JNIEnv *env = nullptr;


void on_library_loaded(const char *name, void *handle) {

    if (name == nullptr){ return;}
    // hooks on `libtarget.so`
//    void* target = nullptr;
//    std::string namestr(name);
    LOGD("[libraryLoad]%s", name);
}
void scan_maps(){
    FILE* popenResult = popen("cat /proc/self/maps","r");

    if (popenResult == nullptr) {
        LOGD("Failed to run command:cat\n");
        return;
    }

    char* line;
    size_t len;
    while ((line = fgetln(popenResult,&len)) != nullptr) {
        LOGD("%s", line);
    }
    if (!feof(popenResult)){
        LOGD("Failed to run command:cat\n");
    }
    pclose(popenResult);
}


extern "C" [[gnu::visibility("default")]] [[gnu::used]]
jint JNI_OnLoad(JavaVM *jvm, void*) {
    jvm->GetEnv((void **)&env, JNI_VERSION_1_6);
    return JNI_VERSION_1_6;
}

extern "C" [[gnu::visibility("default")]] [[gnu::used]]
NativeOnModuleLoaded native_init(const NativeAPIEntries *entries) {
    hook_func = entries->hook_func;
    LOGD("inited");
    scan_maps();
    return on_library_loaded;
}