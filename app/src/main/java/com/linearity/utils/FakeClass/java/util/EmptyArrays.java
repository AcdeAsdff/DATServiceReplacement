package com.linearity.utils.FakeClass.java.util;

import static com.linearity.datservicereplacement.SomeClasses.NetworkStateClass;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.app.usage.NetworkStats;
import android.content.ComponentName;
import android.content.SyncAdapterType;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import java.lang.reflect.Array;
import java.util.Arrays;

public class EmptyArrays {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final StackTraceElement[] EMPTY_STACKTRACE_ELEMENT_ARRAY = new StackTraceElement[0];
    public static final Account[] EMPTY_ACCOUNT_ARRAY = new Account[0];
    public static final AuthenticatorDescription[] EMPTY_AUTHENTICATOR_DESCRIPTION_ARRAY = new AuthenticatorDescription[0];
    public static final NetworkCapabilities[] EMPTY_NETWORK_CAPABILITY_ARRAY = new NetworkCapabilities[0];
    public static final NetworkInfo[] EMPTY_NETWORK_INFO_ARRAY = new NetworkInfo[0];

    public static final Object[] EMPTY_NETWORK_STATE_ARRAY = (Object[]) Array.newInstance(NetworkStateClass,0);
    public static final ComponentName EMPTY_COMPONENT_NAME = new ComponentName("","");
    public static final SyncAdapterType[] EMPTY_SYNC_ADAPTER_TYPE_ARRAY = new SyncAdapterType[0];
}
