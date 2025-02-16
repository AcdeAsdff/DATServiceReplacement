package com.linearity.utils.LocationUtils;

import android.telephony.CellIdentity;

import com.linearity.utils.GeometryUtils;

import java.util.Random;

public class CellIdentityWithLocation{

    final CellIdentity cellIdentity;
    final double lat;
    final double lit;
    final int freqMHz;
    public static final int basePL = -43;

    public CellIdentityWithLocation(double lat,double lit,CellIdentity cellIdentity, int freqMHz){
        this.lat = lat;
        this.lit = lit;
        this.cellIdentity = cellIdentity;
        this.freqMHz = freqMHz;
    }
    public CellIdentity getCellIdentity() {
        return cellIdentity;
    }

    public double getLat(){
        return lat;
    }

    public double getLit() {
        return lit;
    }


    public double signalStrengthRSRPFromCoordinates(double latTo,double litTo){
        double distance = GeometryUtils.distanceOnEarth(lat,lit,latTo,litTo);
        return Math.log10(distance) * 20 + 32.45 + Math.log10(freqMHz) * 20 - basePL;
    }

    /**
     * i don't have way to solve from rssi :(
     */
    public double signalStrengthRSSIFromCoordinates(double latTo,double litTo){
        return signalStrengthRSRPFromCoordinates(latTo,litTo) + new Random().nextInt(10)-5;
    }
}
