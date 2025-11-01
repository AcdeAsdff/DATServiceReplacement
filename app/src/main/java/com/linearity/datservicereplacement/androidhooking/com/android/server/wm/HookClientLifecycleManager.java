package com.linearity.datservicereplacement.androidhooking.com.android.server.wm;

import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgsByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.ReturnIfNonSys.isSystemIApplicationThread;
import static com.linearity.datservicereplacement.ReturnIfNonSys.noSystemChecker;
import static com.linearity.datservicereplacement.ReturnIfNonSys.showAfter;
import static com.linearity.datservicereplacement.ReturnIfNonSys.uidOfIApplicationThread;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIActivityManager.modifyConfiguration;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIActivityManager.modifyConfigurationWithUid;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIActivityManager.modifyWindowConfiguration;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.isSystemTask;
import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;

import android.app.IApplicationThread;
import android.app.WindowConfiguration;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.PauseActivityItem;
import android.app.servertransaction.ResumeActivityItem;
import android.app.servertransaction.StopActivityItem;
import android.app.servertransaction.TopResumedActivityChangeItem;
import android.content.res.Configuration;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.linearity.utils.SimpleExecutor;
import com.linearity.utils.SimpleExecutorWithMode;
import com.linearity.utils.SystemAppChecker;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookClientLifecycleManager {



    public static void doHook(){
        classesAndHooks.put("com.android.server.wm.ClientLifecycleManager",HookClientLifecycleManager::hookClientLifecycleManager);
    }

    private static void hookClientLifecycleManager(Class<?> hookClass){
        SystemAppChecker isIApplicationThreadOrClientTransactionItemSystem = param -> {
            IApplicationThread thread = findArgByClassInArgs(param.args,IApplicationThread.class);
            if (thread != null){
                if (!isSystemIApplicationThread(thread)){
                    return false;
                }
            }
            ClientTransaction transaction = findArgByClassInArgs(param.args,ClientTransaction.class);
            if (transaction != null){
                if (!isSystemIApplicationThread(transaction.getClient())){
                    return false;
                }
            }
            return true;
        };
        SimpleExecutor checkTransaction = param -> {

            List<ClientTransactionItem> items = findArgsByClassInArgs(param.args,ClientTransactionItem.class);
            for (ClientTransactionItem item:items){
                if (shouldCancelTransaction(item)){
                    param.setResult(null);
                    return;
                }
                modifyClientTransactionItem(item,getUIDFromArgsForTransaction(param));
            }
        };
        hookAllMethodsWithCache_Auto(hookClass,"scheduleTransactionAndLifecycleItems",checkTransaction,isIApplicationThreadOrClientTransactionItemSystem);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleTransactionItemNow",checkTransaction,isIApplicationThreadOrClientTransactionItemSystem);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleTransaction",checkTransaction,isIApplicationThreadOrClientTransactionItemSystem);
        hookAllMethodsWithCache_Auto(hookClass,"scheduleTransactionItem",checkTransaction,isIApplicationThreadOrClientTransactionItemSystem);
//        ClientTransaction
    }

    private static boolean shouldCancelTransaction(ClientTransactionItem item){

//        if (item instanceof PauseActivityItem /*  || item instanceof StopActivityItem || item instanceof ResumeActivityItem*/){
//            return true;
//        }

        return false;
    }
    private static final Multimap<String,String> configurationFieldsMap = MultimapBuilder.SetMultimapBuilder.hashKeys().hashSetValues().build();
    private static final Multimap<String,String> windowConfigurationFieldsMap = MultimapBuilder.SetMultimapBuilder.hashKeys().hashSetValues().build();
    private static void modifyClientTransactionItem(ClientTransactionItem item,int uid){

        try {
            String itemClassName = item.getClass().getName();
            {
                if (!configurationFieldsMap.containsKey(itemClassName)) {
                    Class<?> itemClass = item.getClass();
                    for (Field f : itemClass.getDeclaredFields()) {
                        if (Objects.equals(f.getType(), Configuration.class)) {
                            configurationFieldsMap.put(itemClassName, f.getName());
                        }
                    }
                    if (configurationFieldsMap.get(itemClassName).isEmpty()){
                        configurationFieldsMap.put(itemClassName,"");
                    }
                }
                for (String configurationFieldName : configurationFieldsMap.get(itemClassName)) {
                    if (configurationFieldName.isEmpty()){continue;}
                    Configuration configuration = (Configuration) XposedHelpers.getObjectField(item, configurationFieldName);
                    modifyConfigurationWithUid(configuration,uid);
                }
            }
            {
                if (!windowConfigurationFieldsMap.containsKey(itemClassName)) {
                    Class<?> itemClass = item.getClass();
                    for (Field f : itemClass.getDeclaredFields()) {
                        if (Objects.equals(f.getType(), WindowConfiguration.class)) {
                            windowConfigurationFieldsMap.put(itemClassName, f.getName());
                        }
                    }
                    if (windowConfigurationFieldsMap.get(itemClassName).isEmpty()){
                        windowConfigurationFieldsMap.put(itemClassName,"");
                    }
                }

                for (String configurationFieldName : windowConfigurationFieldsMap.get(itemClassName)) {
                    if (configurationFieldName.isEmpty()){continue;}
                    WindowConfiguration windowConfiguration = (WindowConfiguration) XposedHelpers.getObjectField(item, configurationFieldName);
                    modifyWindowConfiguration(windowConfiguration);
                }
            }
        }catch (Exception e){
            LoggerLog(e);
        }
    }
    private static int getUIDFromArgsForTransaction(XC_MethodHook.MethodHookParam param){
        IApplicationThread thread = findArgByClassInArgs(param.args,IApplicationThread.class);
        if (thread != null){
            int uid = uidOfIApplicationThread(thread);
            if (!(uid == Integer.MIN_VALUE)){
                return uid;
            }
        }
        ClientTransaction transaction = findArgByClassInArgs(param.args,ClientTransaction.class);
        if (transaction != null){
            int uid = uidOfIApplicationThread(transaction.getClient());
            if (!(uid == Integer.MIN_VALUE)){
                return uid;
            }
        }
        return Integer.MIN_VALUE;
    }

}
