package com.linearity.utils.FakeInfo;

import static com.linearity.datservicereplacement.Phone.GsmCdmaPhoneConstructor.DEVICE_ID;
import static com.linearity.datservicereplacement.StartHook.publicSeed;
import static com.linearity.utils.ExtendedRandom.SYSTEM_INSTANCE;

import android.text.format.DateFormat;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.ListenerUtils.ListenerByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakeProcInfoGenerator {

    public static final FakeProcInfoGenerator INSTANCE = new FakeProcInfoGenerator();
    public static final ExtendedRandom random = new ExtendedRandom(publicSeed * FakeProcInfoGenerator.class.getName().hashCode());
    public static final  HashMap<String,String> infoMap = new HashMap<>();
    static {//not finished
        infoMap.put("aaudio.hw_burst_min_usec","2000");
        infoMap.put("aaudio.mmap_exclusive_policy","2");
        infoMap.put("aaudio.mmap_policy","2");
        infoMap.put("bootreceiver.enable","0");
        infoMap.put("build.version.extensions.r","1");
        infoMap.put("build.version.extensions.s","1");
//        infoMap.put("cache_key.display_info", String.valueOf(random.nextLong()));//"3710082813313631186"
//        infoMap.put("cache_key.get_packages_for_uid",String.valueOf(random.nextLong()));//"3710082813313631190"
//        infoMap.put("cache_key.has_system_feature",String.valueOf(random.nextLong()));//"3710082813313626931"
//        infoMap.put("cache_key.is_compat_change_enabled",String.valueOf(random.nextLong()));//"3710082813313631199"
//        infoMap.put("cache_key.is_interactive",String.valueOf(random.nextLong()));//"3710082813313631184"
//        infoMap.put("cache_key.is_power_save_mode",String.valueOf(random.nextLong()));//"3710082813313626943"
//        infoMap.put("cache_key.is_user_unlocked",String.valueOf(random.nextLong()));//"3710082813313629390"
//        infoMap.put("cache_key.location_enabled",String.valueOf(random.nextLong()));//"3710082813313626942"
//        infoMap.put("cache_key.package_info",String.valueOf(random.nextLong()));//"3710082813313631200"
//        infoMap.put("cache_key.system_server.account_user_data",String.valueOf(random.nextLong()));//"3710082813313631119"
//        infoMap.put("cache_key.system_server.accounts_data",String.valueOf(random.nextLong()));//"3710082813313629389"
//        infoMap.put("cache_key.telephony.get_active_data_sub_id",String.valueOf(random.nextLong()));//"7941374824241881760"
//        infoMap.put("cache_key.telephony.get_default_data_sub_id",String.valueOf(random.nextLong()));//"7941374824241825116"
//        infoMap.put("cache_key.telephony.get_default_sms_sub_id",String.valueOf(random.nextLong()));//"7941374824241825122"
//        infoMap.put("cache_key.telephony.get_default_sub_id",String.valueOf(random.nextLong()));//"7941374824241825120"
//        infoMap.put("cache_key.telephony.get_slot_index",String.valueOf(random.nextLong()));//"7941374824241825121"
        infoMap.put("camera.disable_zsl_mode","true");
        infoMap.put("dalvik.vm.appimageformat","lz4");
//        infoMap.put("dalvik.vm.dex2oat-Xms","1m");//"64m"
//        infoMap.put("dalvik.vm.dex2oat-Xmx","2m");//"512m"
        infoMap.put("dalvik.vm.dex2oat-max-image-block-size","2048");//524288
        infoMap.put("dalvik.vm.dex2oat-minidebuginfo","false");//true
        infoMap.put("dalvik.vm.dex2oat-resolve-startup-strings","false");//true
        infoMap.put("dalvik.vm.dex2oat-updatable-bcp-packages-file","/404notfound");// /system/etc/updatable-bcp-packages.txt
        infoMap.put("dalvik.vm.dexopt.secondary","false");//true
        infoMap.put("dalvik.vm.dexopt.thermal-cutoff","2");
//        infoMap.put("dalvik.vm.heapgrowthlimit","4m");//256
//        infoMap.put("dalvik.vm.heapmaxfree","2m");//32
//        infoMap.put("dalvik.vm.heapminfree","1m");//8
//        infoMap.put("dalvik.vm.heapsize","2m");//512
//        infoMap.put("dalvik.vm.heapstartsize","1m");//16
        infoMap.put("dalvik.vm.heaptargetutilization","0.5");
//        infoMap.put("dalvik.vm.image-dex2oat-Xms","6m");//64
//        infoMap.put("dalvik.vm.image-dex2oat-Xmx","6m");//64
        infoMap.put("dalvik.vm.isa.arm.features","default");
        infoMap.put("dalvik.vm.isa.arm.variant",SYSTEM_INSTANCE.nextString(20));//"kryo385"
        infoMap.put("dalvik.vm.isa.arm64.features","default");
        infoMap.put("dalvik.vm.isa.arm64.variant",SYSTEM_INSTANCE.nextString(20));//"kryo385"
        infoMap.put("dalvik.vm.lockprof.threshold","500");
        infoMap.put("dalvik.vm.madvise.artfile.size", String.valueOf(random.nextLong()));//"4294967295"
        infoMap.put("dalvik.vm.madvise.odexfile.size","1024");//104857600
        infoMap.put("dalvik.vm.madvise.vdexfile.size","1024");//104857600
        infoMap.put("dalvik.vm.minidebuginfo","false");//true
        infoMap.put("dalvik.vm.usejit","false");//true
        infoMap.put("dalvik.vm.usejitprofiles","false");//true
        infoMap.put("debug.atrace.tags.enableflags","0");
        infoMap.put("debug.egl.hw","0");
        infoMap.put("debug.force_rtl","false");
        infoMap.put("debug.gralloc.gfx_ubwc_disable","0");
        infoMap.put("debug.mdpcomp.logs","0");
        infoMap.put("debug.sf.enable_advanced_sf_phase_offset","1");
        infoMap.put("debug.sf.enable_gl_backpressure","1");
        infoMap.put("debug.sf.enable_hwc_vds","1");
        infoMap.put("debug.sf.high_fps_early_gl_phase_offset_ns","-4000000");
        infoMap.put("debug.sf.high_fps_early_phase_offset_ns","-4000000");
        infoMap.put("debug.sf.high_fps_late_app_phase_offset_ns","1000000");
        infoMap.put("debug.sf.high_fps_late_sf_phase_offset_ns","-4000000");
        infoMap.put("debug.sf.hw","0");
        infoMap.put("debug.sf.latch_unsignaled","1");
        infoMap.put("debug.stagefright.ccodec","4");
        infoMap.put("debug.stagefright.omx_default_rank","0");
        infoMap.put("debug.tracing.screen_brightness", String.valueOf(random.nextFloat()));//"0.06357726"
        infoMap.put("debug.tracing.screen_state","2");
        infoMap.put("dev.bootcomplete","1");
        infoMap.put("dev.mnt.blk.cache","sda");
        infoMap.put("dev.mnt.blk.data","dm-5");
        infoMap.put("dev.mnt.blk.data.user.0","dm-5");
        infoMap.put("dev.mnt.blk.data_mirror.cur_profiles","dm-5");
        infoMap.put("dev.mnt.blk.data_mirror.data_ce.null","dm-5");
        infoMap.put("dev.mnt.blk.data_mirror.data_ce.null.0","dm-5");
        infoMap.put("dev.mnt.blk.data_mirror.data_de.null","dm-5");
        infoMap.put("dev.mnt.blk.data_mirror.ref_profiles","dm-5");
        infoMap.put("dev.mnt.blk.dev.logfs","sda");
        infoMap.put("dev.mnt.blk.metadata","sda");
        infoMap.put("dev.mnt.blk.mnt.vendor.persist","sda");
        infoMap.put("dev.mnt.blk.mnt.vendor.spunvm","sde");
        infoMap.put("dev.mnt.blk.odm","dm-3");
        infoMap.put("dev.mnt.blk.product","dm-2");
        infoMap.put("dev.mnt.blk.root","dm-0");
        infoMap.put("dev.mnt.blk.system_ext","dm-4");
        infoMap.put("dev.mnt.blk.vendor","dm-1");
        infoMap.put("dev.mnt.blk.vendor.bt_firmware","sde");
        infoMap.put("dev.mnt.blk.vendor.dsp","sde");
        infoMap.put("dev.mnt.blk.vendor.firmware_mnt","sde");
        infoMap.put("gsm.current.phone-type", random.nextInt(20) + "," + random.nextInt(20));//"1,1"
        infoMap.put("gsm.network.type",SYSTEM_INSTANCE.nextString(20));//"LTE,Unknown"
        infoMap.put("gsm.operator.alpha",SYSTEM_INSTANCE.nextString(20)+",");//"CHN-UNICOM,"
        infoMap.put("gsm.operator.iso-country","cn,");
        infoMap.put("gsm.operator.isroaming","false,false");
        infoMap.put("gsm.operator.numeric","46001,");
        infoMap.put("gsm.sim.operator.alpha",SYSTEM_INSTANCE.nextString(20));//"China Unicom"
        infoMap.put("gsm.sim.operator.iso-country","cn");
        infoMap.put("gsm.sim.operator.numeric","46001");
        infoMap.put("gsm.sim.state","LOADED,ABSENT");
        infoMap.put("gsm.version.baseband",SYSTEM_INSTANCE.nextString(20)+","+SYSTEM_INSTANCE.nextString(20));//"2.5.c1-7.1-4393.37-1031_2028_9969247ed8,2.5.c1-7.1-4393.37-1031_2028_9969247ed8"
        infoMap.put("gsm.version.ril-impl","Qualcomm RIL 1.0");
        infoMap.put("hwservicemanager.ready","true");
        infoMap.put("init.svc.adb_root","stopped");
        infoMap.put("init.svc.adbd","stopped");
        infoMap.put("init.svc.alarm-hal-1-0","stopped");
        infoMap.put("init.svc.android.thermal-hal","running");
        infoMap.put("init.svc.apexd","stopped");
        infoMap.put("init.svc.apexd-bootstrap","stopped");
        infoMap.put("init.svc.apexd-snapshotde","stopped");
        infoMap.put("init.svc.audioserver","running");
        infoMap.put("init.svc.batterysecret","stopped");
        infoMap.put("init.svc.bootanim","stopped");
        infoMap.put("init.svc.boringssl_self_test32","stopped");
        infoMap.put("init.svc.boringssl_self_test32_vendor","stopped");
        infoMap.put("init.svc.boringssl_self_test64","stopped");
        infoMap.put("init.svc.boringssl_self_test64_vendor","stopped");
        infoMap.put("init.svc.boringssl_self_test_apex32","stopped");
        infoMap.put("init.svc.boringssl_self_test_apex64","stopped");
        infoMap.put("init.svc.bpfloader","stopped");
        infoMap.put("init.svc.cameraserver","running");
        infoMap.put("init.svc.cnss-daemon","running");
        infoMap.put("init.svc.credstore","running");
        infoMap.put("init.svc.dcvs-sh","stopped");
        infoMap.put("init.svc.derive_classpath","stopped");
        infoMap.put("init.svc.derive_sdk","stopped");
        infoMap.put("init.svc.display-color-hal-1-0","running");
        infoMap.put("init.svc.dpmQmiMgr","running");
        infoMap.put("init.svc.dpmd","running");
        infoMap.put("init.svc.drm","running");
        infoMap.put("init.svc.feature_enabler_client","running");
        infoMap.put("init.svc.gatekeeper-1-0","running");
        infoMap.put("init.svc.gatekeeperd","running");
        infoMap.put("init.svc.gnss_service","running");
        infoMap.put("init.svc.gpu","running");
        infoMap.put("init.svc.health-hal-2-1","running");
        infoMap.put("init.svc.heapprofd","stopped");
        infoMap.put("init.svc.hidl_memory","running");
        infoMap.put("init.svc.hwservicemanager","running");
        infoMap.put("init.svc.idmap2d","stopped");
        infoMap.put("init.svc.incidentd","running");
        infoMap.put("init.svc.installd","running");
        infoMap.put("init.svc.irsc_util","stopped");
        infoMap.put("init.svc.keymaster-4-0","running");
        infoMap.put("init.svc.keystore2","running");
        infoMap.put("init.svc.lmkd","running");
        infoMap.put("init.svc.loc_launcher","running");
        infoMap.put("init.svc.logcatd","running");
        infoMap.put("init.svc.logd","running");
        infoMap.put("init.svc.logd-auditctl","stopped");
        infoMap.put("init.svc.logd-reinit","stopped");
        infoMap.put("init.svc.media","running");
        infoMap.put("init.svc.media.swcodec","running");
        infoMap.put("init.svc.mediadrm","running");
        infoMap.put("init.svc.mediaextractor","running");
        infoMap.put("init.svc.mediametrics","running");
        infoMap.put("init.svc.mi_thermald","running");
        infoMap.put("init.svc.mlid","running");
        infoMap.put("init.svc.netd","running");
        infoMap.put("init.svc.neuralnetworks_hal_service","running");
        infoMap.put("init.svc.odsign","stopped");
        infoMap.put("init.svc.qccvndhalservice","running");
        infoMap.put("init.svc.qcom-c_main-sh","stopped");
        infoMap.put("init.svc.qcom-post-boot","stopped");
        infoMap.put("init.svc.qcom-sh","stopped");
        infoMap.put("init.svc.qseecom-service","running");
        infoMap.put("init.svc.qteeconnector-hal-1-0","running");
        infoMap.put("init.svc.qti_esepowermanager_service_1_1","running");
        infoMap.put("init.svc.secureelement-hal_1_2","running");
        infoMap.put("init.svc.sensorscal-hal-1-0","running");
        infoMap.put("init.svc.servicemanager","running");
        infoMap.put("init.svc.soter-1-0","running");
        infoMap.put("init.svc.statsd","running");
        infoMap.put("init.svc.storaged","running");
        infoMap.put("init.svc.surfaceflinger","running");
        infoMap.put("init.svc.system_suspend","running");
        infoMap.put("init.svc.time_daemon","running");
        infoMap.put("init.svc.tombstoned","running");
        infoMap.put("init.svc.traced","running");
        infoMap.put("init.svc.traced_perf","stopped");
        infoMap.put("init.svc.traced_probes","running");
        infoMap.put("init.svc.tui_comm-1-0","running");
        infoMap.put("init.svc.ueventd","running");
        infoMap.put("init.svc.usbd","stopped");
        infoMap.put("init.svc.vendor-sensor-sh","stopped");
        infoMap.put("init.svc.vendor_flash_recovery","stopped");
        infoMap.put("init.svc.vndservicemanager","running");
        infoMap.put("init.svc.vold","running");
        infoMap.put("init.svc.wait_for_keymaster","stopped");
        infoMap.put("init.svc.wificond","running");
        infoMap.put("init.svc.wifidisplayhalservice","running");
        infoMap.put("init.svc.zygote","running");
        infoMap.put("init.svc.zygote_secondary","running");
        infoMap.put("log.tag.APM_AudioPolicyManager","D");
        infoMap.put("log.tag.stats_log","I");
        infoMap.put("logd.logpersistd","logcatd");
        infoMap.put("logd.logpersistd.buffer","all");
        infoMap.put("logd.logpersistd.enable","false");//true
        infoMap.put("media.recorder.show_manufacturer_and_model","true");
        infoMap.put("media.settings.xml",SYSTEM_INSTANCE.nextString(40));//"/vendor/etc/media_profiles_vendor.xml"
        infoMap.put("net.bt.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"Android"
        infoMap.put("net.qtaguid_enabled","1");
        infoMap.put("net.tcp.2g_init_rwnd","10");
        infoMap.put("net.tethering.noprovisioning","true");
        infoMap.put("nfc.initialized","true");
        infoMap.put("persist.dbg.volte_avail_ovr","1");
        infoMap.put("persist.dbg.vt_avail_ovr","1");
        infoMap.put("persist.dbg.wfc_avail_ovr","1");
        infoMap.put("persist.debug.dalvik.vm.core_platform_api_policy","just-warn");
        infoMap.put("persist.device_config.runtime_native.usap_pool_enabled","true");
        infoMap.put("persist.log.tag","");
        infoMap.put("persist.logd.logpersistd","logcatd");
        infoMap.put("persist.logd.logpersistd.buffer","all");
        infoMap.put("persist.logd.size","-1");
        infoMap.put("persist.logd.size.crash","-1");
        infoMap.put("persist.logd.size.main","-1");
        infoMap.put("persist.radio.active.slots","2");
        infoMap.put("persist.radio.airplane_mode_on","0");
        infoMap.put("persist.radio.autoselect_by_uim","7");
        infoMap.put("persist.radio.display_mipi_cur","0");
        infoMap.put("persist.radio.display_mipi_init_num","656");
        infoMap.put("persist.radio.display_mipi_set_num","0");
        infoMap.put("persist.radio.goldencopy_flag","true");
        infoMap.put("persist.radio.is_vonr_enabled_0","false");
        infoMap.put("persist.radio.is_vonr_enabled_1","false");
        infoMap.put("persist.radio.modem_mbn_check","1");
        infoMap.put("persist.radio.modem_sw_mbn_count","98");
        infoMap.put("persist.radio.modem_sw_mbn_flag","1");
        infoMap.put("persist.radio.mtbf_flag","false");
        infoMap.put("persist.radio.multisim.config","dsds");
        infoMap.put("persist.radio.operating_mode","0");
        infoMap.put("persist.radio.skhwc_matchres","MATCH");
        infoMap.put("persist.radio.speech_codec","");
        infoMap.put("persist.rcs.supported","0");
        infoMap.put("persist.sys.boot.reason","");
        infoMap.put("persist.sys.boot.reason.history","reboot,,0");
        infoMap.put("persist.sys.dalvik.vm.lib.2","libart.so");
        infoMap.put("persist.sys.displayinset.top","0");
        infoMap.put("persist.sys.fflag.override.settings_bluetooth_hearing_aid","true");
        infoMap.put("persist.sys.fuse","true");
        infoMap.put("persist.sys.gps.lpp","2");
        infoMap.put("persist.sys.lmk.reportkills","true");
        infoMap.put("persist.sys.locale","zh-CN");//language
        infoMap.put("persist.sys.max.profiles","2");
        infoMap.put("persist.sys.oem.otg_support","false");
        infoMap.put("persist.sys.sf.color_mode",String.valueOf(random.nextInt(14)));
        infoMap.put("persist.sys.sf.color_saturation","1.0");
        infoMap.put("persist.sys.sf.native_mode","2");
        infoMap.put("persist.sys.strictmode.disable","true");
        infoMap.put("persist.sys.strictmode.visual","");
        infoMap.put("persist.sys.timezone",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"Asia/Shanghai"
        infoMap.put("persist.sys.usb.config","none");
        infoMap.put("persist.sys.vold_app_data_isolation_enabled","1");
        infoMap.put("persist.traced.enable","1");
        infoMap.put("persist.vendor.camera.frontMain.info",SYSTEM_INSTANCE.nextString(20));//"/vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_s5k3t2_front.bin"
        infoMap.put("persist.vendor.camera.frontMain.vendorID",String.valueOf(random.nextInt()));
        infoMap.put("persist.vendor.camera.mi.dualcal.detail","0");
        infoMap.put("persist.vendor.camera.mi.dualcal.exist","0");
        infoMap.put("persist.vendor.camera.mi.dualcal.state","0");
        infoMap.put("persist.vendor.camera.mi.module.info","back_main=imx686;back_ultra=ov13b10;back_depth=gc02m1;");
        infoMap.put("persist.vendor.camera.mi.module.infoext","front_main=s5k3t2;back_macro=s5k5e9;");
        infoMap.put("persist.vendor.camera.rearDepth.info",SYSTEM_INSTANCE.nextString(20));//"/vendor/lib64/camera/com.qti.sensormodule.lmi_ofilm_gc02m1_depth.bin"
        infoMap.put("persist.vendor.camera.rearDepth.vendorID",String.valueOf(random.nextInt()));
        infoMap.put("persist.vendor.camera.rearMacro.info",SYSTEM_INSTANCE.nextString(20));//"/vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_s5k5e9yx04_macro.bin"
        infoMap.put("persist.vendor.camera.rearMacro.vendorID",String.valueOf(random.nextInt()));
        infoMap.put("persist.vendor.camera.rearMain.info",SYSTEM_INSTANCE.nextString(20));//"/vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_imx686_wide_mp.bin"
        infoMap.put("persist.vendor.camera.rearMain.vendorID",String.valueOf(random.nextInt()));
        infoMap.put("persist.vendor.camera.rearUltra.info",SYSTEM_INSTANCE.nextString(20));//"/vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_ov13b10_ultra.bin"
        infoMap.put("persist.vendor.camera.rearUltra.vendorID",String.valueOf(random.nextInt()));
        infoMap.put("persist.vendor.dpm.feature","1");
        infoMap.put("persist.vendor.sys.fp.expolevel","0x"+SYSTEM_INSTANCE.nextRandomHexLower(2));
        infoMap.put("persist.vendor.sys.fp.fod.large_field","0");
        infoMap.put("persist.vendor.sys.fp.info","0xff" + SYSTEM_INSTANCE.nextRandomHexLower(12));//0000450f000000
        infoMap.put("persist.vendor.sys.fp.module","Ofilm");
        infoMap.put("persist.vendor.sys.fp.mulexposupport","0");
        infoMap.put("persist.vendor.sys.fp.uid", UUID.randomUUID().toString());//"51313342-38342E01-BF7E747A-85AC0000"
        infoMap.put("persist.vendor.sys.fp.vendor","goodix_fod");
        infoMap.put("persist.vendor.sys.pay.ifaa","1");
        infoMap.put("pm.dexopt.ab-ota","speed-profile");
        infoMap.put("pm.dexopt.bg-dexopt","speed-profile");
        infoMap.put("pm.dexopt.boot-after-ota","verify");
        infoMap.put("pm.dexopt.cmdline","verify");
        infoMap.put("pm.dexopt.first-boot","verify");
        infoMap.put("pm.dexopt.inactive","verify");
        infoMap.put("pm.dexopt.install","speed-profile");
        infoMap.put("pm.dexopt.install-bulk","speed-profile");
        infoMap.put("pm.dexopt.install-bulk-downgraded","verify");
        infoMap.put("pm.dexopt.install-bulk-secondary","verify");
        infoMap.put("pm.dexopt.install-bulk-secondary-downgraded","extract");
        infoMap.put("pm.dexopt.install-fast","skip");
        infoMap.put("pm.dexopt.post-boot","extract");
        infoMap.put("pm.dexopt.shared","speed");
        infoMap.put("ril.ecclist",
                random.nextInt(256) +","+ random.nextInt(256)
                        +","+ random.nextInt(256) +","+ random.nextInt(256)
                        +","+ random.nextInt(256) +","+ random.nextInt(256)
                        +","+ random.nextInt(256) +","+ random.nextInt(256)
                        +","+ random.nextInt(256) +","+ random.nextInt(256));//"112,000,08,110,120,119,118,122,911,999"
        infoMap.put("ril.ecclist1",random.nextInt(256) +","+ random.nextInt(256)
                +","+ random.nextInt(256) +","+ random.nextInt(256)
                +","+ random.nextInt(256) +","+ random.nextInt(256)
                +","+ random.nextInt(256) +","+ random.nextInt(256)
                +","+ random.nextInt(256) +","+ random.nextInt(256));//"112,000,08,110,120,119,118,122,911,999"
        infoMap.put("ril.fake_bs_flag0","FALSE:0");
        infoMap.put("ril.mcc.mnc0",String.valueOf(random.nextInt(50000)));//"46001"
        infoMap.put("ril.mcc.mnc1","");
        infoMap.put("ro.actionable_compatible_property.enabled","true");
        infoMap.put("ro.adb.secure","1");
        infoMap.put("ro.allow.mock.location","0");
        infoMap.put("ro.baseband","mdm");
        infoMap.put("ro.board.platform","kona");
        infoMap.put("ro.boot.avb_version","1.2");
        infoMap.put("ro.boot.baseband","mdm");
        infoMap.put("ro.boot.boot_devices",SYSTEM_INSTANCE.nextString(20));//"soc/1d84000.ufshc"
        infoMap.put("ro.boot.bootdevice",SYSTEM_INSTANCE.nextString(20));//"1d84000.ufshc"
        infoMap.put("ro.boot.bootreason",SYSTEM_INSTANCE.nextString(20)+",");
        infoMap.put("ro.boot.camera.config","1");
        infoMap.put("ro.boot.cert","M2001J11C");
        infoMap.put("ro.boot.console","ttyMSM0");
        infoMap.put("ro.boot.cpuid","0x" + SYSTEM_INSTANCE.nextRandomHexLower(8));//2b1139f2
        infoMap.put("ro.boot.dp","0x0");
        infoMap.put("ro.boot.dtb_idx","0");
        infoMap.put("ro.boot.dtbo_idx","4");
        infoMap.put("ro.boot.dynamic_partitions","true");
        infoMap.put("ro.boot.fstab_suffix","qcom");
        infoMap.put("ro.boot.hardware","qcom");
        infoMap.put("ro.boot.hwc","CN");
        infoMap.put("ro.boot.hwlevel",SYSTEM_INSTANCE.nextString(9));
        infoMap.put("ro.boot.hwversion",SYSTEM_INSTANCE.nextString(9));
        infoMap.put("ro.boot.init_fatal_reboot_target","recovery");
        infoMap.put("ro.boot.keymaster","1");
        infoMap.put("ro.boot.memcg","1");
        infoMap.put("ro.boot.network.mode","2");
        infoMap.put("ro.boot.product.hardware.sku","nfc");
        infoMap.put("ro.boot.ramdump","disable");
        infoMap.put("ro.boot.secureboot","1");
        infoMap.put("ro.boot.serialno",SYSTEM_INSTANCE.nextRandomHexLower(9));
        infoMap.put("ro.boot.usbcontroller",SYSTEM_INSTANCE.nextRandomHexLower(14));
        infoMap.put("ro.boot.verifiedbootstate","green");
        infoMap.put("ro.bootimage.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.bootimage.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.bootimage.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.bootimage.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.bootimage.build.tags","release-keys");
        infoMap.put("ro.bootimage.build.type",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.bootimage.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));
        infoMap.put("ro.bootimage.build.version.release",String.valueOf(random.nextInt(14)));
        infoMap.put("ro.bootimage.build.version.release_or_codename",String.valueOf(random.nextInt(14)));
        infoMap.put("ro.bootimage.build.version.sdk",String.valueOf(random.nextInt(34)));
        infoMap.put("ro.bootloader",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"unknown"
        infoMap.put("ro.bootmode",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"unknown"
        infoMap.put("ro.build.characteristics","default");
        infoMap.put("ro.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.build.description",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.build.display.id",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.build.flavor","lineage_lmi-userdebug");
        infoMap.put("ro.build.host",SYSTEM_INSTANCE.nextRandomHexLower(12));
        infoMap.put("ro.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.build.product",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.build.tags","release-keys");
        infoMap.put("ro.build.type","user");
        infoMap.put("ro.build.user",SYSTEM_INSTANCE.nextString(5));//"root"
        infoMap.put("ro.build.version.all_codenames",SYSTEM_INSTANCE.nextString(3));//"REL"
        infoMap.put("ro.build.version.base_os","");
        infoMap.put("ro.build.version.codename",SYSTEM_INSTANCE.nextString(3));//"REL"
        infoMap.put("ro.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));//"57e323c73e"
        infoMap.put("ro.build.version.min_supported_target_sdk","1");
        infoMap.put("ro.build.version.preview_sdk",String.valueOf(random.nextInt(14)));
        infoMap.put("ro.build.version.preview_sdk_fingerprint",SYSTEM_INSTANCE.nextString(3));//"REL"
        infoMap.put("ro.build.version.release",String.valueOf(random.nextInt(14)));//
        infoMap.put("ro.build.version.release_or_codename",String.valueOf(random.nextInt(14)));//
        infoMap.put("ro.build.version.sdk",String.valueOf(random.nextInt(34)));//
        infoMap.put("ro.build.version.security_patch",DateFormat.format("yyyy-MM-dd",random.nextLong()).toString());//"2023-02-05"
        infoMap.put("ro.camera.notify_nfc","1");
        infoMap.put("ro.carrier","unknown");
        infoMap.put("ro.com.android.dataroaming","true");
        infoMap.put("ro.com.google.clientidbase",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"android-xiaomi"
        infoMap.put("ro.config.alarm_alert",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"Hassium.ogg"
        infoMap.put("ro.config.notification_sound",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"Argon.ogg"
        infoMap.put("ro.config.ringtone",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"Orion.ogg"
        infoMap.put("ro.config.vc_call_vol_steps", String.valueOf(random.nextInt(20) + 1));
        infoMap.put("ro.control_privapp_permissions","enforce");
        infoMap.put("ro.crypto.allow_encrypt_override","true");
        infoMap.put("ro.crypto.metadata.enabled","true");
        infoMap.put("ro.crypto.state","encrypted");
        infoMap.put("ro.crypto.type","file");
        infoMap.put("ro.crypto.uses_fs_ioc_add_encryption_key","true");
        infoMap.put("ro.crypto.volume.filenames_mode","aes-256-cts");
        infoMap.put("ro.dalvik.vm.native.bridge","0");
        infoMap.put("ro.debuggable","0");
        infoMap.put("ro.frp.pst",SYSTEM_INSTANCE.nextString(20));//"/dev/block/bootdevice/by-name/frp"
        infoMap.put("ro.hardware","qcom");
        infoMap.put("ro.hardware.egl","adreno");
        infoMap.put("ro.hardware.fp.udfps","true");
        infoMap.put("ro.hardware.keystore_desede","true");
        infoMap.put("ro.hardware.vulkan","adreno");
        infoMap.put("ro.hwui.use_vulkan","");
        infoMap.put("ro.iorapd.enable","true");
        infoMap.put("ro.kernel.version", String.valueOf(random.nextFloat() + 1));//"4.19"
//        infoMap.put("ro.lineage.build.version","19.1");
//        infoMap.put("ro.lineage.build.version.plat.rev","0");
//        infoMap.put("ro.lineage.build.version.plat.sdk",String.valueOf(random.nextInt(14)));
//        infoMap.put("ro.lineage.device","lmi");
//        infoMap.put("ro.lineage.display.version","19-20230304-NIGHTLY-lmi");
//        infoMap.put("ro.lineage.releasetype","NIGHTLY");
//        infoMap.put("ro.lineage.version","19.1-20230304-NIGHTLY-lmi");
//        infoMap.put("ro.lineagelegal.url","https://lineageos.org/legal");
        infoMap.put("ro.lmk.kill_heaviest_task","true");
        infoMap.put("ro.lmk.kill_timeout_ms",String.valueOf(random.nextInt(7)+9));
        infoMap.put("ro.lmk.use_minfree_levels","true");
        infoMap.put("ro.logd.kernel","true");
        infoMap.put("ro.logd.size.stats","64K");
        infoMap.put("ro.media.xml_variant.codecs",SYSTEM_INSTANCE.nextString(4));//"_kona"
        infoMap.put("ro.media.xml_variant.codecs_performance",SYSTEM_INSTANCE.nextString(4));//"_kona"
        infoMap.put("ro.modversion",SYSTEM_INSTANCE.nextString(20));//"19.1-20230304-NIGHTLY-lmi"
        infoMap.put("ro.netflix.bsp_rev",SYSTEM_INSTANCE.nextString(20));//"Q8250-19134-1"
        infoMap.put("ro.odm.build.date", DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.odm.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.odm.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.odm.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));
        infoMap.put("ro.odm_dlkm.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.opengles.version", String.valueOf(random.nextLong()));//196610
        infoMap.put("ro.organization_owned","false");
        infoMap.put("ro.postinstall.fstab.prefix","/system");
        infoMap.put("ro.product.board",SYSTEM_INSTANCE.nextString(20));//"kona"
        infoMap.put("ro.product.bootimage.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.bootimage.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.bootimage.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.bootimage.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.bootimage.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.product.build.date.utc",String.valueOf(random.nextLong()));//forgot"2007-08-31");//forgot
        infoMap.put("ro.product.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.product.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.product.build.tags","release-keys");
        infoMap.put("ro.product.build.type",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.product.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));//"57e323c73e"
        infoMap.put("ro.product.build.version.release",String.valueOf(random.nextInt(14)));//
        infoMap.put("ro.product.build.version.release_or_codename",String.valueOf(random.nextInt(14)));//
        infoMap.put("ro.product.build.version.sdk",String.valueOf(random.nextInt(34)));//
        infoMap.put("ro.product.cpu.abi","arm64-v8a");
        infoMap.put("ro.product.cpu.abilist","arm64-v8a,armeabi-v7a,armeabi");
        infoMap.put("ro.product.cpu.abilist32","armeabi-v7a,armeabi");
        infoMap.put("ro.product.cpu.abilist64","arm64-v8a");
        infoMap.put("ro.product.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.first_api_level",String.valueOf(random.nextInt()));//29
        infoMap.put("ro.product.locale",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"en-US"
        infoMap.put("ro.product.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.marketname","");
        infoMap.put("ro.product.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.odm.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.odm.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.odm.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.odm.marketname","");
        infoMap.put("ro.product.odm.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.odm.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.odm_dlkm.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.odm_dlkm.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.odm_dlkm.marketname","");
        infoMap.put("ro.product.odm_dlkm.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.product.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.product.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.product.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.product.marketname","");
        infoMap.put("ro.product.product.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.product.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.system.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.system.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.system.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.system.marketname","");
        infoMap.put("ro.product.system.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.system.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.system_ext.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.system_ext.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.system_ext.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.system_ext.marketname","");
        infoMap.put("ro.product.system_ext.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.system_ext.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.vendor.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.vendor.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.vendor.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.vendor.marketname","");
        infoMap.put("ro.product.vendor.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.vendor.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.product.vendor_dlkm.brand",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"POCO"
        infoMap.put("ro.product.vendor_dlkm.device",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lmi"
        infoMap.put("ro.product.vendor_dlkm.manufacturer",DEVICE_ID);//"Xiaomi"
        infoMap.put("ro.product.vendor_dlkm.marketname","");
        infoMap.put("ro.product.vendor_dlkm.model",DEVICE_ID);//"POCO F2 Pro"
        infoMap.put("ro.product.vendor_dlkm.name",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"lineage_lmi"
        infoMap.put("ro.property_service.version",String.valueOf(random.nextInt(4)));//2
        infoMap.put("ro.revision",String.valueOf(random.nextInt(14)));
        infoMap.put("ro.ril.factory_id", String.valueOf(random.nextInt(114514) + 1));
        infoMap.put("ro.ril.nal","");
        infoMap.put("ro.ril.operator","");
        infoMap.put("ro.ril.region","");
        infoMap.put("ro.secure","1");
        infoMap.put("ro.serialno",SYSTEM_INSTANCE.nextRandomHexLower(9));//"6266a966"
        infoMap.put("ro.sf.lcd_density",String.valueOf(440 + random.nextInt(10)*(random.nextBoolean()?-1:1)));
        infoMap.put("ro.soc.manufacturer","Qualcomm");
        infoMap.put("ro.soc.model",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"SM8250"
        infoMap.put("ro.storage_manager.enabled","true");
        infoMap.put("ro.support_one_handed_mode","true");
        infoMap.put("ro.surface_flinger.force_hwc_copy_for_virtual_displays","true");
        infoMap.put("ro.surface_flinger.has_HDR_display","true");
        infoMap.put("ro.surface_flinger.has_wide_color_display","true");
        infoMap.put("ro.surface_flinger.max_frame_buffer_acquired_buffers","3");
        infoMap.put("ro.surface_flinger.max_virtual_display_dimension",String.valueOf(2048+random.nextInt(2049)));
        infoMap.put("ro.surface_flinger.protected_contents","true");
        infoMap.put("ro.surface_flinger.use_color_management","true");
        infoMap.put("ro.surface_flinger.wcg_composition_dataspace", String.valueOf(random.nextLong()));//"143261696"
        infoMap.put("ro.system.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.system.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.system.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.system.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.system.build.tags","release-keys");
        infoMap.put("ro.system.build.type",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.system.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));//"57e323c73e"
        infoMap.put("ro.system.build.version.release",String.valueOf(random.nextInt(14)));//
        infoMap.put("ro.system.build.version.release_or_codename",String.valueOf(random.nextInt(14)));//
        infoMap.put("ro.system.build.version.sdk",String.valueOf(random.nextInt(34)));//
        infoMap.put("ro.system.product.cpu.abilist","arm64-v8a,armeabi-v7a,armeabi");
        infoMap.put("ro.system.product.cpu.abilist32","armeabi-v7a,armeabi");
        infoMap.put("ro.system.product.cpu.abilist64","arm64-v8a");
        infoMap.put("ro.system_ext.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.system_ext.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.system_ext.build.fingerprint",SYSTEM_INSTANCE.nextString(40));
        infoMap.put("ro.system_ext.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.system_ext.build.tags","release-keys");
        infoMap.put("ro.system_ext.build.type",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));
        infoMap.put("ro.system_ext.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));//"57e323c73e"
        infoMap.put("ro.system_ext.build.version.release",String.valueOf(random.nextInt(14)));//"12"
        infoMap.put("ro.system_ext.build.version.release_or_codename",String.valueOf(random.nextInt(14)));//"12"
        infoMap.put("ro.system_ext.build.version.sdk",String.valueOf(random.nextInt(34)));//"32"
        infoMap.put("ro.telephony.call_ring.multiple","false");
        infoMap.put("ro.telephony.default_network",String.valueOf(random.nextInt()));
        infoMap.put("ro.treble.enabled","true");
        infoMap.put("ro.vendor.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.vendor.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.vendor.build.fingerprint",SYSTEM_INSTANCE.nextString(40));//"POCO/lmi_global/lmi:12/RKQ1.211001.001/V13.0.3.0.SJKMIXM:user/release-keys"
        infoMap.put("ro.vendor.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"SQ3A.220705.004"
        infoMap.put("ro.vendor.build.security_patch",DateFormat.format("yyyy-MM-dd",random.nextLong()).toString());//forgot
        infoMap.put("ro.vendor.build.tags","release-keys");
        infoMap.put("ro.vendor.build.type",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"userdebug"
        infoMap.put("ro.vendor.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));//"57e323c73e"
        infoMap.put("ro.vendor.build.version.release",String.valueOf(random.nextInt(14)));//"12"
        infoMap.put("ro.vendor.build.version.release_or_codename",String.valueOf(random.nextInt(14)));//12
        infoMap.put("ro.vendor.build.version.sdk",String.valueOf(random.nextInt(34)));//32
        infoMap.put("ro.vendor.product.cpu.abilist","arm64-v8a,armeabi-v7a,armeabi");
        infoMap.put("ro.vendor.product.cpu.abilist32","armeabi-v7a,armeabi");
        infoMap.put("ro.vendor.product.cpu.abilist64","arm64-v8a");
        infoMap.put("ro.vendor.qti.va_aosp.support","1");
        infoMap.put("ro.vendor.qti.va_odm.support","1");
        infoMap.put("ro.vendor_dlkm.build.date",DateFormat.format("EEE MMM  d HH:mm:ss 'UTC' yyyy",random.nextLong()).toString());//forgot
        infoMap.put("ro.vendor_dlkm.build.date.utc",String.valueOf(random.nextLong()));//forgot
        infoMap.put("ro.vendor_dlkm.build.fingerprint",SYSTEM_INSTANCE.nextString(40));//"POCO/lmi_global/lmi:12/RKQ1.211001.001/V13.0.3.0.SJKMIXM:user/release-keys"
        infoMap.put("ro.vendor_dlkm.build.id",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"SQ3A.220705.004"
        infoMap.put("ro.vendor_dlkm.build.tags","release-keys");
        infoMap.put("ro.vendor_dlkm.build.type",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"userdebug"
        infoMap.put("ro.vendor_dlkm.build.version.incremental",SYSTEM_INSTANCE.nextRandomHexLower(9));
        infoMap.put("ro.vendor_dlkm.build.version.release",String.valueOf(random.nextInt(14)));//"12"
        infoMap.put("ro.vendor_dlkm.build.version.release_or_codename",String.valueOf(random.nextInt(14)));//"12"
        infoMap.put("ro.vendor_dlkm.build.version.sdk",String.valueOf(random.nextInt(34)));
        infoMap.put("ro.vndk.version",String.valueOf(random.nextInt(34)));
        infoMap.put("ro.wifi.channels","");
        infoMap.put("ro.zygote","zygote64_32");
        infoMap.put("security.perf_harden","1");
        infoMap.put("selinux.restorecon_recursive",SYSTEM_INSTANCE.nextString(random.nextInt(10)+5));//"/data/misc_ce/10"
        infoMap.put("service.sf.present_timestamp","1");
        infoMap.put("sys.boot.reason","reboot,");
        infoMap.put("sys.boot.reason.last","reboot,");
        infoMap.put("sys.boot_completed","1");
        infoMap.put("sys.bootstat.first_boot_completed","1");
        infoMap.put("sys.fflag.override.settings_bluetooth_hearing_aid","true");
        infoMap.put("sys.fflag.override.settings_seamless_transfer","true");
        infoMap.put("sys.fuse.transcode_enabled","true");
        infoMap.put("sys.init.perf_lsm_hooks","1");
        infoMap.put("sys.oem_unlock_allowed","0");
        infoMap.put("sys.rescue_boot_count","1");
        infoMap.put("sys.sysctl.extra_free_kbytes",String.valueOf(random.nextInt(8206) + 22169));
        infoMap.put("sys.system_server.start_count","1");
        infoMap.put("sys.system_server.start_elapsed",String.valueOf(random.nextInt(8206)));
        infoMap.put("sys.system_server.start_uptime",String.valueOf(random.nextInt(8206)));
        infoMap.put("sys.usb.config","none");
        infoMap.put("sys.usb.configfs","1");
        infoMap.put("sys.usb.controller","a600000.dwc3");
        infoMap.put("sys.usb.mtp.device_type","3");
        infoMap.put("sys.usb.state",SYSTEM_INSTANCE.nextString(20));
        infoMap.put("sys.use_memfd","false");
        infoMap.put("sys.user.0.ce_available","true");
        infoMap.put("sys.vendor.shutdown.waittime",String.valueOf(random.nextInt(500)));
        infoMap.put("sys.wifitracing.started","1");
        infoMap.put("vendor.camera.aux.packagelist","com.android.camera");//"org.codeaurora.snapcam,com.android.camera"
        infoMap.put("vendor.camera.sensor.frontMain.fuseID","sunn" + SYSTEM_INSTANCE.nextRandomHexUpper(60));//"sunn001200D7999E0DE000000000000000000000000000000000000000000000"
        infoMap.put("vendor.camera.sensor.rearDepth.fuseID","ofil" + SYSTEM_INSTANCE.nextRandomHexUpper(60));//"ofil0018192C5A30303030373800000000000000000000000000000000000000"
        infoMap.put("vendor.camera.sensor.rearMacro.fuseID","sunn" + SYSTEM_INSTANCE.nextRandomHexUpper(60));//"sunn001618704535F49020800000000000000000000000000000000000000000"
        infoMap.put("vendor.camera.sensor.rearMain.fuseID","sunn" + SYSTEM_INSTANCE.nextRandomHexUpper(60));//"sunn00229C60686C000205796101000000000000000000000000000000000000"
        infoMap.put("vendor.camera.sensor.rearUltra.fuseID","sunn" + SYSTEM_INSTANCE.nextRandomHexUpper(60));//"sunn00321F130B01000A19020A000903060D2401000000000000000000000000"
        infoMap.put("vendor.gralloc.disable_ubwc","0");
        infoMap.put("vendor.iop.enable_prefetch_ofr","1");
        infoMap.put("vendor.iop.enable_uxe","0");
        infoMap.put("vendor.sys.listeners.registered","true");
        infoMap.put("vold.has_adoptable","0");
        infoMap.put("vold.has_compress","0");
        infoMap.put("vold.has_quota","1");
        infoMap.put("vold.has_reserved","1");
    }
    public FakeProcInfoGenerator(){
    }

    public Process generate(String procInfo){
        StringBuilder outStr = new StringBuilder();
        InputStream inputStream = null;
        OutputStream outputStream = new ByteArrayOutputStream(1);
        if (procInfo == null){
            for (Map.Entry<String,String> entry:infoMap.entrySet()){
                outStr.append("[");
                outStr.append(entry.getKey());
                outStr.append("]");
                outStr.append(": ");
                outStr.append("[");
                outStr.append(entry.getValue());
                outStr.append("]\n");
                inputStream = new ByteArrayInputStream(outStr.toString().getBytes());
            }
        }else if(infoMap.containsKey(procInfo)){
            outStr.append("[");
            outStr.append(procInfo);
            outStr.append("]");
            outStr.append(": ");
            outStr.append("[");
            outStr.append(infoMap.get(procInfo));
            outStr.append("]\n");
            inputStream = new ByteArrayInputStream(outStr.toString().getBytes());
        }else {
            inputStream = new ByteArrayInputStream("\n".getBytes());
        }

        return generateProcess(inputStream,ListenerByteArrayOutputStream.INSTANCE,inputStream);
    }

    public static Process generateProcess(InputStream inputStream,OutputStream outputStream,InputStream errorStream){
        return new Process() {
            @Override
            public OutputStream getOutputStream() {
                return outputStream;
            }

            @Override
            public InputStream getInputStream() {
                return inputStream;
            }

            @Override
            public InputStream getErrorStream() {
                return errorStream;
            }

            @Override
            public int waitFor() throws InterruptedException {
                return 0;
            }

            @Override
            public int exitValue() {
                return 0;
            }

            @Override
            public void destroy() {

            }
        };
    }

    public static final Process emptyProcess = new Process() {
        @Override
        public OutputStream getOutputStream() {
            return ListenerByteArrayOutputStream.INSTANCE;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream("\n".getBytes());
        }

        @Override
        public InputStream getErrorStream() {
            return new ByteArrayInputStream("\n".getBytes());
        }

        @Override
        public int waitFor() throws InterruptedException {
            return 0;
        }

        @Override
        public int exitValue() {
            return 0;
        }

        @Override
        public void destroy() {

        }
    };
}
