package com.linearity.datservicereplacement.Search;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_COMPONENT_NAME;

import com.linearity.datservicereplacement.Wifi.HookWifiService;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookSearch {


    public static void doHook(){
        classesAndHooks.put("com.android.server.search.SearchManagerService", HookSearch::hookISearchUiManager);
        classesAndHooks.put("com.android.server.search.SearchManagerService", HookSearch::hookISearchUiService);
        classesAndHooks.put("com.android.server.searchui.SearchUiManagerService$SearchUiManagerStub", HookSearch::hookISearchUiManager);


    }
    //TODO:Randomize
    public static void hookISearchManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getSearchableInfo",null);//SearchableInfo
        hookAllMethodsWithCache_Auto(hookClass,"getSearchablesInGlobalSearch",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getGlobalSearchActivities",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getGlobalSearchActivity",EMPTY_COMPONENT_NAME);//ComponentName
        hookAllMethodsWithCache_Auto(hookClass,"getWebSearchActivity",EMPTY_COMPONENT_NAME);//ComponentName
        hookAllMethodsWithCache_Auto(hookClass,"launchAssist",null);
    }
    public static void hookISearchUiManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"createSearchSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"query",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerEmptyQueryResultUpdateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterEmptyQueryResultUpdateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"destroySearchSession",null);
    }
    public static void hookISearchUiService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onCreateSearchSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"onQuery",null);
        hookAllMethodsWithCache_Auto(hookClass,"onNotifyEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"onRegisterEmptyQueryResultUpdateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"onUnregisterEmptyQueryResultUpdateCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"onDestroy",null);
    }

}
