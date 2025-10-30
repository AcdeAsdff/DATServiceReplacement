package com.linearity.utils;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import com.google.common.collect.MapMaker;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessListUtils {
    public static final Set<Object> ALL_PROCESS_LISTS = Collections.newSetFromMap(new MapMaker().weakKeys().weakValues().makeMap());
    public static void addProcessList(Object processList){
        ALL_PROCESS_LISTS.add(processList);
    }

}
