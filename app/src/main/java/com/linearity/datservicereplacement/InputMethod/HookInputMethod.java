package com.linearity.datservicereplacement.InputMethod;

import static com.linearity.datservicereplacement.PackageManager.PackageManagerUtils.getPackageName;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_ReturnObjIfNonSys;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_executeIfNonSys;
import static com.linearity.datservicereplacement.SomeClasses.InputMethodSubtypeArrayClass;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.utils.SimpleExecutor.MODE_AFTER;
import static com.linearity.utils.SimpleExecutor.MODE_BEFORE;

import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.SimpleExecutorWithMode;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookInputMethod {

    public static void doHook(){
        classesAndHooks.put("com.android.server.inputmethod.InputMethodManagerService",HookInputMethod::hookIInputMethodManager);
        classesAndHooks.put("com.android.server.inputmethod.ImeTrackerService",HookInputMethod::hookIImeTracker);
    }

    public static void hookIInputMethodManager(Class<?> hookClass){

        SimpleExecutorWithMode fakeInputMethodSubtype = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
            int callingUid = Binder.getCallingUid();
            String pkgName = getPackageName(callingUid);
            ExtendedRandom extendedRandom = new ExtendedRandom(callingUid);
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + "inputMethodSubtype".hashCode());
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            InputMethodSubtype.InputMethodSubtypeBuilder builder = new InputMethodSubtype.InputMethodSubtypeBuilder();
            InputMethodSubtype inputMethodSubtype = builder.setIsAsciiCapable(true)
                    .setIsAuxiliary(false)
                    .setIsAsciiCapable(true)
                    .setLanguageTag("zh-CN")
                    .setSubtypeMode("Pinyin")
                    .setSubtypeLocale("zh-CN")
                    .setSubtypeId(extendedRandom.nextInt())
                    .setSubtypeNameResId(extendedRandom.nextInt())
                    .setSubtypeExtraValue(extendedRandom.nextString(extendedRandom.nextInt(6)+2))
                    .setSubtypeIconResId(extendedRandom.nextInt())
                    .setOverridesImplicitlyEnabledSubtype(true).build();
            param.setResult(inputMethodSubtype);

        });
        SimpleExecutorWithMode fakeInputMethodSubtypeList = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
            int callingUid = Binder.getCallingUid();
            String pkgName = getPackageName(callingUid);
            ExtendedRandom extendedRandom = new ExtendedRandom(callingUid);
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + "inputMethodSubtype".hashCode());
            if (pkgName != null){
                extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
            }
            InputMethodSubtype.InputMethodSubtypeBuilder builder = new InputMethodSubtype.InputMethodSubtypeBuilder();
            InputMethodSubtype inputMethodSubtype = builder.setIsAsciiCapable(true)
                    .setIsAuxiliary(false)
                    .setIsAsciiCapable(true)
                    .setLanguageTag("zh-CN")
                    .setSubtypeMode("Pinyin")
                    .setSubtypeLocale("zh-CN")
                    .setSubtypeId(extendedRandom.nextInt())
                    .setSubtypeNameResId(extendedRandom.nextInt())
                    .setSubtypeExtraValue(extendedRandom.nextString(extendedRandom.nextInt(6)+2))
                    .setSubtypeIconResId(extendedRandom.nextInt())
                    .setOverridesImplicitlyEnabledSubtype(true).build();
            List<InputMethodSubtype> list = new ArrayList<>();
            list.add(inputMethodSubtype);
            param.setResult(list);

        });


        SimpleExecutorWithMode fakeInputMethodInfoList = new SimpleExecutorWithMode(MODE_BEFORE, param -> {
            List<InputMethodInfo> list = new ArrayList<>();
            list.add(generateInputMethodInfo());
            param.setResult(list);
        });
        SimpleExecutorWithMode fakeInputMethodInfo = new SimpleExecutorWithMode(MODE_BEFORE, param -> param.setResult(generateInputMethodInfo()));
        SimpleExecutorWithMode addRandom = new SimpleExecutorWithMode(MODE_AFTER, param -> {
            int callingUid = Binder.getCallingUid();
            ExtendedRandom extendedRandom = ExtendedRandom.generateBasicFromUid(callingUid,"getInputMethodWindowVisibleHeight");
            double percent = extendedRandom.nextDouble() % 0.05; // your calculation has 5% error,no way for fingerprint now
            int result = (int) param.getResult();
            param.setResult(result + percent*result);
        });

//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addClient",null);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getCurrentInputMethodInfoAsUser",fakeInputMethodInfo);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getInputMethodList",fakeInputMethodInfoList);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getEnabledInputMethodList",fakeInputMethodInfoList);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getEnabledInputMethodSubtypeList",fakeInputMethodSubtypeList);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getLastInputMethodSubtype",fakeInputMethodSubtype);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"showSoftInput",true);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"hideSoftInput",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"hideSoftInputFromServerForTest",null);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startInputOrWindowGainedFocus",executorNopArg(new int[]{2}));
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startInputOrWindowGainedFocusAsync",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"showInputMethodPickerFromClient",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"showInputMethodPickerFromSystem",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isInputMethodPickerShownForTest",true);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getCurrentInputMethodSubtype",fakeInputMethodSubtype);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setAdditionalInputMethodSubtypes",null);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setExplicitlyEnabledInputMethodSubtypes",null);
        hookAllMethodsWithCache_executeIfNonSys(hookClass,"getInputMethodWindowVisibleHeight",addRandom);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"reportPerceptibleAsync",null);
//        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"removeImeSurface",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"removeImeSurfaceFromWindowAsync",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startProtoDump",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isImeTraceEnabled",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startImeTrace",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"stopImeTrace",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startStylusHandwriting",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"startConnectionlessStylusHandwriting",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"prepareStylusHandwritingDelegation",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"acceptStylusHandwritingDelegation",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"acceptStylusHandwritingDelegationAsync",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"isStylusHandwritingAvailableAsUser",true);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"addVirtualStylusIdForTestSession",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"setStylusWindowIdleTimeoutForTest",null);

//    hookAllMethods(hookClass,"getImeTrackerService",IImeTracker);
    }
    public static void hookIImeTracker(Class<?> hookClass){
//    hookAllMethods(hookClass,"onStart",ImeTracker);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"onProgress",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"onFailed",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"onCancelled",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"onShown",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"onHidden",null);
        hookAllMethodsWithCache_ReturnObjIfNonSys(hookClass,"hasPendingImeVisibilityRequests",true);
    }
    //InputMethodInfo:buildFakeResolveInfo
    public static ResolveInfo buildFakeResolveInfo(String packageName, String className,
                                                   CharSequence label) {
        ResolveInfo ri = new ResolveInfo();
        ServiceInfo si = new ServiceInfo();
        ApplicationInfo ai = new ApplicationInfo();
        ai.packageName = packageName;
        ai.enabled = true;
        si.applicationInfo = ai;
        si.enabled = true;
        si.packageName = packageName;
        si.name = className;
        si.exported = true;
        si.nonLocalizedLabel = label;
        ri.serviceInfo = si;
        return ri;
    }
    static {
        XposedHelpers.findAndHookConstructor(InputMethodInfo.class, InputMethodInfo.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (param.args[0] == null){
                    param.setResult(null);
                }
            }
        });
    }
    public static InputMethodInfo generateInputMethodInfo(){
        int callingUid = Binder.getCallingUid();
        String pkgName = getPackageName(callingUid);
        ExtendedRandom extendedRandom = new ExtendedRandom(callingUid);
        extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + "inputMethodSubtype".hashCode());
        if (pkgName != null){
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
        }
//            LoggerLog(new Exception(pkgName));
        InputMethodSubtype.InputMethodSubtypeBuilder builder = new InputMethodSubtype.InputMethodSubtypeBuilder();
        InputMethodSubtype inputMethodSubtype = builder.setIsAsciiCapable(true)
                .setIsAuxiliary(false)
                .setIsAsciiCapable(true)
                .setLanguageTag("zh-CN")
                .setSubtypeMode("Pinyin")
                .setSubtypeLocale("zh-CN")
                .setSubtypeId(extendedRandom.nextInt())
                .setSubtypeNameResId(extendedRandom.nextInt())
                .setSubtypeExtraValue(extendedRandom.nextString(extendedRandom.nextInt(6)+2))
                .setSubtypeIconResId(extendedRandom.nextInt())
                .setOverridesImplicitlyEnabledSubtype(true).build();
        List<InputMethodSubtype> subtypeList = new ArrayList<>();
        subtypeList.add(inputMethodSubtype);

        Object subtypeArray = XposedHelpers.newInstance(InputMethodSubtypeArrayClass,new Class[]{List.class},subtypeList);

        ResolveInfo fakeResolverInfo = buildFakeResolveInfo("com.sohu.inputmethod.sogou",".SogouIME","");
        InputMethodInfo inputMethodInfo = (InputMethodInfo)
                XposedHelpers.newInstance(InputMethodInfo.class,new Class[]{InputMethodInfo.class}, (Object) null);

        XposedHelpers.setBooleanField(inputMethodInfo,"mIsAuxIme",true);
        XposedHelpers.setObjectField(inputMethodInfo,"mService",fakeResolverInfo);
        XposedHelpers.setObjectField(inputMethodInfo,"mSettingsActivityName","com.sohu.inputmethod.sogou.SogouIMESettingsLauncher");
        XposedHelpers.setObjectField(inputMethodInfo,"mId",new ComponentName("com.sohu.inputmethod.sogou",".SogouIME").flattenToShortString());
        XposedHelpers.setIntField(inputMethodInfo,"mIsDefaultResId",0);
        XposedHelpers.setObjectField(inputMethodInfo,"mSubtypes",subtypeArray);
        XposedHelpers.setBooleanField(inputMethodInfo,"mForceDefault",false);
        XposedHelpers.setBooleanField(inputMethodInfo,"mSupportsSwitchingToNextInputMethod",true);
        XposedHelpers.setBooleanField(inputMethodInfo,"mInlineSuggestionsEnabled",true);
        XposedHelpers.setBooleanField(inputMethodInfo,"mSupportsInlineSuggestionsWithTouchExploration",true);
        XposedHelpers.setBooleanField(inputMethodInfo,"mSuppressesSpellChecker",true);
        XposedHelpers.setBooleanField(inputMethodInfo,"mShowInInputMethodPicker",true);
        XposedHelpers.setBooleanField(inputMethodInfo,"mIsVrOnly",false);
        XposedHelpers.setIntField(inputMethodInfo,"mHandledConfigChanges",0);
        XposedHelpers.setBooleanField(inputMethodInfo,"mSupportsStylusHandwriting",false);
        XposedHelpers.setObjectField(inputMethodInfo,"mStylusHandwritingSettingsActivityAttr","");

        return inputMethodInfo;
    }
}
