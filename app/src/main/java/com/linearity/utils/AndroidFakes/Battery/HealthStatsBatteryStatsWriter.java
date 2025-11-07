package com.linearity.utils.AndroidFakes.Battery;

import static android.os.BatteryStats.STATS_SINCE_CHARGED;

import static com.linearity.datservicereplacement.androidhooking.com.android.server.am.HookIBattery.uidToBatteryStatsImplUidObj;

import android.os.BatteryStats;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.health.HealthStatsWriter;
import android.os.health.PackageHealthStats;
import android.os.health.PidHealthStats;
import android.os.health.ProcessHealthStats;
import android.os.health.ServiceHealthStats;
import android.os.health.TimerStat;
import android.os.health.UidHealthStats;
import android.util.SparseArray;

import com.linearity.utils.ExtendedRandom;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.robv.android.xposed.XposedHelpers;

public class HealthStatsBatteryStatsWriter {

    private final long mNowRealtimeMs;
    private final long mNowUptimeMs;

    public HealthStatsBatteryStatsWriter() {
        mNowRealtimeMs = SystemClock.elapsedRealtime();
        mNowUptimeMs = SystemClock.uptimeMillis();
    }

    /**
     * Writes the contents of a BatteryStats.Uid into a HealthStatsWriter.
     */
    @SuppressWarnings("deprecation")
    public void writeUid(HealthStatsWriter uidWriter,int uidFor) {
        long elapsed = SystemClock.elapsedRealtime();
        ExtendedRandom random = new ExtendedRandom( uidFor * (long)HealthStatsBatteryStatsWriter.class.getName().hashCode());
        int N;
        BatteryStats.Timer timer;
        SparseArray<? extends BatteryStats.Uid.Sensor> sensors;
        SparseArray<? extends BatteryStats.Uid.Pid> pids;
        BatteryStats.ControllerActivityCounter controller;
        long sum;


        int periods = (int) ((elapsed/30000));
        //
        // It's a little odd for these first four to be here but it's not the end of the
        // world. It would be easy enough to duplicate them somewhere else if this API
        // grows.
        //

        long usedTime = random.nextInt(36000000)+7200000 + elapsed;
        long usedUpTime = random.nextInt(36000000)+3600000 + elapsed;
        while (usedUpTime >= usedTime){
            usedUpTime = random.nextInt(36000000)+3600000 + elapsed;
        }
        long usedScreenOffTime = random.nextInt(36000000 + 7200000) + elapsed;
        while (usedScreenOffTime >= usedTime){
            usedScreenOffTime = random.nextInt(36000000)+3600000 + elapsed;
        }
        long usedScreenOffUpTime = random.nextInt(36000000 + 7200000) + (periods*10000L);
        while (usedScreenOffUpTime >= usedTime){
            usedScreenOffUpTime = random.nextInt(36000000)+3600000 + (periods*10000L);
        }

        // MEASUREMENT_REALTIME_BATTERY_MS
        uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_REALTIME_BATTERY_MS,usedTime
//                bs.computeBatteryRealtime(mNowRealtimeMs*1000, STATS_SINCE_CHARGED)/1000
        );

        // MEASUREMENT_UPTIME_BATTERY_MS
        uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_UPTIME_BATTERY_MS,usedUpTime
//                bs.computeBatteryUptime(mNowUptimeMs*1000, STATS_SINCE_CHARGED)/1000
        );

        // MEASUREMENT_REALTIME_SCREEN_OFF_BATTERY_MS
        uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_REALTIME_SCREEN_OFF_BATTERY_MS,usedScreenOffTime
//                bs.computeBatteryScreenOffRealtime(
//                        mNowRealtimeMs*1000, STATS_SINCE_CHARGED)/1000
        );

        // MEASUREMENT_UPTIME_SCREEN_OFF_BATTERY_MS
        uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_UPTIME_SCREEN_OFF_BATTERY_MS,usedScreenOffUpTime
//                bs.computeBatteryScreenOffUptime(mNowUptimeMs*1000, STATS_SINCE_CHARGED)/1000
        );

        //
        // Now on to the real per-uid stats...
        //
//TODO:Non-sys wake-lock key and values.I may already prevent some
        Object uidObj = uidToBatteryStatsImplUidObj.get(uidFor);
        if (uidObj != null) {
            Map<String, ? extends BatteryStats.Uid.Wakelock> map =
                    (Map<String, ? extends BatteryStats.Uid.Wakelock>)
                            XposedHelpers.callMethod(XposedHelpers.getObjectField(uidObj, "mWakelockStats"), "getMap");

            long totalTime = SystemClock.elapsedRealtime() - random.nextInt(30000) - 30000;

            long timePartial = (long) (random.nextSmallDouble(0.8) * totalTime);
            long timeWindow = totalTime - timePartial;
            long timeDraw = (long) (timeWindow * (0.1 + random.nextSmallDouble(0.3)));
            for (final Map.Entry<String, ? extends BatteryStats.Uid.Wakelock> entry :
                    map.entrySet()
//                uid.getWakelockStats().entrySet()
            ) {
                final String key = entry.getKey();
                if (Objects.equals("Icing",key)){continue;}
                final BatteryStats.Uid.Wakelock wakelock = entry.getValue();
                TimerStat stat;
                // TIMERS_WAKELOCKS_FULL
                int statLockCount;


//                timer = wakelock.getWakeTime(BatteryStats.WAKE_TYPE_FULL);
                statLockCount = (periods/2) * 3;
                stat = new TimerStat(statLockCount,
                        totalTime
                );
                addTimers(uidWriter, UidHealthStats.TIMERS_WAKELOCKS_FULL, key, stat);

                // TIMERS_WAKELOCKS_PARTIAL
//                timer = wakelock.getWakeTime(BatteryStats.WAKE_TYPE_PARTIAL);
                statLockCount = (periods/2) * 3;
                stat = new TimerStat(statLockCount, timePartial);
                addTimers(uidWriter, UidHealthStats.TIMERS_WAKELOCKS_PARTIAL, key, stat);

                // TIMERS_WAKELOCKS_WINDOW
//                timer = wakelock.getWakeTime(BatteryStats.WAKE_TYPE_WINDOW);
                statLockCount = (periods/2) * 3;
                stat = new TimerStat(statLockCount,
                        timeWindow
                );
                addTimers(uidWriter, UidHealthStats.TIMERS_WAKELOCKS_WINDOW, key, stat);

                statLockCount = (periods/2) * 3;
                stat = new TimerStat(statLockCount,
                        timeDraw
                );
                addTimers(uidWriter, UidHealthStats.TIMERS_WAKELOCKS_DRAW, key, stat);
            }


            Map<String, ? extends BatteryStats.Timer> map2 =
                    (Map<String, ? extends BatteryStats.Timer>)
                            XposedHelpers.callMethod(XposedHelpers.getObjectField(uidObj, "mSyncStats"), "getMap");
            // TIMERS_SYNCS
            //TODO:Relate with ContentResolver#requestSync add values
            for (final Map.Entry<String,? extends BatteryStats.Timer> entry: map2.entrySet()) {
                addTimers(uidWriter, UidHealthStats.TIMERS_SYNCS, entry.getKey(), new TimerStat(random.nextInt(20),0L));
            }

            Map<String, ? extends BatteryStats.Timer> map3 =
                    (Map<String, ? extends BatteryStats.Timer>)
                            XposedHelpers.callMethod(XposedHelpers.getObjectField(uidObj, "mJobStats"), "getMap");
            // TIMERS_JOBS
            //TODO:Fake more if needed
            for (final Map.Entry<String,? extends BatteryStats.Timer> entry:
                    map3.entrySet()) {
                addTimers(uidWriter, UidHealthStats.TIMERS_JOBS, entry.getKey(), new TimerStat(random.nextInt(100),totalTime));
            }


            sensors = (SparseArray<? extends BatteryStats.Uid.Sensor>) XposedHelpers.getObjectField(uidObj, "mSensorStats");


            N = sensors.size();
            for (int i=0; i<N; i++) {
                int sensorId = sensors.keyAt(i);
                // Battery Stats stores the GPS sensors with a bogus key in this API. Pull it out
                // as a separate metric here so as to not expose that in the API.
                if (sensorId == BatteryStats.Uid.Sensor.GPS) {
                    addTimer(uidWriter, UidHealthStats.TIMER_GPS_SENSOR,random.nextInt(50), (long) (totalTime * random.nextSmallDouble(0.7)));
                } else {
                    addTimers(uidWriter, UidHealthStats.TIMERS_SENSORS, Integer.toString(sensorId),
                            new TimerStat(random.nextInt(50), (long) (totalTime * random.nextSmallDouble(0.7))));
                }
            }


            // STATS_PIDS
            pids = (SparseArray<? extends BatteryStats.Uid.Pid>) XposedHelpers.getObjectField(uidObj, "mPids");
            N = pids.size();
            for (int i=0; i<N; i++) {
                final HealthStatsWriter writer = new HealthStatsWriter(PidHealthStats.CONSTANTS);

                // MEASUREMENT_WAKE_NESTING_COUNT
                writer.addMeasurement(PidHealthStats.MEASUREMENT_WAKE_NESTING_COUNT, random.nextInt(100));

                double a = (random.nextSmallDouble(0.2)+0.5);
                double b = 0.8-a;
                // MEASUREMENT_WAKE_SUM_MS
                writer.addMeasurement(PidHealthStats.MEASUREMENT_WAKE_SUM_MS, (long) (totalTime * a));

                // MEASUREMENT_WAKE_START_MS
                writer.addMeasurement(PidHealthStats.MEASUREMENT_WAKE_START_MS, (long) (totalTime * b));

                uidWriter.addStats(UidHealthStats.STATS_PIDS, Integer.toString(pids.keyAt(i)), writer);
            }
            // STATS_PROCESSES

            //TODO:fake
//            for (final Map.Entry<String,? extends BatteryStats.Uid.Proc> entry:
//                    uid.getProcessStats().entrySet()) {
//                final HealthStatsWriter writer = new HealthStatsWriter(ProcessHealthStats.CONSTANTS);
//                writeProc(writer, entry.getValue());
//                uidWriter.addStats(UidHealthStats.STATS_PROCESSES, entry.getKey(), writer);
//            }


            // STATS_PACKAGES

            //TODO:fake
//            for (final Map.Entry<String,? extends BatteryStats.Uid.Pkg> entry:
//                    uid.getPackageStats().entrySet()) {
//                final HealthStatsWriter writer = new HealthStatsWriter(PackageHealthStats.CONSTANTS);
//                writePkg(writer, entry.getValue());
//                uidWriter.addStats(UidHealthStats.STATS_PACKAGES, entry.getKey(), writer);
//            }


            //TODO:fake
//            controller = uid.getWifiControllerActivity();
//            if (controller != null) {
//                // MEASUREMENT_WIFI_IDLE_MS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_IDLE_MS,
//                        controller.getIdleTimeCounter().getCountLocked(STATS_SINCE_CHARGED));
//                // MEASUREMENT_WIFI_RX_MS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_RX_MS,
//                        controller.getRxTimeCounter().getCountLocked(STATS_SINCE_CHARGED));
//                // MEASUREMENT_WIFI_TX_MS
//                sum = 0;
//                for (final BatteryStats.LongCounter counter: controller.getTxTimeCounters()) {
//                    sum += counter.getCountLocked(STATS_SINCE_CHARGED);
//                }
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_TX_MS, sum);
//                // MEASUREMENT_WIFI_POWER_MAMS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_POWER_MAMS,
//                        controller.getPowerCounter().getCountLocked(STATS_SINCE_CHARGED));
//            }

            //TODO:fake
//            controller = uid.getBluetoothControllerActivity();
//            if (controller != null) {
//                // MEASUREMENT_BLUETOOTH_IDLE_MS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_IDLE_MS,
//                        controller.getIdleTimeCounter().getCountLocked(STATS_SINCE_CHARGED));
//                // MEASUREMENT_BLUETOOTH_RX_MS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_RX_MS,
//                        controller.getRxTimeCounter().getCountLocked(STATS_SINCE_CHARGED));
//                // MEASUREMENT_BLUETOOTH_TX_MS
//                sum = 0;
//                for (final BatteryStats.LongCounter counter: controller.getTxTimeCounters()) {
//                    sum += counter.getCountLocked(STATS_SINCE_CHARGED);
//                }
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_TX_MS, sum);
//                // MEASUREMENT_BLUETOOTH_POWER_MAMS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_POWER_MAMS,
//                        controller.getPowerCounter().getCountLocked(STATS_SINCE_CHARGED));
//            }


            //TODO:fake
//            controller = uid.getModemControllerActivity();
//            if (controller != null) {
//                // MEASUREMENT_MOBILE_IDLE_MS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_IDLE_MS,
//                        controller.getIdleTimeCounter().getCountLocked(STATS_SINCE_CHARGED));
//                // MEASUREMENT_MOBILE_RX_MS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_RX_MS,
//                        controller.getRxTimeCounter().getCountLocked(STATS_SINCE_CHARGED));
//                // MEASUREMENT_MOBILE_TX_MS
//                sum = 0;
//                for (final BatteryStats.LongCounter counter: controller.getTxTimeCounters()) {
//                    sum += counter.getCountLocked(STATS_SINCE_CHARGED);
//                }
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_TX_MS, sum);
//                // MEASUREMENT_MOBILE_POWER_MAMS
//                uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_POWER_MAMS,
//                        controller.getPowerCounter().getCountLocked(STATS_SINCE_CHARGED));
//            }

            // MEASUREMENT_WIFI_RUNNING_MS
            //TODO:fake
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_RUNNING_MS,0);

            // MEASUREMENT_WIFI_FULL_LOCK_MS
            //TODO:fake
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_FULL_LOCK_MS,0);

            // TIMER_WIFI_SCAN
            uidWriter.addTimer(UidHealthStats.TIMER_WIFI_SCAN,0,0);

            // MEASUREMENT_WIFI_MULTICAST_MS
            //TODO:fake
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_MULTICAST_MS,0L);

            // TIMER_AUDIO
            //TODO:fake
            addTimer(uidWriter, UidHealthStats.TIMER_AUDIO, 0,0);

            // TIMER_VIDEO
            //TODO:fake
            addTimer(uidWriter, UidHealthStats.TIMER_VIDEO, 0,0);

            // TIMER_FLASHLIGHT
            addTimer(uidWriter, UidHealthStats.TIMER_FLASHLIGHT, 0,0);

            // TIMER_CAMERA
            //TODO:fake
            addTimer(uidWriter, UidHealthStats.TIMER_CAMERA, 0,0);

            // TIMER_FOREGROUND_ACTIVITY
            addTimer(uidWriter, UidHealthStats.TIMER_FOREGROUND_ACTIVITY,1,totalTime);


            // TIMER_BLUETOOTH_SCAN
            //TODO:related with StoppableBLEScan
            addTimer(uidWriter, UidHealthStats.TIMER_BLUETOOTH_SCAN, 0,0);

            // TIMER_PROCESS_STATE_TOP_MS
            addTimer(uidWriter, UidHealthStats.TIMER_PROCESS_STATE_TOP_MS,1,totalTime);

            // TIMER_PROCESS_STATE_FOREGROUND_SERVICE_MS
            addTimer(uidWriter, UidHealthStats.TIMER_PROCESS_STATE_FOREGROUND_SERVICE_MS,1,totalTime);

            // TIMER_PROCESS_STATE_TOP_SLEEPING_MS
            addTimer(uidWriter, UidHealthStats.TIMER_PROCESS_STATE_TOP_SLEEPING_MS,0,0);

            // TIMER_PROCESS_STATE_FOREGROUND_MS
            addTimer(uidWriter, UidHealthStats.TIMER_PROCESS_STATE_FOREGROUND_MS,1,totalTime);

            // TIMER_PROCESS_STATE_BACKGROUND_MS
            addTimer(uidWriter, UidHealthStats.TIMER_PROCESS_STATE_BACKGROUND_MS,0,0);

            // TIMER_PROCESS_STATE_CACHED_MS
            int stateCached = random.nextInt(20)+((int)(periods/3));
            addTimer(uidWriter, UidHealthStats.TIMER_PROCESS_STATE_CACHED_MS,stateCached,stateCached * (50L + random.nextInt(100)));

            // TIMER_VIBRATOR
            int vibrated = random.nextInt(20)+((int)(periods/3));
            addTimer(uidWriter, UidHealthStats.TIMER_VIBRATOR,vibrated,vibrated * 500L);

            // MEASUREMENT_OTHER_USER_ACTIVITY_COUNT
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_OTHER_USER_ACTIVITY_COUNT,random.nextInt(2000)+(50L*periods));

            // MEASUREMENT_BUTTON_USER_ACTIVITY_COUNT
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BUTTON_USER_ACTIVITY_COUNT,random.nextInt(2000)+(50L*periods));

            // MEASUREMENT_TOUCH_USER_ACTIVITY_COUNT
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_TOUCH_USER_ACTIVITY_COUNT,random.nextInt(2000)+(50L*periods));

            long bleRxPackets = random.nextInt(10000) + (periods*301L);
            long bleTxPackets = random.nextInt(10000) + (periods*301L);
            long wifiRxPackets = random.nextInt(10000) + (periods*301L);
            long wifiTxPackets = random.nextInt(10000) + (periods*301L);
            long mobileRxPackets = random.nextInt(10000) + (periods*301L);
            long mobileTxPackets = random.nextInt(10000) + (periods*301L);
            // MEASUREMENT_MOBILE_RX_BYTES
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_RX_BYTES,mobileTxPackets * (20 + random.nextInt(200)));

            // MEASUREMENT_MOBILE_TX_BYTES
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_TX_BYTES,mobileRxPackets * (20 + random.nextInt(200)));

            // MEASUREMENT_WIFI_RX_BYTES
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_RX_BYTES,wifiRxPackets * (20 + random.nextInt(200)));

            // MEASUREMENT_WIFI_TX_BYTES
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_TX_BYTES,wifiTxPackets * (20 + random.nextInt(200)));

            // MEASUREMENT_BLUETOOTH_RX_BYTES
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_RX_BYTES,bleRxPackets * (20 + random.nextInt(20)));

            // MEASUREMENT_BLUETOOTH_TX_BYTES
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_TX_BYTES,bleTxPackets * (20 + random.nextInt(20)));

            // MEASUREMENT_MOBILE_RX_PACKETS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_RX_PACKETS,mobileRxPackets);

            // MEASUREMENT_MOBILE_TX_PACKETS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_MOBILE_TX_PACKETS,mobileTxPackets);

            // MEASUREMENT_WIFI_RX_PACKETS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_RX_PACKETS,wifiRxPackets);

            // MEASUREMENT_WIFI_TX_PACKETS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_WIFI_TX_PACKETS,wifiTxPackets);

            // MEASUREMENT_BLUETOOTH_RX_PACKETS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_RX_PACKETS,bleRxPackets);

            // MEASUREMENT_BLUETOOTH_TX_PACKETS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_BLUETOOTH_TX_PACKETS,bleTxPackets);

            // TIMER_MOBILE_RADIO_ACTIVE
            uidWriter.addTimer(UidHealthStats.TIMER_MOBILE_RADIO_ACTIVE,
                    random.nextInt(3),
                    usedUpTime - random.nextInt(5000));

            // MEASUREMENT_USER_CPU_TIME_MS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_USER_CPU_TIME_MS,usedUpTime);

            // MEASUREMENT_SYSTEM_CPU_TIME_MS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_SYSTEM_CPU_TIME_MS,usedTime);

            // MEASUREMENT_CPU_POWER_MAMS
            uidWriter.addMeasurement(UidHealthStats.MEASUREMENT_CPU_POWER_MAMS, 0);
        }
    }

    /**
     * Writes the contents of a BatteryStats.Uid.Pid into a HealthStatsWriter.
     */
    public void writePid(HealthStatsWriter pidWriter, BatteryStats.Uid.Pid pid,ExtendedRandom random) {
        if (pid == null) {
            return;
        }

        // MEASUREMENT_WAKE_NESTING_COUNT
        pidWriter.addMeasurement(PidHealthStats.MEASUREMENT_WAKE_NESTING_COUNT, pid.mWakeNesting);

        // MEASUREMENT_WAKE_SUM_MS
        pidWriter.addMeasurement(PidHealthStats.MEASUREMENT_WAKE_SUM_MS, pid.mWakeSumMs);

        // MEASUREMENT_WAKE_START_MS
        pidWriter.addMeasurement(PidHealthStats.MEASUREMENT_WAKE_SUM_MS, pid.mWakeStartMs);
    }

    /**
     * Writes the contents of a BatteryStats.Uid.Proc into a HealthStatsWriter.
     */
    public void writeProc(HealthStatsWriter procWriter, BatteryStats.Uid.Proc proc) {
        // MEASUREMENT_USER_TIME_MS
        procWriter.addMeasurement(ProcessHealthStats.MEASUREMENT_USER_TIME_MS,
                proc.getUserTime(STATS_SINCE_CHARGED));

        // MEASUREMENT_SYSTEM_TIME_MS
        procWriter.addMeasurement(ProcessHealthStats.MEASUREMENT_SYSTEM_TIME_MS,
                proc.getSystemTime(STATS_SINCE_CHARGED));

        // MEASUREMENT_STARTS_COUNT
        procWriter.addMeasurement(ProcessHealthStats.MEASUREMENT_STARTS_COUNT,
                proc.getStarts(STATS_SINCE_CHARGED));

        // MEASUREMENT_CRASHES_COUNT
        procWriter.addMeasurement(ProcessHealthStats.MEASUREMENT_CRASHES_COUNT,
                proc.getNumCrashes(STATS_SINCE_CHARGED));

        // MEASUREMENT_ANR_COUNT
        procWriter.addMeasurement(ProcessHealthStats.MEASUREMENT_ANR_COUNT,
                proc.getNumAnrs(STATS_SINCE_CHARGED));

        // MEASUREMENT_FOREGROUND_MS
        procWriter.addMeasurement(ProcessHealthStats.MEASUREMENT_FOREGROUND_MS,
                proc.getForegroundTime(STATS_SINCE_CHARGED));
    }

    /**
     * Writes the contents of a BatteryStats.Uid.Pkg into a HealthStatsWriter.
     */
    public void writePkg(HealthStatsWriter pkgWriter, BatteryStats.Uid.Pkg pkg) {
        // STATS_SERVICES
        for (final Map.Entry<String,? extends BatteryStats.Uid.Pkg.Serv> entry:
                pkg.getServiceStats().entrySet()) {
            final HealthStatsWriter writer = new HealthStatsWriter(ServiceHealthStats.CONSTANTS);
            writeServ(writer, entry.getValue());
            pkgWriter.addStats(PackageHealthStats.STATS_SERVICES, entry.getKey(), writer);
        }

        // MEASUREMENTS_WAKEUP_ALARMS_COUNT
        for (final Map.Entry<String,? extends BatteryStats.Counter> entry:
                pkg.getWakeupAlarmStats().entrySet()) {
            final BatteryStats.Counter counter = entry.getValue();
            if (counter != null) {
                pkgWriter.addMeasurements(PackageHealthStats.MEASUREMENTS_WAKEUP_ALARMS_COUNT,
                        entry.getKey(), counter.getCountLocked(STATS_SINCE_CHARGED));
            }
        }
    }

    /**
     * Writes the contents of a BatteryStats.Uid.Pkg.Serv into a HealthStatsWriter.
     */
    public void writeServ(HealthStatsWriter servWriter, BatteryStats.Uid.Pkg.Serv serv) {
        // MEASUREMENT_START_SERVICE_COUNT
        servWriter.addMeasurement(ServiceHealthStats.MEASUREMENT_START_SERVICE_COUNT,
                serv.getStarts(STATS_SINCE_CHARGED));

        // MEASUREMENT_LAUNCH_COUNT
        servWriter.addMeasurement(ServiceHealthStats.MEASUREMENT_LAUNCH_COUNT,
                serv.getLaunches(STATS_SINCE_CHARGED));
    }

    /**
     * Adds a BatteryStats.Timer into a HealthStatsWriter. Safe to pass a null timer.
     */
    private void addTimer(HealthStatsWriter writer, int key, int count,long time) {
        writer.addTimer(key, count, time);
    }

    /**
     * Adds a named BatteryStats.Timer into a HealthStatsWriter. Safe to pass a null timer.
     */
    private void addTimers(HealthStatsWriter writer, int key, String name,
                           TimerStat stat) {
        writer.addTimers(key, name,stat);
    }
}
