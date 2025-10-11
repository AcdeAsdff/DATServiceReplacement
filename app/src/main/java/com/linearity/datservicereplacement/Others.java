package com.linearity.datservicereplacement;

import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.StartHook.isHookedPoolRegistered;

import org.jetbrains.annotations.TestOnly;

import java.util.HashSet;
import java.util.Set;

public class Others {

    private static final Set<Class<?>> hookedPoolForConstructor = new HashSet<>();
    private static final Set<Class<?>> hookedPoolForMethods = new HashSet<>();

    @TestOnly
    public static void doHook(){
        classesAndHooks.put("android.content.res.Configuration", Others::listenMethods);
    }


    public static void listenConstructor(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,hookedPoolForConstructor)){return;}
        listenConstructor(hookClass);
    }

    public static void listenMethods(Class<?> hookClass){
        if (isHookedPoolRegistered(hookClass,hookedPoolForMethods)){return;}
        listenMethods(hookClass);
    }
}
