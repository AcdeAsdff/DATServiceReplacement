package com.linearity.datservicereplacement.ActivityManagerService;

import androidx.annotation.Nullable;
import android.content.Intent;
import android.os.Binder;

public class ColormaticIntent extends Intent {

    public String actionCheatsSystem;

    public ColormaticIntent(Intent intent,String actionCheatsSystem){
        super(intent);
        this.actionCheatsSystem = actionCheatsSystem;
    }

    @Override
    public @Nullable String getAction() {
        if (Binder.getCallingUid() == 1000){
            return actionCheatsSystem;
        }
        return super.getAction();
    }
}
