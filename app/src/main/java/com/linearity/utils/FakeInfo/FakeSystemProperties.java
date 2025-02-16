package com.linearity.utils.FakeInfo;

import static com.linearity.utils.ExtendedRandom.SYSTEM_INSTANCE;
import static com.linearity.utils.ReturnReplacements.random;

import java.util.HashMap;

public class FakeSystemProperties {
    public static final  HashMap<String,String> propertiesMap = new HashMap<>();
    static {

        propertiesMap.put("line.separator","\n");
        propertiesMap.put("path.separator",":");
        propertiesMap.put("file.separator","/");
        propertiesMap.put("file.encoding","UTF-8");

        propertiesMap.put("java.boot.class.path",SYSTEM_INSTANCE.nextString(20));
        propertiesMap.put("java.class.version",random.nextInt(100)+"."+random.nextInt(10));
        propertiesMap.put("java.class.path",".");
        propertiesMap.put("java.compiler","");
        propertiesMap.put("java.ext.dirs","");
        propertiesMap.put("java.home","/system");
        propertiesMap.put("java.io.tmpdir","/tmp");
        propertiesMap.put("java.runtime.name","Android Runtime");
        propertiesMap.put("java.runtime.version",random.nextInt(10)+"."+random.nextInt(10));
        propertiesMap.put("java.vendor","The Android Project");
        propertiesMap.put("java.version",SYSTEM_INSTANCE.nextString(5));
        propertiesMap.put("java.vm.version",random.nextInt(10)+"."+random.nextInt(10)+"."+random.nextInt(10));
        propertiesMap.put("java.vm.name","Dalvik");
        propertiesMap.put("java.vm.vendor","The Android Project");
        propertiesMap.put("java.vm.vendor.url","http://www.android.com/");
        propertiesMap.put("java.vm.specification.name","Dalvik Virtual Machine Specification");
        propertiesMap.put("java.vm.specification.vendor","The Android Project");
        propertiesMap.put("java.vm.specification.version",random.nextInt(10)+"."+random.nextInt(10));
        propertiesMap.put("java.vendor.url","http://www.android.com/");
        propertiesMap.put("java.specification.name","Dalvik Core Library");
        propertiesMap.put("java.specification.vendor","The Android Project");
        propertiesMap.put("java.specification.version", random.nextInt(10)+"."+random.nextInt(10));
        propertiesMap.put("os.version",random.nextInt(10)+"."+random.nextInt(20)+"."+random.nextInt(300)+"-"+SYSTEM_INSTANCE.nextString(20));
        propertiesMap.put("os.arch","aarch64");
        propertiesMap.put("os.name","Linux");
        propertiesMap.put("user.dir","/");
        propertiesMap.put("user.variant","");
        propertiesMap.put("user.home","");
        propertiesMap.put("user.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"root"
        propertiesMap.put("user.language","zh");
        propertiesMap.put("user.region","CN");
        propertiesMap.put("user.locale","zh-CN");


        propertiesMap.put("android.zlib.version",random.nextInt(10)+"."+random.nextInt(10)+"."+random.nextInt(100));
        propertiesMap.put("android.icu.cldr.version",random.nextInt(100)+"."+random.nextInt(10));
        propertiesMap.put("android.icu.library.version",random.nextInt(100)+"."+random.nextInt(10));
        propertiesMap.put("android.icu.unicode.version",random.nextInt(20)+"."+random.nextInt(10));
        propertiesMap.put("android.icu.impl.ICUResourceBundle.skipRuntimeLocaleResourceScan",null);
        propertiesMap.put("com.android.org.bouncycastle.rsa.allow_unsafe_mod",null);
        propertiesMap.put("android.icu.impl.ICUBinary.dataPath","/data/misc/zoneinfo/current/icu/:/apex/com.android.tzdata/etc/icu/:/apex/com.android.i18n/etc/icu/");
        propertiesMap.put("android.icu.impl.ICUBinary.dataPat","/data/misc/zoneinfo/current/icu/:/apex/com.android.tzdata/etc/icu/:/apex/com.android.i18n/etc/icu/");
        propertiesMap.put("android.openssl.version","OpenSSL "+random.nextInt(10)+"."+random.nextInt(10)+"."+random.nextInt(10)+" (compatible; BoringSSL)");
        propertiesMap.put("com.android.org.conscrypt.useEngineSocketByDefault","true");

        propertiesMap.put("kotlinx.coroutines.debug",null);
        propertiesMap.put("kotlinx.coroutines.scheduler.default.name",null);
        propertiesMap.put("kotlinx.coroutines.scheduler.resolution.ns",null);
        propertiesMap.put("kotlinx.coroutines.scheduler.core.pool.size",null);
        propertiesMap.put("kotlinx.coroutines.scheduler.max.pool.size",null);
        propertiesMap.put("kotlinx.coroutines.scheduler.keep.alive.sec",null);
        propertiesMap.put("kotlinx.coroutines.io.parallelism",null);
        propertiesMap.put("kotlinx.coroutines.main.delay",null);
        propertiesMap.put("kotlinx.coroutines.DefaultExecutor.keepAlive",null);
        propertiesMap.put("kotlinx.coroutines.fast.service.loader",null);
        propertiesMap.put("kotlinx.coroutines.channels.defaultBuffer",null);

        propertiesMap.put("javax.net.ssl.trustStore",null);

        propertiesMap.put("javax.net.ssl.keyStore",null);
        propertiesMap.put("jsse.enableSNIExtension","true");
        propertiesMap.put("java.library.path","/system/lib64:/system_ext/lib64:/product/lib64");
        propertiesMap.put("java.net.preferIPv6Addresses","false");
        propertiesMap.put("rx.scheduler.max-computation-threads",null);
        propertiesMap.put("rx.scheduler.jdk6.purge-frequency-millis",null);
        propertiesMap.put("rx.scheduler.jdk6.purge-force",null);
        propertiesMap.put("rx.io-scheduler.keepalive",null);
        propertiesMap.put("rx.ring-buffer.size",null);
        propertiesMap.put("rx.unsafe-disable",null);
        propertiesMap.put("sparse.shift",null);
        propertiesMap.put("jctools.spsc.max.lookahead.step",null);

        propertiesMap.put("http.agent",SYSTEM_INSTANCE.nextString(20));
        propertiesMap.put("http.proxyHost","");
        propertiesMap.put("http.proxyPort","");
        propertiesMap.put("http.nonProxyHosts",null);
        propertiesMap.put("https.proxyHost","");
        propertiesMap.put("https.proxyPort","");
        propertiesMap.put("https.nonProxyHosts",null);
        propertiesMap.put("proxyHost",null);
        propertiesMap.put("socksProxyHost",null);
        propertiesMap.put("ftp.proxyHost",null);
        propertiesMap.put("ftp.nonProxyHosts",null);

        propertiesMap.put("io.grpc.Status.failOnEqualsForTest","false");
        propertiesMap.put("rx3.buffer-size",null);
        propertiesMap.put("rx3.scheduler.drift-tolerance",null);
        propertiesMap.put("rx3.scheduler.drift-tolerance-unit","minutes");
        propertiesMap.put("rx3.single-priority",null);
        propertiesMap.put("rx3.purge-enabled",null);
        propertiesMap.put("rx3.purge-period-seconds",null);
        propertiesMap.put("rx3.computation-threads",null);
        propertiesMap.put("rx3.computation-priority",null);
        propertiesMap.put("org.apache.commons.logging.diagnostics.dest",null);
        propertiesMap.put("org.apache.commons.logging.LogFactory.HashtableImpl",null);
        propertiesMap.put("io.github.qauxv.startup.StartupHook",null);
        propertiesMap.put("io.grpc.ManagedChannel.enableAllocationTracking","true");
        propertiesMap.put("guava.concurrent.generate_cancellation_cause","false");
        propertiesMap.put("rx.scheduler.drift-tolerance",null);
        propertiesMap.put("ro.yunos.version",null);

    }
}
