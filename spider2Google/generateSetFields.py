a = """private int mFlags;
    private int mSvid;
    private int mConstellationType;
    private double mTimeOffsetNanos;
    private int mState;
    private long mReceivedSvTimeNanos;
    private long mReceivedSvTimeUncertaintyNanos;
    private double mCn0DbHz;
    private double mBasebandCn0DbHz;
    private double mPseudorangeRateMetersPerSecond;
    private double mPseudorangeRateUncertaintyMetersPerSecond;
    private int mAccumulatedDeltaRangeState;
    private double mAccumulatedDeltaRangeMeters;
    private double mAccumulatedDeltaRangeUncertaintyMeters;
    private float mCarrierFrequencyHz;
    private long mCarrierCycles;
    private double mCarrierPhase;
    private double mCarrierPhaseUncertainty;
    private int mMultipathIndicator;
    private double mSnrInDb;
    private double mAutomaticGainControlLevelInDb;
    @NonNull private String mCodeType;
    private double mFullInterSignalBiasNanos;
    private double mFullInterSignalBiasUncertaintyNanos;
    private double mSatelliteInterSignalBiasNanos;
    private double mSatelliteInterSignalBiasUncertaintyNanos;
    @Nullable private SatellitePvt mSatellitePvt;
    @Nullable private Collection<CorrelationVector> mReadOnlyCorrelationVectors;""".replace('    ', '').replace(';', '').split('\n')

getFields = []
setFields = []
clazz = 'GnssMeasurement.class'
refFieldNameFormat = "GnssMeasurementField_%s"

for l in a:
    parts = l.split(' ')
    typeVar = ''
    extra = ''
    if parts[-2] == 'int':
        typeVar = 'Int'
    elif parts[-2] == 'long':
        typeVar = 'Long'
    elif parts[-2] == 'short':
        typeVar = 'Short'
    elif parts[-2] == 'double':
        typeVar = 'Double'
    elif parts[-2] == 'float':
        typeVar = 'Float'
    elif parts[-2] == 'char':
        typeVar = 'Char'
    elif parts[-2] == 'byte':
        typeVar = 'Byte'
    else:
        typeVar = 'Object'
    extra = '//' + parts[-2]
    refFieldName = refFieldNameFormat % (parts[-1])
    getFields.append(
        "public static Field " + refFieldName +

        ' = XposedHelpers.findField(%s,"%s");' % (
             clazz, parts[-1]) + extra)
    setFields.append(
        refFieldName + ".set(result,);"+extra
    )
for l in getFields:
    print(l)

print('\n=========================================\n')

for l in setFields:
    print(l)