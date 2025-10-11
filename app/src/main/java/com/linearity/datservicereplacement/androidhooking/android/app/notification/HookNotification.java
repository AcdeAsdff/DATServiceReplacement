package com.linearity.datservicereplacement.androidhooking.android.app.notification;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.ParceledListSliceGen;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;
import static com.linearity.datservicereplacement.StartHook.registerServiceHook_map;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.app.NotificationManager;
import android.content.Context;

import com.linearity.utils.FakeClass.java.util.EmptyArrays;

import java.util.HashSet;
import java.util.Set;

public class HookNotification {
    public static void doHook(){
        hookPublishBinderService();
    }
    public static void hookPublishBinderService(){
        registerServiceHook_map.put(Context.NOTIFICATION_SERVICE,c -> {
            hookINotificationManager(c);
            return null;
        });
    }
    public static final NotificationManager.Policy policy = new NotificationManager.Policy((1<<9)-1,0,0);
    //TODO:Randomize if needed

    public static final Set<Class<?>> INotificationManagerHookedPool = new HashSet<>();
    public static void hookINotificationManager(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,INotificationManagerHookedPool)){return;}
        hookAllMethodsWithCache_Auto(hookClass,"cancelAllNotifications",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearData",null);
        hookAllMethodsWithCache_Auto(hookClass,"enqueueTextToast",true);
        hookAllMethodsWithCache_Auto(hookClass,"enqueueToast",true);
        hookAllMethodsWithCache_Auto(hookClass,"cancelToast",null);
        hookAllMethodsWithCache_Auto(hookClass,"finishToken",null);
//        hookAllMethodsWithCache_Auto(hookClass,"enqueueNotificationWithTag",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelNotificationWithTag",null);
        hookAllMethodsWithCache_Auto(hookClass,"isInCall",true);
        hookAllMethodsWithCache_Auto(hookClass,"setShowBadge",null);
        hookAllMethodsWithCache_Auto(hookClass,"canShowBadge",true);
        hookAllMethodsWithCache_Auto(hookClass,"hasSentValidMsg",true);
        hookAllMethodsWithCache_Auto(hookClass,"isInInvalidMsgState",true);
        hookAllMethodsWithCache_Auto(hookClass,"hasUserDemotedInvalidMsgApp",true);
        hookAllMethodsWithCache_Auto(hookClass,"setInvalidMsgAppDemoted",null);
        hookAllMethodsWithCache_Auto(hookClass,"hasSentValidBubble",true);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationsEnabledForPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationsEnabledWithImportanceLockForPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"areNotificationsEnabledForPackage",true);
        hookAllMethodsWithCache_Auto(hookClass,"areNotificationsEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"getPackageImportance",0);
        hookAllMethodsWithCache_Auto(hookClass,"isImportanceLocked",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAllowedAssistantAdjustments",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"shouldHideSilentStatusIcons",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setHideSilentStatusIcons",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBubblesAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"areBubblesAllowed",true);
        hookAllMethodsWithCache_Auto(hookClass,"areBubblesEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"getBubblePreferenceForPackage",0);
//        hookAllMethodsWithCache_Auto(hookClass,"createNotificationChannelGroups",null);
//        hookAllMethodsWithCache_Auto(hookClass,"createNotificationChannels",null);
//        hookAllMethodsWithCache_Auto(hookClass,"createNotificationChannelsForPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"getConversations",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getConversationsForPackage",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelGroupsForPackage",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelGroupForPackage",null);//NotificationChannelGroup
        hookAllMethodsWithCache_Auto(hookClass,"getPopulatedNotificationChannelGroupForPackage",null);//NotificationChannelGroup
        hookAllMethodsWithCache_Auto(hookClass,"getRecentBlockedNotificationChannelGroupsForPackage",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"updateNotificationChannelGroupForPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateNotificationChannelForPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"unlockNotificationChannel",null);
        hookAllMethodsWithCache_Auto(hookClass,"unlockAllNotificationChannels",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannel",null,getSystemChecker_PackageNameAt(0));//NotificationChannel
        hookAllMethodsWithCache_Auto(hookClass,"getConversationNotificationChannel",null,getSystemChecker_PackageNameAt(0));//NotificationChannel
//        hookAllMethodsWithCache_Auto(hookClass,"createConversationNotificationChannelForPackage",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelForPackage",null);//NotificationChannel
        hookAllMethodsWithCache_Auto(hookClass,"deleteNotificationChannel",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannels",ParceledListSliceGen,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelsForPackage",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getNumNotificationChannelsForPackage",0);
        hookAllMethodsWithCache_Auto(hookClass,"getDeletedChannelCount",0);
        hookAllMethodsWithCache_Auto(hookClass,"getBlockedChannelCount",0);
        hookAllMethodsWithCache_Auto(hookClass,"deleteNotificationChannelGroup",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelGroup",null);//NotificationChannelGroup
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelGroups",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"onlyHasDefaultChannel",true);
        hookAllMethodsWithCache_Auto(hookClass,"areChannelsBypassingDnd",true);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelsBypassingDnd",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesBypassingDnd",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"isPackagePaused",true);
        hookAllMethodsWithCache_Auto(hookClass,"deleteNotificationHistoryItem",null);
        hookAllMethodsWithCache_Auto(hookClass,"isPermissionFixed",true);
        hookAllMethodsWithCache_Auto(hookClass,"silenceNotificationSound",null);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNotifications",null,getSystemChecker_PackageNameAt(0));//StatusBarNotification[]
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNotificationsWithAttribution",null,getSystemChecker_PackageNameAt(0));//StatusBarNotification[]
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalNotifications",null,getSystemChecker_PackageNameAt(0));//StatusBarNotification[]
        hookAllMethodsWithCache_Auto(hookClass,"getHistoricalNotificationsWithAttribution",null,getSystemChecker_PackageNameAt(0));//StatusBarNotification[]
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationHistory",null,getSystemChecker_PackageNameAt(0));//NotificationHistory
//        hookAllMethodsWithCache_Auto(hookClass,"registerListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelNotificationFromListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"cancelNotificationsFromListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"snoozeNotificationUntilContextFromListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"snoozeNotificationUntilFromListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestBindListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestUnbindListener",null);
//        hookAllMethodsWithCache_Auto(hookClass,"requestUnbindListenerComponent",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestBindProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestUnbindProvider",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationsShownFromListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveNotificationsFromListener",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getSnoozedNotificationsFromListener",ParceledListSliceGen);
//        hookAllMethodsWithCache_Auto(hookClass,"clearRequestedListenerHints",null);
        hookAllMethodsWithCache_Auto(hookClass,"requestHintsFromListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getHintsFromListener",0);
        hookAllMethodsWithCache_Auto(hookClass,"getHintsFromListenerNoToken",0);
        hookAllMethodsWithCache_Auto(hookClass,"requestInterruptionFilterFromListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getInterruptionFilterFromListener",0);
//        hookAllMethodsWithCache_Auto(hookClass,"setOnNotificationPostedTrimFromListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"setInterruptionFilter",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateNotificationChannelGroupFromPrivilegedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateNotificationChannelFromPrivilegedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelsFromPrivilegedListener",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationChannelGroupsFromPrivilegedListener",ParceledListSliceGen);
        hookAllMethodsWithCache_Auto(hookClass,"applyEnqueuedAdjustmentFromAssistant",null);
        hookAllMethodsWithCache_Auto(hookClass,"applyAdjustmentFromAssistant",null);
        hookAllMethodsWithCache_Auto(hookClass,"applyAdjustmentsFromAssistant",null);
        hookAllMethodsWithCache_Auto(hookClass,"unsnoozeNotificationFromAssistant",null);
        hookAllMethodsWithCache_Auto(hookClass,"unsnoozeNotificationFromSystemListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"getEffectsSuppressor",null);//ComponentName
        hookAllMethodsWithCache_Auto(hookClass,"matchesCallFilter",true);
        hookAllMethodsWithCache_Auto(hookClass,"cleanUpCallersAfter",null);
        hookAllMethodsWithCache_Auto(hookClass,"isSystemConditionProviderEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isNotificationListenerAccessGranted",true);
        hookAllMethodsWithCache_Auto(hookClass,"isNotificationListenerAccessGrantedForUser",true);
        hookAllMethodsWithCache_Auto(hookClass,"isNotificationAssistantAccessGranted",true);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationListenerAccessGranted",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationAssistantAccessGranted",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationListenerAccessGrantedForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationAssistantAccessGrantedForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"getEnabledNotificationListenerPackages",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getEnabledNotificationListeners",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getAllowedNotificationAssistantForUser", EmptyArrays.EMPTY_COMPONENT_NAME);//ComponentName
        hookAllMethodsWithCache_Auto(hookClass,"getAllowedNotificationAssistant", EmptyArrays.EMPTY_COMPONENT_NAME);//ComponentName
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultNotificationAssistant", EmptyArrays.EMPTY_COMPONENT_NAME);//ComponentName
        hookAllMethodsWithCache_Auto(hookClass,"setNASMigrationDoneAndResetDefault",null);
        hookAllMethodsWithCache_Auto(hookClass,"hasEnabledNotificationListener",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getZenMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"getZenModeConfig",null);//ZenModeConfig
        hookAllMethodsWithCache_Auto(hookClass,"getConsolidatedNotificationPolicy",policy);//NotificationManager.Policy
        hookAllMethodsWithCache_Auto(hookClass,"setZenMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"notifyConditions",null);
        hookAllMethodsWithCache_Auto(hookClass,"isNotificationPolicyAccessGranted",true);
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationPolicy",policy);//NotificationManager.Policy
//        hookAllMethodsWithCache_Auto(hookClass,"setNotificationPolicy",null);
        hookAllMethodsWithCache_Auto(hookClass,"isNotificationPolicyAccessGrantedForPackage",true);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationPolicyAccessGranted",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationPolicyAccessGrantedForUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultZenPolicy",null);//ZenPolicy
        hookAllMethodsWithCache_Auto(hookClass,"getAutomaticZenRule",null);//AutomaticZenRule
        hookAllMethodsWithCache_Auto(hookClass,"getAutomaticZenRules",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"getZenRules",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"addAutomaticZenRule",null);//String
        hookAllMethodsWithCache_Auto(hookClass,"updateAutomaticZenRule",true);
        hookAllMethodsWithCache_Auto(hookClass,"removeAutomaticZenRule",true);
        hookAllMethodsWithCache_Auto(hookClass,"removeAutomaticZenRules",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getRuleInstanceCount",0);
        hookAllMethodsWithCache_Auto(hookClass,"getAutomaticZenRuleState",0);
        hookAllMethodsWithCache_Auto(hookClass,"setAutomaticZenRuleState",null);
        hookAllMethodsWithCache_Auto(hookClass,"setManualZenRuleDeviceEffects",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBackupPayload", EmptyArrays.EMPTY_BYTE_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"applyRestore",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAppActiveNotifications",ParceledListSliceGen,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setNotificationDelegate",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getNotificationDelegate",null,getSystemChecker_PackageNameAt(0));//String
        hookAllMethodsWithCache_Auto(hookClass,"canNotifyAsPackage",true,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"canUseFullScreenIntent",true,getSystemChecker_AttributionSourceAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"setPrivateNotificationsAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPrivateNotificationsAllowed",true);
        hookAllMethodsWithCache_Auto(hookClass,"pullStats",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getListenerFilter",null);//NotificationListenerFilter
//        hookAllMethodsWithCache_Auto(hookClass,"setListenerFilter",null);
        hookAllMethodsWithCache_Auto(hookClass,"migrateNotificationFilter",null);
        hookAllMethodsWithCache_Auto(hookClass,"setToastRateLimitingEnabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"registerCallNotificationEventListener",null,getSystemChecker_PackageNameAt(0));
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterCallNotificationEventListener",null,getSystemChecker_PackageNameAt(0));
    }

}
