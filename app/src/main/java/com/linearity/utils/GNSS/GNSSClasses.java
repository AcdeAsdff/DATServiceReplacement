package com.linearity.utils.GNSS;

import android.location.GnssClock;
import android.location.GnssMeasurement;
import android.location.GnssMeasurementsEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class GNSSClasses {

    public static final Constructor<GnssMeasurementsEvent> GnssMeasurementsEventConstructor =
            (Constructor<GnssMeasurementsEvent>)
                    XposedHelpers.findConstructorExact(GnssMeasurementsEvent.class,
                            int.class,
                            GnssClock.class,
                            List.class,
                            List.class,
                            boolean.class);

    public static final Constructor<GnssMeasurement> GnssMeasurementConstructor = (Constructor<GnssMeasurement>)
            XposedHelpers.findConstructorExact(GnssMeasurement.class);
    public static final Constructor<GnssClock> GnssClockConstructor =
            (Constructor<GnssClock>)
                    XposedHelpers.findConstructorExact(GnssClock.class);


    public static Field GnssClockField_mFlags = XposedHelpers.findField(GnssClock.class,"mFlags");//int
    public static Field GnssClockField_mLeapSecond = XposedHelpers.findField(GnssClock.class,"mLeapSecond");//int

    public static Field GnssClockField_mTimeNanos = XposedHelpers.findField(GnssClock.class,"mTimeNanos");//long
    public static Field GnssClockField_mTimeUncertaintyNanos = XposedHelpers.findField(GnssClock.class,"mTimeUncertaintyNanos");//double
    public static Field GnssClockField_mFullBiasNanos = XposedHelpers.findField(GnssClock.class,"mFullBiasNanos");//long
    public static Field GnssClockField_mBiasNanos = XposedHelpers.findField(GnssClock.class,"mBiasNanos");//double
    public static Field GnssClockField_mBiasUncertaintyNanos = XposedHelpers.findField(GnssClock.class,"mBiasUncertaintyNanos");//double
    public static Field GnssClockField_mDriftNanosPerSecond = XposedHelpers.findField(GnssClock.class,"mDriftNanosPerSecond");//double
    public static Field GnssClockField_mDriftUncertaintyNanosPerSecond = XposedHelpers.findField(GnssClock.class,"mDriftUncertaintyNanosPerSecond");//double
    public static Field GnssClockField_mHardwareClockDiscontinuityCount = XposedHelpers.findField(GnssClock.class,"mHardwareClockDiscontinuityCount");//int
    public static Field GnssClockField_mElapsedRealtimeNanos = XposedHelpers.findField(GnssClock.class,"mElapsedRealtimeNanos");//long
    public static Field GnssClockField_mElapsedRealtimeUncertaintyNanos = XposedHelpers.findField(GnssClock.class,"mElapsedRealtimeUncertaintyNanos");//double
    public static Field GnssClockField_mReferenceConstellationTypeForIsb = XposedHelpers.findField(GnssClock.class,"mReferenceConstellationTypeForIsb");//int
    public static Field GnssClockField_mReferenceCarrierFrequencyHzForIsb = XposedHelpers.findField(GnssClock.class,"mReferenceCarrierFrequencyHzForIsb");//double
    public static Field GnssClockField_mReferenceCodeTypeForIsb = XposedHelpers.findField(GnssClock.class,"mReferenceCodeTypeForIsb");//String

    public static Field GnssMeasurementField_mFlags = XposedHelpers.findField(GnssMeasurement.class,"mFlags");//int
    public static Field GnssMeasurementField_mSvid = XposedHelpers.findField(GnssMeasurement.class,"mSvid");//int
    public static Field GnssMeasurementField_mConstellationType = XposedHelpers.findField(GnssMeasurement.class,"mConstellationType");//int
    public static Field GnssMeasurementField_mTimeOffsetNanos = XposedHelpers.findField(GnssMeasurement.class,"mTimeOffsetNanos");//double
    public static Field GnssMeasurementField_mState = XposedHelpers.findField(GnssMeasurement.class,"mState");//int
    public static Field GnssMeasurementField_mReceivedSvTimeNanos = XposedHelpers.findField(GnssMeasurement.class,"mReceivedSvTimeNanos");//long
    public static Field GnssMeasurementField_mReceivedSvTimeUncertaintyNanos = XposedHelpers.findField(GnssMeasurement.class,"mReceivedSvTimeUncertaintyNanos");//long
    public static Field GnssMeasurementField_mCn0DbHz = XposedHelpers.findField(GnssMeasurement.class,"mCn0DbHz");//double
    public static Field GnssMeasurementField_mBasebandCn0DbHz = XposedHelpers.findField(GnssMeasurement.class,"mBasebandCn0DbHz");//double
    public static Field GnssMeasurementField_mPseudorangeRateMetersPerSecond = XposedHelpers.findField(GnssMeasurement.class,"mPseudorangeRateMetersPerSecond");//double
    public static Field GnssMeasurementField_mPseudorangeRateUncertaintyMetersPerSecond = XposedHelpers.findField(GnssMeasurement.class,"mPseudorangeRateUncertaintyMetersPerSecond");//double
    public static Field GnssMeasurementField_mAccumulatedDeltaRangeState = XposedHelpers.findField(GnssMeasurement.class,"mAccumulatedDeltaRangeState");//int
    public static Field GnssMeasurementField_mAccumulatedDeltaRangeMeters = XposedHelpers.findField(GnssMeasurement.class,"mAccumulatedDeltaRangeMeters");//double
    public static Field GnssMeasurementField_mAccumulatedDeltaRangeUncertaintyMeters = XposedHelpers.findField(GnssMeasurement.class,"mAccumulatedDeltaRangeUncertaintyMeters");//double
    public static Field GnssMeasurementField_mCarrierFrequencyHz = XposedHelpers.findField(GnssMeasurement.class,"mCarrierFrequencyHz");//float
    public static Field GnssMeasurementField_mCarrierCycles = XposedHelpers.findField(GnssMeasurement.class,"mCarrierCycles");//long
    public static Field GnssMeasurementField_mCarrierPhase = XposedHelpers.findField(GnssMeasurement.class,"mCarrierPhase");//double
    public static Field GnssMeasurementField_mCarrierPhaseUncertainty = XposedHelpers.findField(GnssMeasurement.class,"mCarrierPhaseUncertainty");//double
    public static Field GnssMeasurementField_mMultipathIndicator = XposedHelpers.findField(GnssMeasurement.class,"mMultipathIndicator");//int
    public static Field GnssMeasurementField_mSnrInDb = XposedHelpers.findField(GnssMeasurement.class,"mSnrInDb");//double
    public static Field GnssMeasurementField_mAutomaticGainControlLevelInDb = XposedHelpers.findField(GnssMeasurement.class,"mAutomaticGainControlLevelInDb");//double
    public static Field GnssMeasurementField_mCodeType = XposedHelpers.findField(GnssMeasurement.class,"mCodeType");//String
    public static Field GnssMeasurementField_mFullInterSignalBiasNanos = XposedHelpers.findField(GnssMeasurement.class,"mFullInterSignalBiasNanos");//double
    public static Field GnssMeasurementField_mFullInterSignalBiasUncertaintyNanos = XposedHelpers.findField(GnssMeasurement.class,"mFullInterSignalBiasUncertaintyNanos");//double
    public static Field GnssMeasurementField_mSatelliteInterSignalBiasNanos = XposedHelpers.findField(GnssMeasurement.class,"mSatelliteInterSignalBiasNanos");//double
    public static Field GnssMeasurementField_mSatelliteInterSignalBiasUncertaintyNanos = XposedHelpers.findField(GnssMeasurement.class,"mSatelliteInterSignalBiasUncertaintyNanos");//double
    public static Field GnssMeasurementField_mSatellitePvt = XposedHelpers.findField(GnssMeasurement.class,"mSatellitePvt");//SatellitePvt
    public static Field GnssMeasurementField_mReadOnlyCorrelationVectors = XposedHelpers.findField(GnssMeasurement.class,"mReadOnlyCorrelationVectors");//Collection<CorrelationVector>
}
