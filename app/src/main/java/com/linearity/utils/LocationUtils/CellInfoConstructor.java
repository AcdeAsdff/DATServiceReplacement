package com.linearity.utils.LocationUtils;

import static com.linearity.utils.LoggerUtils.LoggerLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.telephony.CellIdentityLte;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthLte;
import android.telephony.ClosedSubscriberGroupInfo;

import java.lang.reflect.Constructor;
import java.util.Collection;

import de.robv.android.xposed.XposedHelpers;

public class CellInfoConstructor {
    public static Class<?> CellConfigLteClass = XposedHelpers.findClass("android.telephony.CellConfigLte",CellInfoLte.class.getClassLoader());

    public static final Constructor<CellIdentityLte> Constructor_CellIdentityLte = (Constructor<CellIdentityLte>)
            XposedHelpers.findConstructorExactIfExists(CellIdentityLte.class,
            int.class,int.class,int.class,int.class,
            int[].class,int.class,
            String.class,String.class,
            String.class,String.class,
            Collection.class,ClosedSubscriberGroupInfo.class
            );
    public static final Constructor<CellInfoLte> Constructor_CellInfoLte = (Constructor<CellInfoLte>)
            XposedHelpers.findConstructorExactIfExists(CellInfoLte.class,
                    int.class, boolean.class, long.class,
                    CellIdentityLte.class, CellSignalStrengthLte.class,
                    CellConfigLteClass
                    );

    public static final Constructor<CellSignalStrengthLte> Constructor_CellSignalStrengthLte = (Constructor<CellSignalStrengthLte>)
            XposedHelpers.findConstructorExactIfExists(CellSignalStrengthLte.class
                    ,int.class,int.class,int.class,
                    int.class,int.class,int.class,
                    int.class);
    public static Object fakeCellInfoConfig = XposedHelpers.newInstance(CellConfigLteClass,false);
    public static CellIdentityLte constructCellIdentityLte(
            int ci, int pci, int tac, int earfcn,
            @NonNull int[] bands,int bandwidth,
            @Nullable String mccStr, @Nullable String mncStr,
            @Nullable String alphal, @Nullable String alphas,
            @NonNull Collection<String> additionalPlmns,
                                                     @Nullable ClosedSubscriberGroupInfo csgInfo){
        try {
            return Constructor_CellIdentityLte.newInstance(ci,pci,tac,earfcn,bands,bandwidth,mccStr,mncStr,alphal,alphas,additionalPlmns,csgInfo);
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
    public static CellSignalStrengthLte fakeCellSignalStrengthLte = constructCellSignalStrengthLte(
            -51,-65,-9,
            Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0,4,1
    );
    public static CellSignalStrengthLte constructCellSignalStrengthLte(
            int rssi, int rsrp, int rsrq,
            int rssnr, int cqiTableIndex,
            int cqi, int timingAdvance,
            int level,int parametersUseForLevel){
        try {
            CellSignalStrengthLte result = new CellSignalStrengthLte(rssi,rsrp,rsrq,rssnr,cqiTableIndex,cqi,timingAdvance);
//            CellSignalStrengthLte result = Constructor_CellSignalStrengthLte.newInstance(rssi,rsrp,rsrq,rssnr,cqiTableIndex,cqi,timingAdvance);
            XposedHelpers.setIntField(result,"mParametersUseForLevel",parametersUseForLevel);
            XposedHelpers.setIntField(result,"mLevel",level);
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }

    public static CellInfoLte constructCellInfoLte(int connectionStatus, boolean registered, long timeStamp,
                                                   CellIdentityLte cellIdentityLte, CellSignalStrengthLte cellSignalStrengthLte,
                                                   Object/*CellConfigLte*/ cellConfig){
        try {
            return Constructor_CellInfoLte.newInstance(connectionStatus,registered,timeStamp,cellIdentityLte,cellSignalStrengthLte,cellConfig);
        }catch (Exception e){
            return null;
        }
    }

}
