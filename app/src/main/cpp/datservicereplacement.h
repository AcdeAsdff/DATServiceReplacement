//
// Created by MOKO on 2024/11/10.
//

#ifndef DATSERVICEREPLACEMENT_DATSERVICEREPLACEMENT_H
#define DATSERVICEREPLACEMENT_DATSERVICEREPLACEMENT_H
#include <cstdint>
typedef int (*HookFunType)(void *func, void *replace, void **backup);

typedef int (*UnhookFunType)(void *func);

typedef void (*NativeOnModuleLoaded)(const char *name, void *handle);

typedef struct {
    uint32_t version;
    HookFunType hook_func;
    UnhookFunType unhook_func;
} NativeAPIEntries;

typedef NativeOnModuleLoaded (*NativeInit)(const NativeAPIEntries *entries);
#endif //DATSERVICEREPLACEMENT_DATSERVICEREPLACEMENT_H
