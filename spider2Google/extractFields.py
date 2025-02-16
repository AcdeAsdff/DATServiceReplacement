import javalang
from javalang.tree import *
import aidlProcessor.parse
a = """public static final class HdrCapabilities implements Parcelable {
        /**
         * Invalid luminance value.
         */
        public static final float INVALID_LUMINANCE = -1;
        /**
         * Invalid HDR type value.
         */
        public static final int HDR_TYPE_INVALID = -1;
        /**
         * Dolby Vision high dynamic range (HDR) display.
         */
        public static final int HDR_TYPE_DOLBY_VISION = 1;
        /**
         * HDR10 display.
         */
        public static final int HDR_TYPE_HDR10 = 2;
        /**
         * Hybrid Log-Gamma HDR display.
         */
        public static final int HDR_TYPE_HLG = 3;

        /**
         * HDR10+ display.
         */
        public static final int HDR_TYPE_HDR10_PLUS = 4;

        /** @hide */
        public static final int[] HDR_TYPES = {
                HDR_TYPE_DOLBY_VISION,
                HDR_TYPE_HDR10,
                HDR_TYPE_HLG,
                HDR_TYPE_HDR10_PLUS
        };

        /** @hide */
        @IntDef(prefix = { "HDR_TYPE_" }, value = {
                HDR_TYPE_INVALID,
                HDR_TYPE_DOLBY_VISION,
                HDR_TYPE_HDR10,
                HDR_TYPE_HLG,
                HDR_TYPE_HDR10_PLUS,
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface HdrType {}

        private @HdrType int[] mSupportedHdrTypes = new int[0];
        private float mMaxLuminance = INVALID_LUMINANCE;
        private float mMaxAverageLuminance = INVALID_LUMINANCE;
        private float mMinLuminance = INVALID_LUMINANCE;

        /**
         * @hide
         */
        public HdrCapabilities() {
        }

        /**
         * @hide
         */
        @UnsupportedAppUsage(maxTargetSdk = Build.VERSION_CODES.R, trackingBug = 170729553)
        public HdrCapabilities(int[] supportedHdrTypes, float maxLuminance,
                float maxAverageLuminance, float minLuminance) {
            mSupportedHdrTypes = supportedHdrTypes;
            Arrays.sort(mSupportedHdrTypes);
            mMaxLuminance = maxLuminance;
            mMaxAverageLuminance = maxAverageLuminance;
            mMinLuminance = minLuminance;
        }

        /**
         * Gets the supported HDR types of this display.
         * Returns empty array if HDR is not supported by the display.
         *
         * @deprecated use {@link Display#getMode()}
         * and {@link Mode#getSupportedHdrTypes()} instead
         */
        @Deprecated
        @HdrType
        public int[] getSupportedHdrTypes() {
            return Arrays.copyOf(mSupportedHdrTypes, mSupportedHdrTypes.length);
        }
        /**
         * Returns the desired content max luminance data in cd/m2 for this display.
         */
        public float getDesiredMaxLuminance() {
            return mMaxLuminance;
        }
        /**
         * Returns the desired content max frame-average luminance data in cd/m2 for this display.
         */
        public float getDesiredMaxAverageLuminance() {
            return mMaxAverageLuminance;
        }
        /**
         * Returns the desired content min luminance data in cd/m2 for this display.
         */
        public float getDesiredMinLuminance() {
            return mMinLuminance;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof HdrCapabilities)) {
                return false;
            }
            HdrCapabilities that = (HdrCapabilities) other;

            return Arrays.equals(mSupportedHdrTypes, that.mSupportedHdrTypes)
                && mMaxLuminance == that.mMaxLuminance
                && mMaxAverageLuminance == that.mMaxAverageLuminance
                && mMinLuminance == that.mMinLuminance;
        }

        @Override
        public int hashCode() {
            int hash = 23;
            hash = hash * 17 + Arrays.hashCode(mSupportedHdrTypes);
            hash = hash * 17 + Float.floatToIntBits(mMaxLuminance);
            hash = hash * 17 + Float.floatToIntBits(mMaxAverageLuminance);
            hash = hash * 17 + Float.floatToIntBits(mMinLuminance);
            return hash;
        }

        public static final @android.annotation.NonNull Creator<HdrCapabilities> CREATOR = new Creator<HdrCapabilities>() {
            @Override
            public HdrCapabilities createFromParcel(Parcel source) {
                return new HdrCapabilities(source);
            }

            @Override
            public HdrCapabilities[] newArray(int size) {
                return new HdrCapabilities[size];
            }
        };

        private HdrCapabilities(Parcel source) {
            readFromParcel(source);
        }

        /**
         * @hide
         */
        public void readFromParcel(Parcel source) {
            int types = source.readInt();
            mSupportedHdrTypes = new int[types];
            for (int i = 0; i < types; ++i) {
                mSupportedHdrTypes[i] = source.readInt();
            }
            mMaxLuminance = source.readFloat();
            mMaxAverageLuminance = source.readFloat();
            mMinLuminance = source.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mSupportedHdrTypes.length);
            for (int i = 0; i < mSupportedHdrTypes.length; ++i) {
                dest.writeInt(mSupportedHdrTypes[i]);
            }
            dest.writeFloat(mMaxLuminance);
            dest.writeFloat(mMaxAverageLuminance);
            dest.writeFloat(mMinLuminance);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public String toString() {
            return "HdrCapabilities{"
                    + "mSupportedHdrTypes=" + Arrays.toString(mSupportedHdrTypes)
                    + ", mMaxLuminance=" + mMaxLuminance
                    + ", mMaxAverageLuminance=" + mMaxAverageLuminance
                    + ", mMinLuminance=" + mMinLuminance + '}';
        }

        /**
         * @hide
         */
        @NonNull
        public static String hdrTypeToString(int hdrType) {
            switch (hdrType) {
                case HDR_TYPE_DOLBY_VISION:
                    return "HDR_TYPE_DOLBY_VISION";
                case HDR_TYPE_HDR10:
                    return "HDR_TYPE_HDR10";
                case HDR_TYPE_HLG:
                    return "HDR_TYPE_HLG";
                case HDR_TYPE_HDR10_PLUS:
                    return "HDR_TYPE_HDR10_PLUS";
                default:
                    return "HDR_TYPE_INVALID";
            }
        }
    }"""

fieldDeclarations = []
fieldFind = []
setFields = []
toParse = javalang.parse.parse(a)
className = str(toParse.types[0].name)
for child in toParse.children:
    if child is PackageDeclaration:
        continue
    if isinstance(child, list):
        for lsItem in child:
            if isinstance(lsItem, Import):
                continue
            if isinstance(lsItem, ClassDeclaration):
                for clsDeclarationItem in lsItem.children[4]:
                    if isinstance(clsDeclarationItem, FieldDeclaration):
                        l = clsDeclarationItem.children
                        if not 'static' in l[1]:

                            assert isinstance(l[-1][0],VariableDeclarator)
                            varName = l[-1][0].name
                            typeString = aidlProcessor.parse.parseTypeString(l[3])
                            fieldDeclarations.append('public static Field %s_%s;'
                                                     %(className,varName)
                                                     )
                            fieldFind.append('%s_%s = XposedHelpers.findField(%s.class,"%s");//%s'
                                             %(className,varName,
                                               className,varName,
                                               typeString))
                            setFields.append('%s_%s.set(result,/*%s*/);'%(className,varName,typeString))

for l in fieldDeclarations:
    print(l)
print('static{')
for l in fieldFind:
    print(l)
print('}')
print('------------------------------')
for l in setFields:
    print(l)