package com.linearity.utils.AndroidFakes.Bluetooth;

import static com.linearity.utils.AndroidFakes.Bluetooth.AppBLERegistration.START_SCAN;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import android.app.PendingIntent;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.os.DeadObjectException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import de.robv.android.xposed.XposedHelpers;

public class StoppableBLEScanThread extends Thread{
    private boolean started = false;
    public static final long DELAY = 1000;
    public static final int MAX_COUNTER = 60;
    public String command;
    public boolean shouldStop = false;
    private final List<ScanResult> fakeScanResult;
    private final Object mScannerCallback;
    private List<ScanFilter> filters = new LinkedList<>();
    public StoppableBLEScanThread(List<ScanResult> fakeScanResult, Object mScannerCallback){
        this.fakeScanResult = fakeScanResult;
        this.mScannerCallback = mScannerCallback;
    }

    public void setFilter(List<ScanFilter> filters){
        this.filters = filters;
    }

    public boolean stopped(){
        return shouldStop;
    }
    public void doStop(){
        shouldStop = true;
    }

    @Override
    public void run() {
        super.run();
        int counter = 0;
        while (true){
            if (shouldStop){return;}
            try {
                sleep(DELAY);
                counter++;
            } catch (InterruptedException e) {
                return;
            }
            if (counter == MAX_COUNTER){return;}
            if (Objects.equals(command, START_SCAN)){
                for (ScanResult s:fakeScanResult){
                    try {
                        sleep(DELAY);
                        counter++;
                    } catch (InterruptedException e) {
                        return;
                    }
                    for (ScanFilter filter:filters){
                        if (!filter.matches(s)){continue;}
                    }
                    if (!(mScannerCallback instanceof PendingIntent)){
                        try{
                            XposedHelpers.callMethod(mScannerCallback,"onScanResult",s);
                        }catch (Exception e){
                            if (!(e instanceof DeadObjectException)){
                                LoggerLog(e);
                            }
                        }
                    }
                }
            }

        }
    }

    public void setCommand(String command){
        synchronized (this){
            this.command = command;
        }
    }
    public String getCommand(){
        synchronized (this){
            return this.command;
        }
    }

    @Override
    public synchronized void start() {
        if (!started){super.start();started = true;}
    }

}
