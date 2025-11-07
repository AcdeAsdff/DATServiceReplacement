package com.linearity.utils.AndroidFakes.Battery;

import android.os.BatteryStats;
import android.os.health.HealthStatsParceler;
import android.os.health.HealthStatsWriter;
import android.os.health.UidHealthStats;

public class HealthStatsParcelerGen {



    public static HealthStatsParceler getHealthStatsForUidLocked(int requestUid) {
        final HealthStatsBatteryStatsWriter writer = new HealthStatsBatteryStatsWriter();
        final HealthStatsWriter uidWriter = new HealthStatsWriter(UidHealthStats.CONSTANTS);
//        final BatteryStats.Uid uid = mStats.getUidStats().get(requestUid);
//        if (uid != null) {
//            writer.writeUid(uidWriter, mStats, uid);
//        }
        writer.writeUid(uidWriter, requestUid);
        return new HealthStatsParceler(uidWriter);
    }

}
