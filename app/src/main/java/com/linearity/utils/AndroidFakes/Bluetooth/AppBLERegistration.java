package com.linearity.utils.AndroidFakes.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.AttributionSource;
import android.os.ParcelUuid;
import android.util.Pair;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.linearity.utils.AndroidConsts.BluetoothUuid;
import com.linearity.utils.ExtendedRandom;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;

public class AppBLERegistration {
    public static final String START_SCAN = "startScan";
    public final Object mScannerCallback;
    public final AttributionSource mAttributionSource;
    public final long registerTime;
    @NonNull
    public final StoppableBLEScanThread mThread;
    public final List<ScanResult> fakeScanResult = new LinkedList<>();
    public static final int[] phyArr = new int[]{
            BluetoothDevice.PHY_LE_1M,BluetoothDevice.PHY_LE_2M,BluetoothDevice.PHY_LE_CODED,
            BluetoothDevice.PHY_LE_1M_MASK,BluetoothDevice.PHY_LE_2M_MASK,BluetoothDevice.PHY_LE_CODED_MASK,
            BluetoothDevice.PHY_OPTION_S2,BluetoothDevice.PHY_OPTION_S8,BluetoothDevice.PHY_OPTION_NO_PREFERRED,
    };
    public AppBLERegistration(Object iScannerCallback, AttributionSource attributionSource){
        this.mAttributionSource = attributionSource;
        this.mScannerCallback = iScannerCallback;
        this.registerTime = System.currentTimeMillis();
        ExtendedRandom extendedRandom = new ExtendedRandom(attributionSource.getUid());
        String pkgName = attributionSource.getPackageName();
        if (pkgName != null){
            extendedRandom = new ExtendedRandom(extendedRandom.nextLong() + pkgName.hashCode());
        }
        extendedRandom = new ExtendedRandom(extendedRandom.nextMacAddrStr().hashCode());

        for (int i=0;i<extendedRandom.nextInt(10)+3;i++){
            fakeScanResult.add(generateFakeScanResult(extendedRandom));
        }
        this.mThread = new StoppableBLEScanThread(fakeScanResult,mScannerCallback);
    }

    private static final int DATA_TYPE_FLAGS = 0x01;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 0x02;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 0x03;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 0x04;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 0x05;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 0x06;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 0x07;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 0x08;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 0x09;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 0x0A;
    private static final int DATA_TYPE_SERVICE_DATA_16_BIT = 0x16;
    private static final int DATA_TYPE_SERVICE_DATA_32_BIT = 0x20;
    private static final int DATA_TYPE_SERVICE_DATA_128_BIT = 0x21;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_16_BIT = 0x14;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_32_BIT = 0x1F;
    private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_128_BIT = 0x15;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 0xFF;
    private static final byte[] DATA_TYPE_SERVICE_DATA_LENGTH = new byte[]{
            DATA_TYPE_SERVICE_DATA_16_BIT,DATA_TYPE_SERVICE_DATA_32_BIT,DATA_TYPE_SERVICE_DATA_128_BIT
    };

    public static ScanResult generateFakeScanResult(ExtendedRandom extendedRandom){
        List<Byte> rawBytesList = new LinkedList<>();
        List<Byte> tempRawBytesList = new LinkedList<>();
        BluetoothDevice bluetoothDevice;
        bluetoothDevice = (BluetoothDevice) XposedHelpers.newInstance(BluetoothDevice.class,extendedRandom.nextMacAddrStr());

        int flags = extendedRandom.nextInt(16);
        rawBytesList.add((byte) 3);
        rawBytesList.add((byte) DATA_TYPE_FLAGS);
        rawBytesList.add((byte) flags);

        List<ParcelUuid> serviceUuids = new ArrayList<>();
        for (int i=0;i<extendedRandom.nextInt(6)+1;i++){
            ParcelUuid uuid = new ParcelUuid(extendedRandom.nextUUID_16_32_128bit());
            serviceUuids.add(uuid);
            Pair<byte[],Integer> bytesAndLength = BluetoothUuid.uuidToBytes(uuid);
            byte[] uuidBytes = bytesAndLength.first;
            int length = bytesAndLength.second;
            if (length == 2){
                tempRawBytesList.add((byte) (extendedRandom.nextBoolean()?DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL:DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE));
            }else if(length == 4){
                tempRawBytesList.add((byte) (extendedRandom.nextBoolean()?DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL:DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE));
            }else {
                tempRawBytesList.add((byte) (extendedRandom.nextBoolean()?DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL:DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE));
            }
            for (byte b:uuidBytes){
                tempRawBytesList.add(b);
            }
        }
        rawBytesList.add((byte) (tempRawBytesList.size()+1));
        rawBytesList.addAll(tempRawBytesList);
        tempRawBytesList = new LinkedList<>();

        List<ParcelUuid> serviceSolicitationUuids = new ArrayList<>();
        for (int i=0;i<extendedRandom.nextInt(32)+1;i++){
            ParcelUuid uuid = new ParcelUuid(extendedRandom.nextUUID_16_32_128bit());
            serviceSolicitationUuids.add(uuid);
            Pair<byte[],Integer> bytesAndLength = BluetoothUuid.uuidToBytes(uuid);
            byte[] uuidBytes = bytesAndLength.first;
            int length = bytesAndLength.second;
            if (length == 2){
                tempRawBytesList.add((byte) (DATA_TYPE_SERVICE_SOLICITATION_UUIDS_16_BIT));
            }else if(length == 4){
                tempRawBytesList.add((byte) (DATA_TYPE_SERVICE_SOLICITATION_UUIDS_32_BIT));
            }else {
                tempRawBytesList.add((byte) (DATA_TYPE_SERVICE_SOLICITATION_UUIDS_128_BIT));
            }
            for (byte b:uuidBytes){
                tempRawBytesList.add(b);
            }
        }
        rawBytesList.add((byte) (tempRawBytesList.size()+1));
        rawBytesList.addAll(tempRawBytesList);
        tempRawBytesList = new LinkedList<>();

        String name = extendedRandom.nextString(extendedRandom.nextInt(10)+5);
        byte[] nameByte = name.getBytes(StandardCharsets.UTF_8);
        {
            rawBytesList.add((byte) (nameByte.length + 2));
            rawBytesList.add((byte) DATA_TYPE_LOCAL_NAME_SHORT);
            for (byte b:nameByte){
                rawBytesList.add(b);
            }
            rawBytesList.add((byte) (nameByte.length + 2));
            rawBytesList.add((byte) DATA_TYPE_LOCAL_NAME_COMPLETE);
            for (byte b:nameByte){
                rawBytesList.add(b);
            }
        }

        int txPowerLevel = extendedRandom.nextInt(100)-90;
        {
            rawBytesList.add((byte) 3);
            rawBytesList.add((byte) DATA_TYPE_TX_POWER_LEVEL);
            rawBytesList.add((byte) txPowerLevel);
        }

        SparseArray<byte[]> manufacturerSpecificData = new SparseArray<>();
        byte[] manufacturerSpecificDataValue = extendedRandom.nextBytes(5);
        manufacturerSpecificData.put(extendedRandom.nextInt(65536),manufacturerSpecificDataValue);
        tempRawBytesList.add((byte) DATA_TYPE_MANUFACTURER_SPECIFIC_DATA);
        for (byte b : manufacturerSpecificDataValue) {
            tempRawBytesList.add(b);
        }
        rawBytesList.add((byte) (tempRawBytesList.size() + 1));
        rawBytesList.addAll(tempRawBytesList);

        //still not write to array!
        Map<ParcelUuid,byte[]> serviceData = new HashMap<>();
        for (int i=0;i<Math.min(extendedRandom.nextInt(serviceSolicitationUuids.size()),extendedRandom.nextInt(5)+1);i++){
            ParcelUuid parcelUuid = serviceSolicitationUuids.get(extendedRandom.nextInt(serviceSolicitationUuids.size()));
            serviceData.put(parcelUuid,new byte[]{extendedRandom.nextByte(),
                    manufacturerSpecificDataValue[0],manufacturerSpecificDataValue[1],
                    manufacturerSpecificDataValue[2],manufacturerSpecificDataValue[3]});
        }

        byte[] rawBytes = new byte[rawBytesList.size()];
        for (int i=0;i<rawBytes.length;i++){
            rawBytes[i] = rawBytesList.get(i);
        }
        ScanRecord scanRecord = (ScanRecord) XposedHelpers.newInstance(ScanRecord.class,
                serviceUuids,
                serviceSolicitationUuids,
                manufacturerSpecificData,
                serviceData,
                flags,
                txPowerLevel,
                name,
                null,
                null,
                rawBytes
                );
        return new ScanResult(
                bluetoothDevice,
                extendedRandom.nextBoolean()?0x01:0x10,
                extendedRandom.pickFromArray(phyArr),
                extendedRandom.pickFromArray(phyArr),
                extendedRandom.nextInt(0xFF),
                extendedRandom.nextInt(255)-127,
                extendedRandom.nextInt(254)-127,
                extendedRandom.nextInt(65531)+6,
                scanRecord,
                System.currentTimeMillis()*1000 - Math.abs(extendedRandom.nextLong()%1000000000)
        );
    }
}