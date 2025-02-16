package com.linearity.utils.GNSS;

import static android.location.GnssStatus.CONSTELLATION_GPS;
import static com.linearity.utils.GNSS.GNSSClasses.*;
import static com.linearity.utils.LoggerUtils.LoggerLog;

import androidx.annotation.NonNull;
import android.location.GnssAutomaticGainControl;
import android.location.GnssClock;
import android.location.GnssMeasurement;
import android.location.GnssMeasurementsEvent;
import android.os.SystemClock;

import java.util.Collection;
import java.util.List;

public class GNSSConstruct {


    public static GnssMeasurementsEvent constructGnssMeasurementsEvent(int flag,
                                        @NonNull GnssClock clock,
                                        @NonNull List<GnssMeasurement> measurements,
                                        @NonNull List<GnssAutomaticGainControl> agcs,
                                        boolean isFullTracking){
        try {
            return GnssMeasurementsEventConstructor.newInstance(flag,clock,measurements,agcs,isFullTracking);
        }catch (Exception e){
            return null;
        }
    }

    public static GnssClock constructGnssClock(){
        try {
            GnssClock result = GnssClockConstructor.newInstance();
            GnssClockField_mFlags.set(result,1<<13 - 1);//int
            GnssClockField_mLeapSecond.set(result,1);//int
            GnssClockField_mTimeNanos.set(result,System.currentTimeMillis());//long
            GnssClockField_mTimeUncertaintyNanos.set(result,1);//double
            GnssClockField_mFullBiasNanos.set(result,1L);//long
            GnssClockField_mBiasNanos.set(result,0.01);//double
            GnssClockField_mBiasUncertaintyNanos.set(result,0.0001);//double
            GnssClockField_mDriftNanosPerSecond.set(result,1);//double
            GnssClockField_mDriftUncertaintyNanosPerSecond.set(result,0.001);//double
            GnssClockField_mHardwareClockDiscontinuityCount.set(result,1);//int
            GnssClockField_mElapsedRealtimeNanos.set(result,SystemClock.elapsedRealtimeNanos());//long
            GnssClockField_mElapsedRealtimeUncertaintyNanos.set(result,0.002);//double
            GnssClockField_mReferenceConstellationTypeForIsb.set(result,1);//int
            GnssClockField_mReferenceCarrierFrequencyHzForIsb.set(result,0.0001);//double
            GnssClockField_mReferenceCodeTypeForIsb.set(result,"X");//String
            return result;
        }catch (Exception e){
            return null;
        }
    }

    public static GnssMeasurement constructGnssMeasurement(int satelliteID, Object satellitePvt, Collection<?>/*<CorrelationVector>*/ mReadOnlyCorrelationVectors){
        try {
            GnssMeasurement result = GnssMeasurementConstructor.newInstance();
            GnssMeasurementField_mFlags.set(result,(1 << 14) + (1 << 15) + (1 << 20) + (1 << 21));//int
            GnssMeasurementField_mSvid.set(result,satelliteID);//int
            GnssMeasurementField_mConstellationType.set(result,CONSTELLATION_GPS);//int
            GnssMeasurementField_mTimeOffsetNanos.set(result,0.001);//double
            GnssMeasurementField_mState.set(result,1<<11);//int
            GnssMeasurementField_mReceivedSvTimeNanos.set(result,100L);//long
            GnssMeasurementField_mReceivedSvTimeUncertaintyNanos.set(result,1L);//long
            GnssMeasurementField_mCn0DbHz.set(result,30.);//double
            GnssMeasurementField_mBasebandCn0DbHz.set(result,30.);//double
            GnssMeasurementField_mPseudorangeRateMetersPerSecond.set(result,1.);//double
            GnssMeasurementField_mPseudorangeRateUncertaintyMetersPerSecond.set(result,.001);//double
            GnssMeasurementField_mAccumulatedDeltaRangeState.set(result,1);//int
            GnssMeasurementField_mAccumulatedDeltaRangeMeters.set(result,.1);//double
            GnssMeasurementField_mAccumulatedDeltaRangeUncertaintyMeters.set(result,.1);//double
            GnssMeasurementField_mCarrierFrequencyHz.set(result,1.f);//float
            GnssMeasurementField_mCarrierCycles.set(result,1L);//long
            GnssMeasurementField_mCarrierPhase.set(result,.001);//double
            GnssMeasurementField_mCarrierPhaseUncertainty.set(result,.001);//double
            GnssMeasurementField_mMultipathIndicator.set(result,1);//int
            GnssMeasurementField_mSnrInDb.set(result,.001);//double
            GnssMeasurementField_mAutomaticGainControlLevelInDb.set(result,0.001);//double
            GnssMeasurementField_mCodeType.set(result,"X");//String
            GnssMeasurementField_mFullInterSignalBiasNanos.set(result,1.);//double
            GnssMeasurementField_mFullInterSignalBiasUncertaintyNanos.set(result,0.001);//double
            GnssMeasurementField_mSatelliteInterSignalBiasNanos.set(result,1.);//double
            GnssMeasurementField_mSatelliteInterSignalBiasUncertaintyNanos.set(result,0.001);//double
            GnssMeasurementField_mSatellitePvt.set(result,satellitePvt);//SatellitePvt
            GnssMeasurementField_mReadOnlyCorrelationVectors.set(result,mReadOnlyCorrelationVectors);//Collection<CorrelationVector>
            return result;
        }catch (Exception e){
            LoggerLog(e);
            return null;
        }
    }
}
