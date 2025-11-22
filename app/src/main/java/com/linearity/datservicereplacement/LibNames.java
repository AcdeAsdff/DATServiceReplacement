package com.linearity.datservicereplacement;

import android.os.Build;

public class LibNames {
    /**
     * magisk/apatch module:dat_zygisk
     */
    public static final String DATZYGISK_SYSTEM_SERVER_LOCAL_PART = "/system/framework/libdatsystemserver_"+ Build.SUPPORTED_ABIS[0] +".so";
    public static final String DATZYGISK_LOCATION_LOCAL_PART = "/system/framework/libdatzygisk_location_local_part_"+ Build.SUPPORTED_ABIS[0] +".so";
}
