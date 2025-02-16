package com.linearity.datservicereplacement.NetworkManagement;

import static com.linearity.datservicereplacement.ReturnIfNonSys.fakeInterfaceConfigurationIfNonSys_executor;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;

import com.linearity.utils.FakeClass.java.util.EmptyArrays;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * TIM cannot connect to network,TODO:find reasons.
 */
public class HookNetworkManagementService {



    public static void doHook(ClassLoader classLoader){
        Class<?> hookClass = XposedHelpers.findClassIfExists("com.android.server.NetworkManagementService", classLoader);
        if (hookClass != null){
            hookINetworkManagementService(hookClass);
        }
    }
    public static void hookINetworkManagementService(Class<?> hookClass){
//        hookAllMethodsWithCache_Auto(hookClass,"registerObserver",null);
//        hookAllMethodsWithCache_Auto(hookClass,"unregisterObserver",null);
        hookAllMethodsWithCache_Auto(hookClass,"listInterfaces", EmptyArrays.EMPTY_STRING_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getInterfaceConfig",fakeInterfaceConfigurationIfNonSys_executor);
        hookAllMethodsWithCache_Auto(hookClass,"setInterfaceConfig",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearInterfaceAddresses",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setInterfaceDown",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setInterfaceUp",null);
        hookAllMethodsWithCache_Auto(hookClass,"setInterfaceIpv6PrivacyExtensions",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableIpv6",null);
        hookAllMethodsWithCache_Auto(hookClass,"enableIpv6",null);
        hookAllMethodsWithCache_Auto(hookClass,"setIPv6AddrGenMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"shutdown",null);
        hookAllMethodsWithCache_Auto(hookClass,"getIpForwardingEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setIpForwardingEnabled",null);
//        hookAllMethodsWithCache_Auto(hookClass,"startTethering",null);
//        hookAllMethodsWithCache_Auto(hookClass,"stopTethering",null);
//        hookAllMethodsWithCache_Auto(hookClass,"isTetheringStarted",true);
//        hookAllMethodsWithCache_Auto(hookClass,"tetherInterface",null);
//        hookAllMethodsWithCache_Auto(hookClass,"untetherInterface",null);
        hookAllMethodsWithCache_Auto(hookClass,"listTetheredInterfaces","");
        hookAllMethodsWithCache_Auto(hookClass,"enableNat",null);
        hookAllMethodsWithCache_Auto(hookClass,"disableNat",null);
//        hookAllMethodsWithCache_Auto(hookClass,"setInterfaceQuota",null);
//        hookAllMethodsWithCache_Auto(hookClass,"removeInterfaceQuota",null);
        hookAllMethodsWithCache_Auto(hookClass,"setInterfaceAlert",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeInterfaceAlert",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUidOnMeteredNetworkDenylist",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUidOnMeteredNetworkAllowlist",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDataSaverModeEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setUidCleartextNetworkPolicy",null);
        hookAllMethodsWithCache_Auto(hookClass,"isBandwidthControlEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setFirewallEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isFirewallEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setFirewallUidRule",null);
        hookAllMethodsWithCache_Auto(hookClass,"setFirewallUidRules",null);
        hookAllMethodsWithCache_Auto(hookClass,"setFirewallChainEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"allowProtect",null);
        hookAllMethodsWithCache_Auto(hookClass,"denyProtect",null);
        hookAllMethodsWithCache_Auto(hookClass,"isNetworkRestricted",true);
    }

}
