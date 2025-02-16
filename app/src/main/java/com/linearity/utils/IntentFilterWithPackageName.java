package com.linearity.utils;

import android.content.IntentFilter;

import androidx.annotation.Nullable;

public class IntentFilterWithPackageName extends IntentFilter {
    public void addActionWithPackageName(String name,@Nullable String packageName){
        addAction(packageName + "|" + name);
    }
}
