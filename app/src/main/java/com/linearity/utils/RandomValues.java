package com.linearity.utils;

import static android.content.res.Configuration.COLOR_MODE_HDR_MASK;
import static android.content.res.Configuration.COLOR_MODE_HDR_NO;
import static android.content.res.Configuration.COLOR_MODE_HDR_UNDEFINED;
import static android.content.res.Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_MASK;
import static android.content.res.Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_NO;
import static android.content.res.Configuration.COLOR_MODE_WIDE_COLOR_GAMUT_UNDEFINED;
import static android.content.res.Configuration.GRAMMATICAL_GENDER_FEMININE;
import static android.content.res.Configuration.GRAMMATICAL_GENDER_MASCULINE;
import static android.content.res.Configuration.GRAMMATICAL_GENDER_NEUTRAL;
import static android.content.res.Configuration.GRAMMATICAL_GENDER_NOT_SPECIFIED;
import static com.linearity.datservicereplacement.StartHook.publicSeed;

import android.content.res.Configuration;

public class RandomValues {


    /**
     * MCC    MNC       牌         操作者      状态     级别（兆赫）                                    参考和笔记
     * 460     00     中国移动     中国移动     操作     2010年GSM 900 / GSM 1800/1880 / TD-SCDMA      TD-SCDMA
     * 460     01     中国联通     中国联通     操作     GSM 900 / GSM 1800 / UMTS 2100               CDMA网络出售给中国电信 ，WCDMA试商用启动2009年5月和2009年10月全面投入商业运作。
     * 460     02     中国移动     中国移动     操作     2010年GSM 900 / GSM 1800/1880 / TD-SCDMA      TD-SCDMA
     * 460     03     中国电信     中国电信     操作     CDMA2000 800 / CDMA2000 2100                     EV-DO
     * 460     05     中国电信     中国电信     操作
     * 460     06     中国联通     中国联通     操作     GSM 900 / GSM 1800 / UMTS 2100
     * 460     07     中国移动     中国移动     操作     GSM 900 / GSM 1800/1880 TD-SCDMA / TD-SCDMA      010
     * 460     20     中国铁通集团有限公司     中国铁通集团有限公司     操作     GSM-R
     */
    public static final int[][] mccAndMncArray = {
            {460,0},{460,1},{460,2},{460,3},
            {460,5},{460,6},{460,7},
    };
    public static int MCCFromUid(int uid){
        ExtendedRandom extendedRandom = new ExtendedRandom(((long) "MCCAndMNC".hashCode()) * uid ^ publicSeed);
        return extendedRandom.pickFromArray(mccAndMncArray)[0];
    }
    public static int MNCFromUid(int uid){
        ExtendedRandom extendedRandom = new ExtendedRandom(((long) "MCCAndMNC".hashCode()) * uid ^ publicSeed);
        return extendedRandom.pickFromArray(mccAndMncArray)[1];
    }

    public static int[] MCCAndMNCFromUid(int uid){
        ExtendedRandom extendedRandom = new ExtendedRandom(((long) "MCCAndMNC".hashCode()) * uid ^ publicSeed);
        return extendedRandom.pickFromArray(mccAndMncArray);
    }

    public static final int[] grammaticalGenderArray = {
            GRAMMATICAL_GENDER_NOT_SPECIFIED,
            GRAMMATICAL_GENDER_NEUTRAL,
            GRAMMATICAL_GENDER_FEMININE,
            GRAMMATICAL_GENDER_MASCULINE,
    };
    //sorry everyone for wrong GrammaticalGender but we need to hide.
    public static @Configuration.GrammaticalGender int grammaticalGenderFromUid(int uid){
        ExtendedRandom extendedRandom = new ExtendedRandom(((long) "@Configuration.GrammaticalGender".hashCode()) * publicSeed ^ uid + publicSeed);
        return extendedRandom.pickFromArray(grammaticalGenderArray);
    }

    public static final int[] colorModeBitMasks = {COLOR_MODE_WIDE_COLOR_GAMUT_MASK};
    public static int colorModeFromUid(int uid){

        ExtendedRandom extendedRandom = new ExtendedRandom(((long) "Configuration.colorMode".hashCode()) * publicSeed ^ uid + publicSeed * uid);
        int colorMode = 0;
        if (extendedRandom.nextBoolean()){
            if (extendedRandom.nextBoolean()){
                colorMode += COLOR_MODE_WIDE_COLOR_GAMUT_MASK;
            }else {
                colorMode += COLOR_MODE_WIDE_COLOR_GAMUT_NO;
            }
        }else {
            colorMode += COLOR_MODE_WIDE_COLOR_GAMUT_UNDEFINED;
        }
        if (extendedRandom.nextBoolean()){
            if (extendedRandom.nextBoolean()){
                colorMode += COLOR_MODE_HDR_MASK;
            }else {
                colorMode += COLOR_MODE_HDR_NO;
            }
        }else {
            colorMode += COLOR_MODE_HDR_UNDEFINED;
        }
        return colorMode;
    }
}
