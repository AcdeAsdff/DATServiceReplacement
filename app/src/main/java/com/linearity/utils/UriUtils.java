package com.linearity.utils;

import android.net.Uri;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UriUtils {
    private static final Set<String> SYSTEM_SCHEMES = Set.of(
            "content", "file", "android.resource", "data", "package", "market",
            "http", "https", "mailto", "tel", "sms", "smsto", "mms", "mmsto",
            "geo", "ftp", "intent", "settings", "about", "chrome", "android-app"
    );

    public static boolean isUriSystem(Uri uri){
        String scheme = uri.normalizeScheme().getScheme();
        if (scheme == null){return false;}
        return SYSTEM_SCHEMES.contains(scheme);
    }
    public static boolean isUriSystem(String uriString){
        return isUriSystem(Uri.parse(uriString));
    }
}
