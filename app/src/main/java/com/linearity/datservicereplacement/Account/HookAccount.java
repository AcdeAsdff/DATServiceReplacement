package com.linearity.datservicereplacement.Account;

import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_ACCOUNT_ARRAY;
import static com.linearity.utils.FakeClass.java.util.EmptyArrays.EMPTY_AUTHENTICATOR_DESCRIPTION_ARRAY;

import android.accounts.Account;

import com.linearity.datservicereplacement.Alarm.HookAlarm;
import com.linearity.utils.ClassHookExecutor;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookAccount {
    public static void doHook(){
        ClassHookExecutor ex_hookIAccountManagerResponse = HookAccount::hookIAccountManagerResponse;
//        classesAndHooks.put("android.accounts.AbstractAccountAuthenticator$Transport", HookAccount::hookIAccountAuthenticator);
        classesAndHooks.put("com.android.server.accounts.AccountManagerService", HookAccount::hookIAccountManager);
//        classesAndHooks.put("android.accounts.AccountManager$AmsTask$Response", ex_hookIAccountManagerResponse);
//        classesAndHooks.put("android.accounts.AccountManager$BaseFutureTask$Response", ex_hookIAccountManagerResponse);
//        classesAndHooks.put("com.android.server.accounts.AccountManagerServiceTest$Response", ex_hookIAccountManagerResponse);
    }

    public static void hookIAccountAuthenticator(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"addAccount",null);
        hookAllMethodsWithCache_Auto(hookClass,"confirmCredentials",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthToken",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthTokenLabel",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateCredentials",null);
        hookAllMethodsWithCache_Auto(hookClass,"editProperties",null);
        hookAllMethodsWithCache_Auto(hookClass,"hasFeatures",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAccountRemovalAllowed",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAccountCredentialsForCloning",null);
        hookAllMethodsWithCache_Auto(hookClass,"addAccountFromCredentials",null);
        hookAllMethodsWithCache_Auto(hookClass,"startAddAccountSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"startUpdateCredentialsSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"finishSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCredentialsUpdateSuggested",null);
    }
    public static void hookIAccountAuthenticatorResponse(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onResult",null);
        hookAllMethodsWithCache_Auto(hookClass,"onRequestContinued",null);
        hookAllMethodsWithCache_Auto(hookClass,"onError",null);
    }
    public static void hookIAccountManager(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"getPassword","");//String
        hookAllMethodsWithCache_Auto(hookClass,"getUserData","");//String
        hookAllMethodsWithCache_Auto(hookClass,"getAuthenticatorTypes",EMPTY_AUTHENTICATOR_DESCRIPTION_ARRAY);//AuthenticatorDescription[]
        hookAllMethodsWithCache_Auto(hookClass,"getAccountsForPackage",EMPTY_ACCOUNT_ARRAY,getSystemChecker_PackageNameAt(0));//Account[]
        hookAllMethodsWithCache_Auto(hookClass,"getAccountsByTypeForPackage",EMPTY_ACCOUNT_ARRAY,getSystemChecker_PackageNameAt(1));//Account[]
        hookAllMethodsWithCache_Auto(hookClass,"getAccountsAsUser",EMPTY_ACCOUNT_ARRAY);//Account[]
        hookAllMethodsWithCache_Auto(hookClass,"hasFeatures",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAccountByTypeAndFeatures",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAccountsByFeatures",null);
        hookAllMethodsWithCache_Auto(hookClass,"addAccountExplicitly",true);
        hookAllMethodsWithCache_Auto(hookClass,"removeAccountAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAccountExplicitly",true);
        hookAllMethodsWithCache_Auto(hookClass,"copyAccountToUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"invalidateAuthToken",null);
        hookAllMethodsWithCache_Auto(hookClass,"peekAuthToken","");//String
        hookAllMethodsWithCache_Auto(hookClass,"setAuthToken",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPassword",null);
        hookAllMethodsWithCache_Auto(hookClass,"clearPassword",null);
        hookAllMethodsWithCache_Auto(hookClass,"setUserData",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateAppPermission",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthToken",null);
        hookAllMethodsWithCache_Auto(hookClass,"addAccount",null);
        hookAllMethodsWithCache_Auto(hookClass,"addAccountAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"updateCredentials",null);
        hookAllMethodsWithCache_Auto(hookClass,"editProperties",null);
        hookAllMethodsWithCache_Auto(hookClass,"confirmCredentialsAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"accountAuthenticated",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAuthTokenLabel",null);
        hookAllMethodsWithCache_Auto(hookClass,"addSharedAccountsFromParentUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"renameAccount",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPreviousName","");//String
        hookAllMethodsWithCache_Auto(hookClass,"startAddAccountSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"startUpdateCredentialsSession",null);
        hookAllMethodsWithCache_Auto(hookClass,"finishSessionAsUser",null);
        hookAllMethodsWithCache_Auto(hookClass,"someUserHasAccount",true);
        hookAllMethodsWithCache_Auto(hookClass,"isCredentialsUpdateSuggested",null);
        hookAllMethodsWithCache_Auto(hookClass,"getPackagesAndVisibilityForAccount",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"addAccountExplicitlyWithVisibility",true);
        hookAllMethodsWithCache_Auto(hookClass,"setAccountVisibility",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAccountVisibility",0,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"getAccountsAndVisibilityForPackage",EMPTY_HASHMAP,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"registerAccountListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAccountListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"hasAccountAccess",true,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"createRequestAccountAccessIntentSenderAsUser",null,getSystemChecker_PackageNameAt(1));//IntentSender
        hookAllMethodsWithCache_Auto(hookClass,"onAccountAccessed",null);
    }
    public static void hookIAccountManagerResponse(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"onResult",null);
        hookAllMethodsWithCache_Auto(hookClass,"onError",null);
    }
}
