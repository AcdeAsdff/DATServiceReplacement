#for developing resetPropGenerator

a = """aaudio.hw_burst_min_usec: 2000
aaudio.mmap_exclusive_policy: 2
aaudio.mmap_policy: 2
apexd.status: ready
bootreceiver.enable: 0
bpf.progs_loaded: 1
build.version.extensions.r: 1
build.version.extensions.s: 1
camera.disable_zsl_mode: true
dalvik.vm.appimageformat: lz4
dalvik.vm.dex2oat-Xms: 64m
dalvik.vm.dex2oat-Xmx: 512m
dalvik.vm.dex2oat-max-image-block-size: 524288
dalvik.vm.dex2oat-minidebuginfo: true
dalvik.vm.dex2oat-resolve-startup-strings: true
dalvik.vm.dex2oat-updatable-bcp-packages-file: /system/etc/updatable-bcp-packages.txt
dalvik.vm.dexopt.secondary: true
dalvik.vm.dexopt.thermal-cutoff: 2
dalvik.vm.heapgrowthlimit: 256m
dalvik.vm.heapmaxfree: 32m
dalvik.vm.heapminfree: 8m
dalvik.vm.heapsize: 512m
dalvik.vm.heapstartsize: 16m
dalvik.vm.heaptargetutilization: 0.5
dalvik.vm.image-dex2oat-Xms: 64m
dalvik.vm.image-dex2oat-Xmx: 64m
dalvik.vm.isa.arm.features: default
dalvik.vm.isa.arm.variant: kryo385
dalvik.vm.isa.arm64.features: default
dalvik.vm.isa.arm64.variant: kryo385
dalvik.vm.lockprof.threshold: 500
dalvik.vm.madvise.artfile.size: 4294967295
dalvik.vm.madvise.odexfile.size: 104857600
dalvik.vm.madvise.vdexfile.size: 104857600
dalvik.vm.minidebuginfo: true
dalvik.vm.usejit: true
dalvik.vm.usejitprofiles: true
debug.atrace.tags.enableflags: 0
debug.egl.hw: 0
debug.force_rtl: false
debug.gralloc.gfx_ubwc_disable: 0
debug.mdpcomp.logs: 0
debug.sf.enable_advanced_sf_phase_offset: 1
debug.sf.enable_gl_backpressure: 1
debug.sf.enable_hwc_vds: 1
debug.sf.high_fps_early_gl_phase_offset_ns: -4000000
debug.sf.high_fps_early_phase_offset_ns: -4000000
debug.sf.high_fps_late_app_phase_offset_ns: 1000000
debug.sf.high_fps_late_sf_phase_offset_ns: -4000000
debug.sf.hw: 0
debug.sf.latch_unsignaled: 1
debug.stagefright.ccodec: 4
debug.stagefright.omx_default_rank: 0
dev.bootcomplete: 1
dev.mnt.blk.cache: sda
dev.mnt.blk.data: dm-5
dev.mnt.blk.data.user.0: dm-5
dev.mnt.blk.data_mirror.cur_profiles: dm-5
dev.mnt.blk.data_mirror.data_ce.null: dm-5
dev.mnt.blk.data_mirror.data_ce.null.0: dm-5
dev.mnt.blk.data_mirror.data_de.null: dm-5
dev.mnt.blk.data_mirror.ref_profiles: dm-5
dev.mnt.blk.dev.logfs: sda
dev.mnt.blk.metadata: sda
dev.mnt.blk.mnt.vendor.persist: sda
dev.mnt.blk.mnt.vendor.spunvm: sde
dev.mnt.blk.odm: dm-3
dev.mnt.blk.product: dm-2
dev.mnt.blk.root: dm-0
dev.mnt.blk.system_ext: dm-4
dev.mnt.blk.vendor: dm-1
dev.mnt.blk.vendor.bt_firmware: sde
dev.mnt.blk.vendor.dsp: sde
dev.mnt.blk.vendor.firmware_mnt: sde
drm.service.enabled: true
gsid.image_installed: 0
gsm.current.phone-type: 1,1
gsm.network.type: LTE,Unknown
gsm.operator.alpha: CHN-UNICOM,
gsm.operator.iso-country: cn,
gsm.operator.isroaming: false,false
gsm.operator.numeric: 46001,
gsm.sim.operator.alpha: China Unicom
gsm.sim.operator.iso-country: cn
gsm.sim.operator.numeric: 46001
gsm.sim.state: LOADED,ABSENT
hwservicemanager.ready: true
init.svc.adb_root: running
init.svc.adbd: running
init.svc.alarm-hal-1-0: stopped
init.svc.android.thermal-hal: running
init.svc.apexd: stopped
init.svc.apexd-bootstrap: stopped
init.svc.apexd-snapshotde: stopped
init.svc.audioserver: running
init.svc.batterysecret: running
init.svc.bootanim: stopped
init.svc.boringssl_self_test32: stopped
init.svc.boringssl_self_test32_vendor: stopped
init.svc.boringssl_self_test64: stopped
init.svc.boringssl_self_test64_vendor: stopped
init.svc.boringssl_self_test_apex32: stopped
init.svc.boringssl_self_test_apex64: stopped
init.svc.bpfloader: stopped
init.svc.cameraserver: running
init.svc.cnss-daemon: running
init.svc.credstore: running
init.svc.dcvs-sh: stopped
init.svc.derive_classpath: stopped
init.svc.derive_sdk: stopped
init.svc.display-color-hal-1-0: running
init.svc.dpmQmiMgr: running
init.svc.dpmd: running
init.svc.drm: running
init.svc.feature_enabler_client: running
init.svc.gatekeeper-1-0: running
init.svc.gatekeeperd: running
init.svc.gnss_service: running
init.svc.gpu: running
init.svc.health-hal-2-1: running
init.svc.heapprofd: stopped
init.svc.hidl_memory: running
init.svc.hwservicemanager: running
init.svc.incidentd: running
init.svc.installd: running
init.svc.irsc_util: stopped
init.svc.keymaster-4-0: running
init.svc.keystore2: running
init.svc.lmkd: running
init.svc.loc_launcher: running
init.svc.logcatd: running
init.svc.logd: running
init.svc.logd-auditctl: stopped
init.svc.logd-reinit: stopped
init.svc.media: running
init.svc.media.swcodec: running
init.svc.mediadrm: running
init.svc.mediaextractor: running
init.svc.mediametrics: running
init.svc.mi_thermald: running
init.svc.mlid: running
init.svc.netd: running
init.svc.neuralnetworks_hal_service: running
init.svc.odsign: stopped
init.svc.qccvndhalservice: running
init.svc.qcom-c_main-sh: stopped
init.svc.qcom-post-boot: stopped
init.svc.qcom-sh: stopped
init.svc.qseecom-service: running
init.svc.qteeconnector-hal-1-0: running
init.svc.qti_esepowermanager_service_1_1: running
init.svc.secureelement-hal_1_2: running
init.svc.sensorscal-hal-1-0: running
init.svc.servicemanager: running
init.svc.soter-1-0: running
init.svc.statsd: running
init.svc.storaged: running
init.svc.surfaceflinger: running
init.svc.system_suspend: running
init.svc.time_daemon: running
init.svc.tombstoned: running
init.svc.traced: running
init.svc.traced_perf: stopped
init.svc.traced_probes: running
init.svc.tui_comm-1-0: running
init.svc.ueventd: running
init.svc.usbd: stopped
init.svc.vendor-sensor-sh: stopped
init.svc.vendor.adsprpcd: running
init.svc.vendor.adsprpcd_audiopd: running
init.svc.vendor.atfwd: running
init.svc.vendor.audio-hal: running
init.svc.vendor.bluetooth-1-0-qti: running
init.svc.vendor.btmac-sh: stopped
init.svc.vendor.camera-provider-2-4: running
init.svc.vendor.capabilityconfigstore: running
init.svc.vendor.cas-hal-1-2: running
init.svc.vendor.cdsprpcd: running
init.svc.vendor.cnd: running
init.svc.vendor.dataadpl: stopped
init.svc.vendor.dataqti: running
init.svc.vendor.drm-clearkey-hal-1-4: running
init.svc.vendor.drm-widevine-hal-1-3: running
init.svc.vendor.dspservice: running
init.svc.vendor.fps_hal: running
init.svc.vendor.hvdcp_opti: running
init.svc.vendor.ims_rtp_daemon: running
init.svc.vendor.imsdatadaemon: running
init.svc.vendor.imsqmidaemon: running
init.svc.vendor.ipacm: running
init.svc.vendor.ir-hal-1-0: running
init.svc.vendor.light-default: running
init.svc.vendor.livedisplay-hal-2-0-sdm: running
init.svc.vendor.lowi: running
init.svc.vendor.mdm_helper: running
init.svc.vendor.mdm_launcher: stopped
init.svc.vendor.media.omx: running
init.svc.vendor.memtrack-hal-1-0: running
init.svc.vendor.mlipay-1-1: running
init.svc.vendor.motor: running
init.svc.vendor.msm_irqbalance: running
init.svc.vendor.netmgrd: running
init.svc.vendor.nfc_hal_service: running
init.svc.vendor.nv_mac: stopped
init.svc.vendor.pd_mapper: running
init.svc.vendor.per_mgr: running
init.svc.vendor.per_proxy: running
init.svc.vendor.port-bridge: running
init.svc.vendor.power-hal-aidl: running
init.svc.vendor.qcrild: running
init.svc.vendor.qcrild2: running
init.svc.vendor.qmipriod: running
init.svc.vendor.qrtr-ns: running
init.svc.vendor.qseecomd: running
init.svc.vendor.qti-chg-policy-sh: stopped
init.svc.vendor.qti.hardware.display.allocator: running
init.svc.vendor.qti.hardware.display.composer: running
init.svc.vendor.qti.vibrator.xiaomi_kona: running
init.svc.vendor.rmt_storage: stopped
init.svc.vendor.sensors: running
init.svc.vendor.sensors-hal-1-0: running
init.svc.vendor.ssgtzd: running
init.svc.vendor.tftp_server: running
init.svc.vendor.touch-hal-1-0: running
init.svc.vendor.usb-hal-1-3: running
init.svc.vendor.vppservice: running
init.svc.vendor.wifi_hal_legacy: running
init.svc.vendor_flash_recovery: stopped
init.svc.vndservicemanager: running
init.svc.vold: running
init.svc.wait_for_keymaster: stopped
init.svc.wificond: running
init.svc.wifidisplayhalservice: running
init.svc.zygote: running
init.svc.zygote_secondary: running
init.svc_debug_pid.alarm-hal-1-0:
init.svc_debug_pid.apexd:
init.svc_debug_pid.apexd-bootstrap:
init.svc_debug_pid.apexd-snapshotde:
init.svc_debug_pid.bootanim:
init.svc_debug_pid.boringssl_self_test32:
init.svc_debug_pid.boringssl_self_test32_vendor:
init.svc_debug_pid.boringssl_self_test64:
init.svc_debug_pid.boringssl_self_test64_vendor:
init.svc_debug_pid.boringssl_self_test_apex32:
init.svc_debug_pid.boringssl_self_test_apex64:
init.svc_debug_pid.bpfloader:
init.svc_debug_pid.dcvs-sh:
init.svc_debug_pid.derive_classpath:
init.svc_debug_pid.derive_sdk:
init.svc_debug_pid.heapprofd:
init.svc_debug_pid.irsc_util:
init.svc_debug_pid.logd-auditctl:
init.svc_debug_pid.logd-reinit:
init.svc_debug_pid.odsign:
init.svc_debug_pid.qcom-c_main-sh:
init.svc_debug_pid.qcom-post-boot:
init.svc_debug_pid.qcom-sh:
init.svc_debug_pid.traced_perf:
init.svc_debug_pid.usbd:
init.svc_debug_pid.vendor-sensor-sh:
init.svc_debug_pid.vendor.btmac-sh:
init.svc_debug_pid.vendor.dataadpl:
init.svc_debug_pid.vendor.mdm_launcher:
init.svc_debug_pid.vendor.nv_mac:
init.svc_debug_pid.vendor.qti-chg-policy-sh:
init.svc_debug_pid.vendor.rmt_storage:
init.svc_debug_pid.vendor_flash_recovery:
init.svc_debug_pid.wait_for_keymaster:
keyguard.no_require_sim: true
keystore.boot_level: 1000000000
keystore.crash_count: 0
khungtask.enable: false
llk.enable: false
lmkd.reinit:
log.tag.APM_AudioPolicyManager: D
log.tag.stats_log: I
logd.logpersistd: logcatd
logd.logpersistd.buffer: all
logd.logpersistd.enable: true
media.recorder.show_manufacturer_and_model: true
media.settings.xml: /vendor/etc/media_profiles_vendor.xml
net.464xlat.cellular.enabled: false
net.bt.name: Android
net.qtaguid_enabled: 1
net.tcp.2g_init_rwnd: 10
net.tcp_def_init_rwnd: 60
net.tethering.noprovisioning: true
nfc.initialized: true
odsign.key.done: 1
odsign.verification.done: 1
odsign.verification.success: 0
persist.bluetooth.a2dp_offload.cap: sbc-aac-aptx-aptxhd-ldac
persist.bluetooth.a2dp_offload.disabled: false
persist.bluetooth.avrcpversion: avrcp15
persist.bluetooth.btsnoopdefaultmode:
persist.bluetooth.btsnooplogmode:
persist.bluetooth.disableabsvol: false
persist.bluetooth.mapversion: map12
persist.bluetooth.maxconnectedaudiodevices:
persist.bluetooth.showdeviceswithoutnames: false
persist.dbg.volte_avail_ovr: 1
persist.dbg.vt_avail_ovr: 1
persist.dbg.wfc_avail_ovr: 1
persist.debug.dalvik.vm.core_platform_api_policy: just-warn
persist.device_config.attempted_boot_count: 0
persist.device_config.runtime_native.usap_pool_enabled: true
persist.log.tag:
persist.logd.logpersistd: logcatd
persist.logd.logpersistd.buffer: all
persist.logd.size: -1
persist.logd.size.crash: -1
persist.logd.size.main: -1
persist.netd.stable_secret: e9ba:66fd:803b:cb60:cd64:8fb3:c35c:ea99
persist.radio.active.slots: 2
persist.radio.airplane_mode_on: 0
persist.radio.autoselect_by_uim: 7
persist.radio.display_mipi_cur: 0
persist.radio.display_mipi_set_num: 0
persist.radio.goldencopy_flag: true
persist.radio.is_vonr_enabled_0: false
persist.radio.is_vonr_enabled_1: false
persist.radio.meid:
persist.radio.modem_mbn_check: 1
persist.radio.modem_sw_mbn_flag: 1
persist.radio.mtbf_flag: false
persist.radio.multisim.config: dsds
persist.radio.operating_mode: 0
persist.radio.skhwc_matchres: MATCH
persist.radio.speech_codec:
persist.rcs.supported: 0
persist.sys.boot.reason:
persist.sys.boot.reason.history: reboot,,1717812155
reboot,,1717811173
reboot,,1717811136
reboot,,1717811092
persist.sys.dalvik.vm.lib.2: libart.so
persist.sys.device_provisioned: 1
persist.sys.displayinset.top: 0
persist.sys.fflag.override.settings_bluetooth_hearing_aid: true
persist.sys.fuse: true
persist.sys.gps.lpp: 2
persist.sys.lmk.reportkills: true
persist.sys.locale: zh-CN
persist.sys.max.profiles: 2
persist.sys.oem.otg_support: true
persist.sys.sf.color_mode: 9
persist.sys.sf.color_saturation: 1.0
persist.sys.sf.native_mode: 2
persist.sys.strictmode.disable: true
persist.sys.strictmode.visual:
persist.sys.theme: 2
persist.sys.timezone: Asia/Shanghai
persist.sys.usb.config: adb
persist.sys.vold_app_data_isolation_enabled: 1
persist.traced.enable: 1
persist.vendor.audio.ambisonic.auto.profile: false
persist.vendor.audio.ambisonic.capture: false
persist.vendor.audio.apptype.multirec.enabled: false
persist.vendor.audio.avs.afe_api_version: 2
persist.vendor.audio.fluence.speaker: true
persist.vendor.audio.fluence.tmic.enabled: false
persist.vendor.audio.fluence.voicecall: true
persist.vendor.audio.fluence.voicecomm: true
persist.vendor.audio.fluence.voicerec: false
persist.vendor.audio.hifi: false
persist.vendor.audio.ras.enabled: false
persist.vendor.audio.ring.filter.mask: 0
persist.vendor.audio.spv3.enable: true
persist.vendor.audio.voicecall.speaker.stereo: true
persist.vendor.audio_hal.dsp_bit_width_enforce_mode: 24
persist.vendor.bt.aac_frm_ctl.enabled: true
persist.vendor.bt.aac_vbr_frm_ctl.enabled: true
persist.vendor.camera.frontMain.info: /vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_s5k3t2_front.bin
persist.vendor.camera.frontMain.vendorID: 01
persist.vendor.camera.mi.dualcal.detail: 0
persist.vendor.camera.mi.dualcal.exist: 0
persist.vendor.camera.mi.dualcal.state: 0
persist.vendor.camera.mi.module.info: back_main=imx686;back_ultra=ov13b10;back_depth=gc02m1;
persist.vendor.camera.mi.module.infoext: front_main=s5k3t2;back_macro=s5k5e9;
persist.vendor.camera.rearDepth.info: /vendor/lib64/camera/com.qti.sensormodule.lmi_ofilm_gc02m1_depth.bin
persist.vendor.camera.rearDepth.vendorID: 07
persist.vendor.camera.rearMacro.info: /vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_s5k5e9yx04_macro.bin
persist.vendor.camera.rearMacro.vendorID: 01
persist.vendor.camera.rearMain.info: /vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_imx686_wide_mp.bin
persist.vendor.camera.rearMain.vendorID: 01
persist.vendor.camera.rearUltra.info: /vendor/lib64/camera/com.qti.sensormodule.lmi_sunny_ov13b10_ultra.bin
persist.vendor.camera.rearUltra.vendorID: 01
persist.vendor.cne.feature: 1
persist.vendor.color.matrix: 2
persist.vendor.data.mode: concurrent
persist.vendor.data.offload_ko_load: 0
persist.vendor.data.perf_ko_load: 3
persist.vendor.data.qmipriod_load: 1
persist.vendor.data.shs_ko_load: 1
persist.vendor.data.shsusr_load: 1
persist.vendor.dpm.feature: 1
persist.vendor.hvdcp_opti.start: 0
persist.vendor.hvdcp_opti.version: 3:0:0
persist.vendor.mmi.misc_dev_path: /dev/block/sda11
persist.vendor.motor.calibration.state: 1
persist.vendor.net.doxlat: false
persist.vendor.qcom.bluetooth.a2dp_mcast_test.enabled: false
persist.vendor.qcom.bluetooth.a2dp_offload_cap: sbc-aptx-aptxtws-aptxhd-aac-ldac-aptxadaptiver2
persist.vendor.qcom.bluetooth.aac_frm_ctl.enabled: true
persist.vendor.qcom.bluetooth.aac_vbr_ctl.enabled: true
persist.vendor.qcom.bluetooth.enable.splita2dp: true
persist.vendor.qcom.bluetooth.scram.enabled: false
persist.vendor.qcom.bluetooth.soc: hastings
persist.vendor.qcom.bluetooth.twsp_state.enabled: false
persist.vendor.qcomsysd.enabled: 1
persist.vendor.qfp: false
persist.vendor.radio.adb_log_on: 0
persist.vendor.radio.apm_sim_not_pwdn: 1
persist.vendor.radio.atfwd.start: true
persist.vendor.radio.cdma_cap: true
persist.vendor.radio.clir0: 0
persist.vendor.radio.clir1: 0
persist.vendor.radio.custom_ecc: 0
persist.vendor.radio.data_con_rprt: 1
persist.vendor.radio.data_ltd_sys_ind: 1
persist.vendor.radio.dynamic_sar: 1
persist.vendor.radio.enable_temp_dds: true
persist.vendor.radio.enableadvancedscan: true
persist.vendor.radio.force_ltd_sys_ind: 1
persist.vendor.radio.force_on_dc: true
persist.vendor.radio.manual_nw_rej_ct: 1
persist.vendor.radio.mbn_cdma: /vendor/firmware_mnt/image/modem_pr/mcfg/configs/mcfg_sw/oem_sw_j11.txt
persist.vendor.radio.meid: 99001621040584
persist.vendor.radio.msim.stackid_0: 0
persist.vendor.radio.msim.stackid_1: 1
persist.vendor.radio.procedure_bytes: SKIP
persist.vendor.radio.rat_on: combine
persist.vendor.radio.redir_party_num: 1
persist.vendor.radio.report_codec: 1
persist.vendor.radio.ril_payload_on: 0
persist.vendor.radio.sglte_target: 0
persist.vendor.radio.sib16_support: 1
persist.vendor.radio.stack_id_0: 0
persist.vendor.radio.stack_id_1: 1
persist.vendor.recovery_update: true
persist.vendor.sensors.allow_non_default_discovery: true
persist.vendor.sensors.sync_request: true
persist.vendor.service.bdroid.bdaddr: 98:f6:21:c9:1a:68
persist.vendor.service.bdroid.system_delay_crash_count: 0
persist.vendor.sys.fp.expolevel: 0x80
persist.vendor.sys.fp.fod.large_field: 0
persist.vendor.sys.fp.info: 0xff0000450f000000
persist.vendor.sys.fp.module: Ofilm
persist.vendor.sys.fp.mulexposupport: 0
persist.vendor.sys.fp.uid: 51313342-38342E01-BF7E747A-85AC0000
persist.vendor.sys.fp.vendor: goodix_fod
persist.vendor.sys.pay.ifaa: 1
persist.vendor.usb.config: diag,diag_mdm,qdss,qdss_mdm,serial_cdev,dpl,rmnet,adb
persist.vendor.usb.config.extra: none
pm.dexopt.ab-ota: speed-profile
pm.dexopt.bg-dexopt: speed-profile
pm.dexopt.boot-after-ota: verify
pm.dexopt.cmdline: verify
pm.dexopt.first-boot: verify
pm.dexopt.inactive: verify
pm.dexopt.install: speed-profile
pm.dexopt.install-bulk: speed-profile
pm.dexopt.install-bulk-downgraded: verify
pm.dexopt.install-bulk-secondary: verify
pm.dexopt.install-bulk-secondary-downgraded: extract
pm.dexopt.install-fast: skip
pm.dexopt.post-boot: extract
pm.dexopt.shared: speed
ril.ecclist: 112,000,08,110,120,119,118,122,911,999
ril.ecclist1: 112,000,08,110,120,119,118,122,911,999
ril.fake_bs_flag0: FALSE:0
ril.mcc.mnc0: 46001
ril.mcc.mnc1:
ro.actionable_compatible_property.enabled: true
ro.adb.secure: 1
ro.allow.mock.location: 0
ro.baseband: mdm
ro.bionic.2nd_arch: arm
ro.bionic.2nd_cpu_variant: kryo385
ro.bionic.arch: arm64
ro.bionic.cpu_variant: kryo385
ro.bluetooth.a2dp_offload.supported: true
ro.board.platform: kona
ro.boot.avb_version: 1.2
ro.boot.baseband: mdm
ro.boot.boot_devices: soc/1d84000.ufshc
ro.boot.bootdevice: 1d84000.ufshc
ro.boot.bootreason: reboot,
ro.boot.camera.config: 1
ro.boot.cert: M2001J11C
ro.boot.console: ttyMSM0
ro.boot.cpuid: 0x2b1139f2
ro.boot.dp: 0x0
ro.boot.dtb_idx: 0
ro.boot.dtbo_idx: 4
ro.boot.dynamic_partitions: true
ro.boot.fstab_suffix: qcom
ro.boot.hardware: qcom
ro.boot.hwc: CN
ro.boot.hwlevel: MP
ro.boot.hwversion: 3.9.1
ro.boot.init_fatal_reboot_target: recovery
ro.boot.keymaster: 1
ro.boot.memcg: 1
ro.boot.network.mode: 2
ro.boot.product.hardware.sku: nfc
ro.boot.ramdump: disable
ro.boot.secureboot: 1
ro.boot.usbcontroller: a600000.dwc3
ro.boot.verifiedbootstate: green
ro.bootimage.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.bootimage.build.version.release: 12
ro.bootimage.build.version.release_or_codename: 12
ro.bootimage.build.version.sdk: 32
ro.bootloader: unknown
ro.bootmode: unknown
ro.build.characteristics: default
ro.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.build.type: user
ro.build.user: root
ro.build.version.base_os:
ro.build.version.min_supported_target_sdk: 23
ro.build.version.preview_sdk: 0
ro.build.version.release: 12
ro.build.version.release_or_codename: 12
ro.build.version.sdk: 32
ro.build.version.security_patch: 2023-02-05
ro.camera.notify_nfc: 1
ro.carrier: unknown
ro.charger.disable_init_blank: true
ro.cold_boot_done: true
ro.com.android.dataroaming: true
ro.com.google.clientidbase: android-xiaomi
ro.config.alarm_alert: Hassium.ogg
ro.config.notification_sound: Argon.ogg
ro.config.ringtone: Orion.ogg
ro.config.vc_call_vol_steps: 11
ro.control_privapp_permissions: enforce
ro.crypto.allow_encrypt_override: true
ro.crypto.metadata.enabled: true
ro.crypto.state: encrypted
ro.crypto.type: file
ro.crypto.uses_fs_ioc_add_encryption_key: true
ro.crypto.volume.filenames_mode: aes-256-cts
ro.dalvik.vm.native.bridge: 0
ro.debuggable: 0
ro.frp.pst: /dev/block/bootdevice/by-name/frp
ro.gsid.image_running: 0
ro.hardware: qcom
ro.hardware.egl: adreno
ro.hardware.fp.udfps: true
ro.hardware.keystore_desede: true
ro.hardware.vulkan: adreno
ro.hwui.use_vulkan:
ro.iorapd.enable: true
ro.lmk.kill_heaviest_task: true
ro.lmk.kill_timeout_ms: 15
ro.lmk.use_minfree_levels: true
ro.logd.kernel: true
ro.logd.size.stats: 64K
ro.media.xml_variant.codecs: _kona
ro.media.xml_variant.codecs_performance: _kona
ro.minui.pixel_format: RGBX_8888
ro.odm.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.odm.build.version.release: 12
ro.odm.build.version.release_or_codename: 12
ro.odm.build.version.sdk: 32
ro.odm.product.cpu.abilist: arm64-v8a,armeabi-v7a,armeabi
ro.odm.product.cpu.abilist32: armeabi-v7a,armeabi
ro.odm.product.cpu.abilist64: arm64-v8a
ro.opengles.version: 196610
ro.organization_owned: false
ro.persistent_properties.ready: true
ro.postinstall.fstab.prefix: /system
ro.product.board: kona
ro.product.bootimage.name: lineage_lmi
ro.product.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.product.build.version.release: 12
ro.product.build.version.release_or_codename: 12
ro.product.build.version.sdk: 32
ro.product.cpu.abi: arm64-v8a
ro.product.cpu.abilist: arm64-v8a,armeabi-v7a,armeabi
ro.product.cpu.abilist32: armeabi-v7a,armeabi
ro.product.cpu.abilist64: arm64-v8a
ro.product.first_api_level: 29
ro.product.locale: en-US
ro.product.marketname:
ro.product.name: lineage_lmi
ro.product.odm.marketname:
ro.product.odm.name: lineage_lmi
ro.product.odm_dlkm.marketname:
ro.product.product.marketname:
ro.product.product.name: lineage_lmi
ro.product.system.marketname:
ro.product.system.name: lineage_lmi
ro.product.system_ext.marketname:
ro.product.system_ext.name: lineage_lmi
ro.product.vendor.marketname:
ro.product.vendor.name: lineage_lmi
ro.product.vendor_dlkm.marketname:
ro.product.vendor_dlkm.name: lineage_lmi
ro.property_service.version: 2
ro.revision: 0
ro.ril.factory_id: 9
ro.ril.nal:
ro.ril.operator:
ro.ril.region:
ro.secure: 1
ro.sf.lcd_density: 440
ro.storage_manager.enabled: true
ro.support_one_handed_mode: true
ro.surface_flinger.force_hwc_copy_for_virtual_displays: true
ro.surface_flinger.has_HDR_display: true
ro.surface_flinger.has_wide_color_display: true
ro.surface_flinger.max_frame_buffer_acquired_buffers: 3
ro.surface_flinger.max_virtual_display_dimension: 4096
ro.surface_flinger.protected_contents: true
ro.surface_flinger.use_color_management: true
ro.surface_flinger.wcg_composition_dataspace: 143261696
ro.system.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.system.build.version.release: 12
ro.system.build.version.release_or_codename: 12
ro.system.build.version.sdk: 32
ro.system.product.cpu.abilist: arm64-v8a,armeabi-v7a,armeabi
ro.system.product.cpu.abilist32: armeabi-v7a,armeabi
ro.system.product.cpu.abilist64: arm64-v8a
ro.system_ext.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.system_ext.build.version.release: 12
ro.system_ext.build.version.release_or_codename: 12
ro.system_ext.build.version.sdk: 32
ro.telephony.call_ring.multiple: false
ro.telephony.default_network: 22,22
ro.treble.enabled: true
ro.vendor.alarm_boot: false
ro.vendor.audio.game.effect: true
ro.vendor.audio.hifi: true
ro.vendor.audio.ring.filter: true
ro.vendor.audio.scenario.support: true
ro.vendor.audio.sdk.fluencetype: fluence
ro.vendor.audio.sdk.ssr: false
ro.vendor.audio.sfx.audiovisual: true
ro.vendor.audio.sfx.earadj: true
ro.vendor.audio.sfx.scenario: true
ro.vendor.audio.sos: true
ro.vendor.audio.soundfx.type: mi
ro.vendor.audio.soundfx.usb: true
ro.vendor.audio.soundtrigger: sva
ro.vendor.audio.soundtrigger.appdefine.cnn.level: 35
ro.vendor.audio.soundtrigger.appdefine.gmm.level: 55
ro.vendor.audio.soundtrigger.appdefine.gmm.user.level: 65
ro.vendor.audio.soundtrigger.appdefine.vop.level: 10
ro.vendor.audio.soundtrigger.lowpower: true
ro.vendor.audio.soundtrigger.snr: 16
ro.vendor.audio.soundtrigger.training.level: 50
ro.vendor.audio.soundtrigger.xanzn.cnn.level: 80
ro.vendor.audio.soundtrigger.xanzn.gmm.level: 45
ro.vendor.audio.soundtrigger.xanzn.gmm.user.level: 30
ro.vendor.audio.soundtrigger.xanzn.vop.level: 10
ro.vendor.audio.soundtrigger.xatx.cnn.level: 27
ro.vendor.audio.soundtrigger.xatx.gmm.level: 50
ro.vendor.audio.soundtrigger.xatx.gmm.user.level: 40
ro.vendor.audio.soundtrigger.xatx.vop.level: 10
ro.vendor.audio.surround.support: true
ro.vendor.audio.us.proximity: true
ro.vendor.audio.vocal.support: true
ro.vendor.audio.voice.change.support: true
ro.vendor.audio.voice.change.youme.support: true
ro.vendor.audio.voice.volume.boost: none
ro.vendor.bluetooth.wipower: false
ro.vendor.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.vendor.build.version.release: 12
ro.vendor.build.version.release_or_codename: 12
ro.vendor.build.version.sdk: 32
ro.vendor.display.sensortype: 2
ro.vendor.extension_library: libqti-perfd-client.so
ro.vendor.gpu.available_frequencies: 587000000 525000000 490000000 441600000 400000000 305000000
ro.vendor.product.cpu.abilist: arm64-v8a,armeabi-v7a,armeabi
ro.vendor.product.cpu.abilist32: armeabi-v7a,armeabi
ro.vendor.product.cpu.abilist64: arm64-v8a
ro.vendor.qti.core_ctl_max_cpu: 4
ro.vendor.qti.core_ctl_min_cpu: 2
ro.vendor.qti.va_aosp.support: 1
ro.vendor.qti.va_odm.support: 1
ro.vendor.ril.mbn_copy_completed: 1
ro.vendor.ril.svdo: false
ro.vendor.ril.svlte1x: false
ro.vendor_dlkm.build.date: Sat Mar  4 04:10:47 UTC 2023
ro.vendor_dlkm.build.version.release: 12
ro.vendor_dlkm.build.version.release_or_codename: 12
ro.vendor_dlkm.build.version.sdk: 32
ro.vndk.version: 32
ro.wifi.channels:
ro.zygote: zygote64_32
security.perf_harden: 1
selinux.restorecon_recursive: /data/misc_ce/0
service.bootanim.exit: 1
service.bootanim.progress: 0
service.sf.present_timestamp: 1
sys.boot.reason: reboot,
sys.boot.reason.last: reboot,
sys.boot_completed: 1
sys.bootstat.first_boot_completed: 1
sys.fflag.override.settings_bluetooth_hearing_aid: true
sys.fflag.override.settings_seamless_transfer: true
sys.fuse.transcode_enabled: true
sys.init.perf_lsm_hooks: 1
sys.lmk.minfree_levels: 18432:0,23040:100,27648:200,32256:250,55296:900,80640:950
sys.lmk.reportkills: 1
sys.oem_unlock_allowed: 0
sys.rescue_boot_count: 1
sys.retaildemo.enabled: 0
sys.sysctl.extra_free_kbytes: 30375
sys.system_server.start_count: 1
sys.usb.config: rndis,none,adb
sys.usb.configfs: 1
sys.usb.controller: a600000.dwc3
sys.usb.ffs.ready: 1
sys.usb.mtp.device_type: 3
sys.usb.state: rndis,adb
sys.use_memfd: false
sys.user.0.ce_available: true
sys.vendor.shutdown.waittime: 500
sys.wifitracing.started: 1
tombstoned.max_tombstone_count: 50
vendor.audio.adm.buffering.ms: 2
vendor.audio.capture.enforce_legacy_copp_sr: true
vendor.audio.chk.cal.spk: 1
vendor.audio.chk.cal.us: 1
vendor.audio.dolby.ds2.enabled: false
vendor.audio.dolby.ds2.hardbypass: false
vendor.audio.enable.mirrorlink: false
vendor.audio.feature.a2dp_offload.enable: true
vendor.audio.feature.afe_proxy.enable: true
vendor.audio.feature.anc_headset.enable: false
vendor.audio.feature.audiozoom.enable: false
vendor.audio.feature.battery_listener.enable: true
vendor.audio.feature.compr_cap.enable: false
vendor.audio.feature.compress_in.enable: true
vendor.audio.feature.compress_meta_data.enable: true
vendor.audio.feature.concurrent_capture.enable: true
vendor.audio.feature.custom_stereo.enable: true
vendor.audio.feature.deepbuffer_as_primary.enable: false
vendor.audio.feature.display_port.enable: true
vendor.audio.feature.dsm_feedback.enable: false
vendor.audio.feature.dynamic_ecns.enable: false
vendor.audio.feature.ext_hw_plugin.enable: false
vendor.audio.feature.external_dsp.enable: false
vendor.audio.feature.external_speaker.enable: false
vendor.audio.feature.external_speaker_tfa.enable: false
vendor.audio.feature.fluence.enable: true
vendor.audio.feature.fm.enable: true
vendor.audio.feature.hdmi_edid.enable: true
vendor.audio.feature.hdmi_passthrough.enable: true
vendor.audio.feature.hfp.enable: true
vendor.audio.feature.hifi_audio.enable: false
vendor.audio.feature.hwdep_cal.enable: false
vendor.audio.feature.incall_music.enable: true
vendor.audio.feature.keep_alive.enable: true
vendor.audio.feature.kpi_optimize.enable: true
vendor.audio.feature.maxx_audio.enable: false
vendor.audio.feature.multi_voice_session.enable: true
vendor.audio.feature.ras.enable: true
vendor.audio.feature.record_play_concurency.enable: false
vendor.audio.feature.snd_mon.enable: true
vendor.audio.feature.spkr_prot.enable: false
vendor.audio.feature.src_trkn.enable: true
vendor.audio.feature.ssrec.enable: true
vendor.audio.feature.usb_offload.enable: true
vendor.audio.feature.usb_offload_burst_mode.enable: true
vendor.audio.feature.usb_offload_sidetone_volume.enable: false
vendor.audio.feature.vbat.enable: true
vendor.audio.feature.wsa.enable: false
vendor.audio.flac.sw.decoder.24bit: true
vendor.audio.hal.output.suspend.supported: false
vendor.audio.hw.aac.encoder: true
vendor.audio.mic.status: off
vendor.audio.offload.buffer.size.kb: 256
vendor.audio.offload.gapless.enabled: true
vendor.audio.offload.multiaac.enable: true
vendor.audio.offload.multiple.enabled: false
vendor.audio.offload.passthrough: false
vendor.audio.offload.track.enable: false
vendor.audio.parser.ip.buffer.size: 262144
vendor.audio.safx.pbe.enabled: false
vendor.audio.spkcal.copy.inhal: true
vendor.audio.spkr_prot.tx.sampling_rate: 48000
vendor.audio.tunnel.encode: false
vendor.audio.usb.disable.sidetone: true
vendor.audio.use.sw.alac.decoder: true
vendor.audio.use.sw.ape.decoder: true
vendor.audio.use.sw.mpegh.decoder: true
vendor.audio.voice.receiver.status: off
vendor.audio.volume.headset.gain.depcal: true
vendor.audio_hal.in_period_size: 144
vendor.audio_hal.period_multiplier: 2
vendor.audio_hal.period_size: 192
vendor.dcvs.prop: 1
vendor.display.comp_mask: 0
vendor.display.disable_excl_rect: 0
vendor.display.disable_excl_rect_partial_fb: 1
vendor.display.disable_hw_recovery_dump: 1
vendor.display.disable_offline_rotator: 1
vendor.display.disable_scaler: 0
vendor.display.enable_async_powermode: 0
vendor.display.enable_optimize_refresh: 1
vendor.display.enable_posted_start_dyn: 1
vendor.display.lcd_density: 480
vendor.display.qdcm.disable_factory_mode: 1
vendor.display.qdcm.mode_combine: 1
vendor.display.use_layer_ext: 0
vendor.display.use_smooth_motion: 0
vendor.gatekeeper.disable_spu: true
vendor.gpu.available_frequencies: 587000000 525000000 490000000 441600000 400000000 305000000
vendor.gralloc.disable_ubwc: 0
vendor.ims.DATA_DAEMON_STATUS: 1
vendor.ims.ENABLE_HELPER: 1
vendor.ims.QMI_DAEMON_STATUS: 1
vendor.ims.activesub: 0
vendor.ims.modem.multisub.cap: 2
vendor.ims.rcs_version: 0
vendor.iop.enable_prefetch_ofr: 1
vendor.iop.enable_uxe: 0
vendor.media.target_variant: _kona
vendor.peripheral.SDX55M.state: ONLINE
vendor.peripheral.shutdown_critical_list: SDX55M
vendor.peripheral.slpi.state: OFFLINE
vendor.peripheral.spss.state: OFFLINE
vendor.post_boot.parsed: 1
vendor.power.pasr.enabled: false
vendor.powerhal.init: 1
vendor.powerhal.rendering:
vendor.qcom.bluetooth.soc: hastings
vendor.radio.db_upgrade: 1
vendor.radio.qcril_pre_init_lock_held: 0
vendor.sys.listeners.registered: true
vendor.sys.thermal.data.path: /data/vendor/thermal/
vendor.usb.configfs: 1
vendor.usb.controller: a600000.dwc3
vendor.usb.diag.func.name: diag
vendor.usb.dpl.inst.name: dpl
vendor.usb.qdss.inst.name: qdss
vendor.usb.rmnet.func.name: gsi
vendor.usb.rmnet.inst.name: rmnet
vendor.usb.rndis.func.name: gsi
vendor.usb.rps_mask: 0
vendor.usb.use_ffs_mtp: 0
vendor.voice.path.for.pcm.voip: true
vold.has_adoptable: 0
vold.has_compress: 0
vold.has_quota: 1
vold.has_reserved: 1
vold.post_fs_data_done: 1
wifi.aware.interface: wifi-aware0
wifi.interface: wlan0"""
for s in a.split("\n"):
    # if "57e323c73e" in s.split(":")[1]:
    #     print("\"" + s.split(":")[0] + "\",", end="")
    value = ""
    if (len(s.split(": "))==2):
        value = s.split(": ")[1]
    else:
        continue
    if "lineage_lmi" == s.split(": ")[1]:
        print("\"" + s.split(":")[0] + "\",",end="")