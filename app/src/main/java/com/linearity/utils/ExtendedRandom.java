package com.linearity.utils;

import static android.content.pm.ApplicationInfo.FLAG_ALLOW_BACKUP;
import static android.content.pm.ApplicationInfo.FLAG_EXTERNAL_STORAGE;
import static android.content.pm.ApplicationInfo.FLAG_INSTALLED;
import static android.content.pm.ApplicationInfo.FLAG_PERSISTENT;
import static android.content.pm.ApplicationInfo.FLAG_SUPPORTS_SMALL_SCREENS;
import static android.content.pm.ApplicationInfo.FLAG_SYSTEM;
import static com.linearity.datservicereplacement.androidhooking.com.android.server.pm.PackageManagerUtils.getPackageName;
import static com.linearity.utils.AndroidConsts.BluetoothUuid.UUID_BYTES_128_BIT;
import static com.linearity.utils.AndroidConsts.BluetoothUuid.UUID_BYTES_16_BIT;
import static com.linearity.utils.AndroidConsts.BluetoothUuid.UUID_BYTES_32_BIT;
import static com.linearity.utils.PublicSeed.publicSeed;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.Attribution;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.util.SparseArray;

import androidx.annotation.IntRange;

import com.linearity.utils.AndroidConsts.BluetoothUuid;
import com.linearity.utils.FakeClass.java.util.CantUseArrayList;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ExtendedRandom{
    /**
     * set to non-zero to confuse app.
     */
    public static final long seedOffset = publicSeed;
    public static final ExtendedRandom SYSTEM_INSTANCE = new ExtendedRandom(ExtendedRandom.seedOffset);
    private Random random;
    private final long seed;
    public ExtendedRandom(){
        this(System.currentTimeMillis());
    }

    public ExtendedRandom(long seed){
        this.seed = seed;
        this.random = new Random(seed + seedOffset);
    }

    public void reset(){
        this.random = new Random(seed + seedOffset);
    }
    public ExtendedRandom nextExtendedRandom(){
        return new ExtendedRandom(random.nextLong());
    }
    public boolean nextBoolean(){
        return random.nextBoolean();
    }
    public int nextInt(){
        return random.nextInt();
    }
    public int nextInt(int bound){
        if (bound == 1 || bound == 0){
            return 0;
        }
        return random.nextInt(bound);
    }
    public long nextLong(){
        return random.nextLong();
    }
    public float nextFloat(){
        return random.nextFloat();
    }
    public double nextDouble(){
        return random.nextDouble();
    }
    public double nextGaussian(){
        return random.nextGaussian();
    }
    public int nextSign(){
        return nextBoolean()?-1:1;
    }
    public long nextSignLong(){
        return nextBoolean()?-1L:1L;
    }
    public int[] nextIntArr(int length){
        int[] result = new int[length];
        for (int i=0;i<length;i++){
            result[i] = random.nextInt();
        }
        return result;
    }
    public long[] nextLongArr(int length,long bound){
        long[] result = new long[length];
        for (int i=0;i<length;i++){
            result[i] = random.nextLong()%bound;
        }
        return result;
    }
    public String[] randomStrArr(int length){
        String[] result = new String[length];
        for (int i=0;i<length;i++){
            result[i] = nextString(random.nextInt(10)+3);
        }
        return result;
    }

    public String nextString(int length) {
//        int minLength = length/2;
//        int exactLength = nextInt(length - minLength) + minLength + 1;
        String str = "abpqmnoEFGHIJrstRSTUVWujkl56YKLX234ZvwxyzABCDcdefghi01MNOPQ789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public UUID nextUUID() {

        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        randomBytes[6]  &= 0x0f;  /* clear version        */
        randomBytes[6]  |= 0x40;  /* set to version 4     */
        randomBytes[8]  &= 0x3f;  /* clear variant        */
        randomBytes[8]  |= (byte) 0x80;  /* set to IETF variant  */

        long msb = 0;
        long lsb = 0;
//        assert randomBytes.length == 16 : "data must be 16 bytes in length";
        for (int i=0; i<8; i++)
            msb = (msb << 8) | (randomBytes[i] & 0xff);
        for (int i=8; i<16; i++)
            lsb = (lsb << 8) | (randomBytes[i] & 0xff);

        return new UUID(msb,lsb);
    }
    public UUID nextUUID_16bit(){
        return BluetoothUuid.parseUuidFrom(this.nextBytes(2)).getUuid();
    }
    public UUID nextUUID_32bit(){
        return BluetoothUuid.parseUuidFrom(this.nextBytes(4)).getUuid();
    }
    public static final int[] UUIDLength = new int[]{
            UUID_BYTES_128_BIT,UUID_BYTES_16_BIT,UUID_BYTES_32_BIT
    };
    public UUID nextUUID_16_32_128bit(){
        return BluetoothUuid.parseUuidFrom(this.nextBytes(this.pickFromArray(UUIDLength))).getUuid();
    }
    public String nextPackageName(String split){
        int dots = nextInt(6)+1;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<dots;i++){
            sb.append(nextString(nextInt(5)+3)).append(split);
        }
        sb.append(nextString(nextInt(5)+3));
        return sb.toString();
    }
    public double nextSmallDouble(double max){
        double result = 0;
        for (int i=0;i<5;i++){
            max *= nextInt(10)*0.001;
            result += max;
        }
        result *= (nextBoolean()?-1:1);
        return result;
    }
    public String nextRandomHexUpper(int length){
//        int minLength = length/2;
//        int exactLength = nextInt(length - minLength) + minLength + 1;
        String str="0123456789ABCDEF";
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public String nextRandomHexLower(int length){
//        int minLength = length/2;
//        int exactLength = nextInt(length - minLength) + minLength + 1;
        String str="0123456789abcdef";
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public String nextRandomDecimal(int length){
//        int minLength = length/2;
//        int exactLength = nextInt(length - minLength) + minLength + 1;
        String str="0123456789";
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public String next0or1(){
        return nextBoolean()?"0":"1";
    }

    public void nextBytes(byte[] write){
        random.nextBytes(write);
    }
    public byte[] nextBytes(int length){
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }
    public String nextMacAddrStr(){
        return nextRandomHexUpper(2) +
                ":" + nextRandomHexUpper(2) +
                ":" + nextRandomHexUpper(2) +
                ":" + nextRandomHexUpper(2) +
                ":" + nextRandomHexUpper(2) +
                ":" + nextRandomHexUpper(2);
    }

    public String nextIPV4(){
        return nextInt(255) +
                ":" + nextInt(255) +
                ":" + nextInt(255) +
                ":" + nextInt(255);
    }

    public <T> T pickFromArray(T[] array){
        return array[random.nextInt(array.length)];
    }
    public int pickFromArray(int[] array){
        return array[random.nextInt(array.length)];
    }
    public long pickFromArray(long[] array){
        return array[random.nextInt(array.length)];
    }
    public byte pickFromArray(byte[] array){
        return array[random.nextInt(array.length)];
    }
    public char pickFromArray(char[] array){
        return array[random.nextInt(array.length)];
    }
    public double pickFromArray(double[] array){
        return array[random.nextInt(array.length)];
    }
    public float pickFromArray(float[] array){
        return array[random.nextInt(array.length)];
    }
    public short pickFromArray(short[] array){
        return array[random.nextInt(array.length)];
    }
    public boolean pickFromArray(boolean[] array){
        return array[random.nextInt(array.length)];
    }
    public byte[] nextValidIPv6Bytes(){
        byte[] result = new byte[16];
        return result;
    }
    public int randomFlag(@IntRange(from=0,to=32) int maxBitDigit){
        int result = 0;
        for (int i=0;i<maxBitDigit;i++){
            if (random.nextBoolean()){
                result += (1<<i);
            }
        }
        return result;
    }

    public <T> T[] nextSubArray(T[] array){
        T[] result = (T[]) new Object[random.nextInt(array.length)];
        T[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public char[] nextSubArray(char[] array){
        char[] result = new char[random.nextInt(array.length)];
        char[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public boolean[] nextSubArray(boolean[] array){
        boolean[] result = new boolean[random.nextInt(array.length)];
        boolean[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public float[] nextSubArray(float[] array){
        float[] result = new float[random.nextInt(array.length)];
        float[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public double[] nextSubArray(double[] array){
        double[] result = new double[random.nextInt(array.length)];
        double[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public short[] nextSubArray(short[] array){
        short[] result = new short[random.nextInt(array.length)];
        short[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public long[] nextSubArray(long[] array){
        long[] result = new long[random.nextInt(array.length)];
        long[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public int[] nextSubArray(int[] array){
        int[] result = new int[random.nextInt(array.length)];
        int[] cloneArray = Arrays.copyOf(array,array.length);
        shuffleArray(cloneArray);
        System.arraycopy(cloneArray, 0, result, 0, result.length);
        return result;
    }
    public void shuffleArray(Object[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Object a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public int pickBitMasks(int[] bitmasks){
        int result = 0;
        for (int bitmask:bitmasks){
            if (this.nextBoolean()){
                result += bitmask;
            }
        }
        return result;
    }
    public long pickBitMasks(long[] bitmasks){
        long result = 0;
        for (long bitmask:bitmasks){
            if (this.nextBoolean()){
                result += bitmask;
            }
        }
        return result;
    }
    public void shuffleArray(double[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            double a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(char[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            char a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(float[] ar)
    {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);
            // Simple swap
            float a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(byte[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            byte a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(long[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            long a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(int[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(short[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            short a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public void shuffleArray(boolean[] ar)
    {
        Random rnd = random;
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            boolean a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    public static final ServiceInfo[] emptyServiceInfo = new ServiceInfo[0];
    public static final ActivityInfo[] emptyActivityInfo = new ActivityInfo[0];
    public static final ProviderInfo[] emptyProviderInfo = new ProviderInfo[0];
    public static final InstrumentationInfo[] emptyInstrumentationInfo = new InstrumentationInfo[0];
    public static final PermissionInfo[] emptyPermissionInfo = new PermissionInfo[0];
    public static final Attribution[] emptyAttributions = new Attribution[0];
    public static final Signature[] emptySignatures = new Signature[0];
    public static final ConfigurationInfo[] emptyConfigurationInfos = new ConfigurationInfo[0];
    public static final FeatureInfo[] emptyFeatureInfo = new FeatureInfo[0];
    public static final FeatureGroupInfo[] emptyFeatureGroupInfo = new FeatureGroupInfo[0];
    public static PackageInfo confusePackagePath(PackageInfo toConfuse, Class<?> signingDetailClass){

        return toConfuse;
    }
    @NotFinished
    public ApplicationInfo confuseApplicationInfo(ApplicationInfo toConfuse){
//        ExtendedRandom random = new ExtendedRandom(Math.abs(toConfuse.packageName.hashCode()));
//        XposedHelpers.setObjectField(toConfuse,"targetActivity",null);
//        if (toConfuse.packageName.startsWith("com.tencent.")){
//            for (Field f:toConfuse.getClass().getFields()){
//                if (Modifier.isStatic(f.getModifiers())){continue;}
//                LoggerLog(f.getName() + ":" + XposedHelpers.getObjectField(toConfuse,f.getName()));
//            }
//            LoggerLog("----------------");
//        }
        XposedHelpers.setObjectField(toConfuse,"processName", nextString(20));
        XposedHelpers.setObjectField(toConfuse,"permission", nextString(20));
        XposedHelpers.setObjectField(toConfuse,"className", nextString(20));
        XposedHelpers.setObjectField(toConfuse,"descriptionRes",0);
        XposedHelpers.setObjectField(toConfuse,"theme",0);
        XposedHelpers.setObjectField(toConfuse,"manageSpaceActivityName",null);
        XposedHelpers.setObjectField(toConfuse,"backupAgentName",null);
        XposedHelpers.setObjectField(toConfuse,"dataExtractionRulesRes",0);
        XposedHelpers.setObjectField(toConfuse,"crossProfile",false);
        XposedHelpers.setObjectField(toConfuse,"uiOptions",0);
        XposedHelpers.setObjectField(toConfuse,"nonLocalizedLabel",null);
        XposedHelpers.setObjectField(toConfuse,"metaData",null);
        XposedHelpers.setObjectField(toConfuse,"zygotePreloadName", nextString(nextInt(20)+5));
        XposedHelpers.setObjectField(toConfuse,"showUserIcon",(nextBoolean()?-1:1)*nextInt(10001));
        XposedHelpers.setObjectField(toConfuse,"logo",(nextBoolean()?-1:1)*nextInt(10001));
        XposedHelpers.setObjectField(toConfuse,"labelRes",(nextBoolean()?-1:1)*nextInt(Integer.MAX_VALUE-5));
        int flags = FLAG_SYSTEM | FLAG_PERSISTENT | FLAG_INSTALLED | FLAG_ALLOW_BACKUP | FLAG_SUPPORTS_SMALL_SCREENS | FLAG_EXTERNAL_STORAGE;
        XposedHelpers.setObjectField(toConfuse,"flags",flags);
        XposedHelpers.setObjectField(toConfuse,"requiresSmallestWidthDp",0);
        XposedHelpers.setObjectField(toConfuse,"compatibleWidthLimitDp",0);
        XposedHelpers.setObjectField(toConfuse,"largestWidthLimitDp", Integer.MAX_VALUE);
        XposedHelpers.setObjectField(toConfuse,"maxAspectRatio",Float.MAX_VALUE);
        XposedHelpers.setObjectField(toConfuse,"minAspectRatio",0.f);
        XposedHelpers.setObjectField(toConfuse,"volumeUuid", nextUUID().toString());
        XposedHelpers.setObjectField(toConfuse,"storageUuid", nextUUID());
        XposedHelpers.setObjectField(toConfuse,"sourceDir", nextString(50));

        XposedHelpers.setObjectField(toConfuse,"publicSourceDir", nextString(50));
        XposedHelpers.setObjectField(toConfuse,"scanPublicSourceDir", nextString(50));
        XposedHelpers.setObjectField(toConfuse,"splitPublicSourceDirs",new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});

        XposedHelpers.setObjectField(toConfuse,"scanSourceDir", nextString(50));
        XposedHelpers.setObjectField(toConfuse,"splitNames",new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"splitSourceDirs",new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"splitDependencies",new SparseArray<int[]>(0));
        XposedHelpers.setObjectField(toConfuse,"resourceDirs",new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"overlayPaths",new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"seInfo", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"seInfoUser", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"sharedLibraryFiles",nextBoolean()?null:new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"sharedLibraryInfos", nextBoolean()?null: CantUseArrayList.INSTANCE);

//        XposedHelpers.setObjectField(toConfuse,"dataDir", nextString(nextInt(10)+5));
//        XposedHelpers.setObjectField(toConfuse,"deviceProtectedDataDir", nextString(nextInt(10)+5));
//        XposedHelpers.setObjectField(toConfuse,"credentialProtectedDataDir", nextString(nextInt(10)+5));

        XposedHelpers.setObjectField(toConfuse,"nativeLibraryDir", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"secondaryNativeLibraryDir", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"nativeLibraryRootRequiresIsa", nextBoolean());

        XposedHelpers.setObjectField(toConfuse,"enabled", true);
        XposedHelpers.setObjectField(toConfuse,"enabledSetting", nextInt());
        XposedHelpers.setObjectField(toConfuse,"nativeLibraryRootDir", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"secondaryNativeLibraryDir", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"primaryCpuAbi", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"secondaryCpuAbi", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"taskAffinity", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"uid", 0);
        XposedHelpers.setObjectField(toConfuse,"minSdkVersion", 0);
        XposedHelpers.setObjectField(toConfuse,"longVersionCode", nextLong());
        XposedHelpers.setObjectField(toConfuse,"versionCode", nextInt());
        XposedHelpers.setObjectField(toConfuse,"privateFlags", nextInt());
        XposedHelpers.setObjectField(toConfuse,"privateFlagsExt", nextInt());
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersion", nextInt());
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersionCodename", String.valueOf(nextInt(12)));
        XposedHelpers.setObjectField(toConfuse,"installLocation", nextInt());
        XposedHelpers.setObjectField(toConfuse,"networkSecurityConfigRes", nextInt());
        XposedHelpers.setObjectField(toConfuse,"targetSandboxVersion", nextInt(5));
        XposedHelpers.setObjectField(toConfuse,"targetSdkVersion", nextInt(32));
        XposedHelpers.setObjectField(toConfuse,"appComponentFactory", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"iconRes", nextInt());
        XposedHelpers.setObjectField(toConfuse,"roundIconRes", nextInt());
        XposedHelpers.setObjectField(toConfuse,"category", nextInt());
        XposedHelpers.setObjectField(toConfuse,"classLoaderName", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"splitClassLoaderNames", new String[]{nextString(nextInt(10)+5), nextString(nextInt(10)+5)});
        XposedHelpers.setObjectField(toConfuse,"hiddenUntilInstalled", false);
        XposedHelpers.setObjectField(toConfuse,"zygotePreloadName", nextString(nextInt(10)+5));
        XposedHelpers.setObjectField(toConfuse,"gwpAsanMode", nextInt());
        XposedHelpers.setObjectField(toConfuse,"mHiddenApiPolicy", nextInt());
        return toConfuse;
    }
    public static final Class<?> signingDetailClass = XposedHelpers.findClass("android.content.pm.PackageParser$SigningDetails",XposedBridge.BOOTCLASSLOADER);
    @NotFinished
    public PackageInfo confusePackageInfo(PackageInfo toConfuse){

        XposedHelpers.setIntField(toConfuse,"versionCode", nextInt());
        XposedHelpers.setIntField(toConfuse,"versionCodeMajor", nextInt());
        XposedHelpers.setIntField(toConfuse,"versionCodeMajor", nextInt());
        XposedHelpers.setObjectField(toConfuse,"versionName", nextString(10));
        XposedHelpers.setIntField(toConfuse,"baseRevisionCode", nextInt());
        XposedHelpers.setObjectField(toConfuse,"splitRevisionCodes", nextIntArr(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"sharedUserId", "0");
        XposedHelpers.setIntField(toConfuse,"sharedUserLabel", 0);
        XposedHelpers.setObjectField(toConfuse,"applicationInfo", confuseApplicationInfo(toConfuse.applicationInfo));
        XposedHelpers.setLongField(toConfuse,"firstInstallTime", nextLong());
        XposedHelpers.setLongField(toConfuse,"lastUpdateTime", nextLong());
        XposedHelpers.setObjectField(toConfuse,"gids", nextIntArr(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"activities", emptyActivityInfo);
        XposedHelpers.setObjectField(toConfuse,"receivers", emptyActivityInfo);
        XposedHelpers.setObjectField(toConfuse,"services", emptyServiceInfo);
        XposedHelpers.setObjectField(toConfuse,"providers", emptyProviderInfo);
        XposedHelpers.setObjectField(toConfuse,"instrumentation", emptyInstrumentationInfo);
        XposedHelpers.setObjectField(toConfuse,"permissions", emptyPermissionInfo);
        int l = nextInt(5)+1;
        XposedHelpers.setObjectField(toConfuse,"requestedPermissions", randomStrArr(l));
        XposedHelpers.setObjectField(toConfuse,"requestedPermissionsFlags", nextIntArr(l));
        XposedHelpers.setObjectField(toConfuse,"attributions", emptyAttributions);
        XposedHelpers.setObjectField(toConfuse,"signatures", emptySignatures);
        SigningInfo signingInfo = new SigningInfo();
        Object signingDetail = XposedHelpers.newInstance(signingDetailClass,emptySignatures,nextInt());
        XposedHelpers.setObjectField(signingInfo,"mSigningDetails", signingDetail);
        XposedHelpers.setObjectField(toConfuse,"signingInfo", signingInfo);
        XposedHelpers.setObjectField(toConfuse,"configPreferences", emptyConfigurationInfos);
        XposedHelpers.setObjectField(toConfuse,"reqFeatures", emptyFeatureInfo);
        XposedHelpers.setObjectField(toConfuse,"featureGroups", emptyFeatureGroupInfo);
        XposedHelpers.setBooleanField(toConfuse,"isStub", nextBoolean());
        XposedHelpers.setBooleanField(toConfuse,"coreApp", false);
        XposedHelpers.setBooleanField(toConfuse,"requiredForAllUsers", false);
        XposedHelpers.setObjectField(toConfuse,"restrictedAccountType", nextString(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"requiredAccountType", nextString(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"overlayTarget", nextString(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"targetOverlayableName", nextString(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"overlayCategory", nextString(nextInt(5)+2));
        XposedHelpers.setIntField(toConfuse,"overlayPriority", nextInt());
        XposedHelpers.setBooleanField(toConfuse,"mOverlayIsStatic", nextBoolean());
        XposedHelpers.setIntField(toConfuse,"compileSdkVersion", nextInt(32));
        XposedHelpers.setObjectField(toConfuse,"overlayCategory", nextString(nextInt(5)+2));
        XposedHelpers.setObjectField(toConfuse,"compileSdkVersionCodename", nextString(nextInt(5)+2));
        XposedHelpers.setBooleanField(toConfuse,"isApex", nextBoolean());
        return toConfuse;
    }
    public byte nextByte() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0];
    }
    public static ExtendedRandom generateBasicFromUid(int callingUid,String salt){
        ExtendedRandom ret = new ExtendedRandom(callingUid);
        ret = new ExtendedRandom(ret.nextLong() + salt.hashCode());
        String pkgName;
        pkgName = getPackageName(callingUid);
        ret.nextRandomDecimal(Math.abs(salt.length()/2 + salt.length()%2));
        if (!pkgName.isEmpty()){
            ret = new ExtendedRandom(ret.nextLong() + pkgName.hashCode());
        }
        return ret;
    }
    public static ExtendedRandom generateBasicFromUid(int callingUid,Object salt){
        ExtendedRandom ret = new ExtendedRandom(callingUid);
        ret = new ExtendedRandom(ret.nextLong() + salt.hashCode());
        String pkgName;
        pkgName = getPackageName(callingUid);
        ret.nextRandomDecimal(Math.abs(salt.hashCode()/2 + salt.hashCode()%2));
        if (!pkgName.isEmpty()){
            ret = new ExtendedRandom(ret.nextLong() + pkgName.hashCode());
        }
        return ret;
    }
}
