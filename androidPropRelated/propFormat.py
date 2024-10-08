import os

from fieldLists import *

phoneTemplate = [
    "0unset",  # 0 # ro.product.model / ro.product.system.model
    "1unset",  # ro.build.version.sdk
    "2unset",  # ro.build.version.release
    "3unset",  # ro.product.system.manufacturer
    "4unset",  # ro.product.system.brand
    "5unset",  # 5 #ro.build.fingerprint
    "6unset",  # ro.build.id
    "7unset",  # 7 #ro.build.flavor
    "8unset",  # ro.build.product,useless here
    "unset",  # "9unset",  # ro.soc.manufacturer
    "unset",  # 10 # gsm.version.ril-impl
    "unset",  # ro.soc.model
    "CookedPotato",  # 12 # ro.modversion
    {
        "ro.build.version.incremental": "V13.0.2.1.37.DEV",
        "ro.build.description": "aeon6580_weg_l_l300-user 5.1 LMY47I 1555741941 release-keys",
    },
    "14unset",  # 14 #ro.system.build.date
    "15unset",  # ro.system.build.date.utc
    "16unset",  # ro.build.version.security_patch
    "release-keys",
    "user",
    "19unset",  # 19
]
# a = """####################################
# # from generate-common-build-props
# # These properties identify this partition image.
# ####################################
# ro.product.system.brand=samsung
# ro.product.system.device=qssi
# ro.product.system.manufacturer=samsung
# ro.product.system.model=SM-T975
# ro.product.system.name=gts7xlxx
# ro.system.product.cpu.abilist=arm64-v8a,armeabi-v7a,armeabi
# ro.system.product.cpu.abilist32=armeabi-v7a,armeabi
# ro.system.product.cpu.abilist64=arm64-v8a
# ro.system.qb.id=62033296
# ro.system.build.date=Mon Feb  6 20:14:50 +07 2023
# ro.system.build.date.utc=1675689290
# ro.system.build.fingerprint=samsung/gts7xlxx/qssi:13/TP1A.220624.014/T975XXU2DWB2:user/release-keys
# ro.system.build.id=TP1A.220624.014
# ro.system.build.tags=release-keys
# ro.system.build.type=user
# ro.system.build.version.incremental=T975XXU2DWB2
# ro.system.build.version.release=13
# ro.system.build.version.release_or_codename=13
# ro.system.build.version.sdk=33
# ####################################
# # from out/target/product/qssi/obj/PACKAGING/system_build_prop_intermediates/buildinfo.prop
# ####################################
# # begin build properties
# # autogenerated by buildinfo.sh
# ro.build.id=TP1A.220624.014
# ro.build.display.id=TP1A.220624.014.T975XXU2DWB2
# ro.build.display_build_number=true
# ro.build.version.incremental=T975XXU2DWB2
# ro.build.version.sdk=33
# ro.build.version.preview_sdk=0
# ro.build.version.preview_sdk_fingerprint=REL
# ro.build.version.codename=REL
# ro.build.version.all_codenames=REL
# ro.build.version.known_codenames=Base,Base11,Cupcake,Donut,Eclair,Eclair01,EclairMr1,Froyo,Gingerbread,GingerbreadMr1,Honeycomb,HoneycombMr1,HoneycombMr2,IceCreamSandwich,IceCreamSandwichMr1,JellyBean,JellyBeanMr1,JellyBeanMr2,Kitkat,KitkatWatch,Lollipop,LollipopMr1,M,N,NMr1,O,OMr1,P,Q,R,S,Sv2,Tiramisu
# ro.build.version.release=13
# ro.build.version.release_or_codename=13
# ro.build.version.release_or_preview_display=13
# ro.build.version.security_patch=2023-02-01
# ro.build.version.base_os=
# ro.build.version.security_index=1
# ro.build.version.min_supported_target_sdk=23
# ro.build.date=Mon Feb  6 20:14:50 +07 2023
# ro.build.date.utc=1675689290
# ro.build.type=user
# ro.build.user=dpi
# ro.build.host=VPDJR1010
# ro.build.tags=release-keys
# ro.build.flavor=gts7xlxx-user
# ro.build.system_root_image=false
# # ro.product.cpu.abi and ro.product.cpu.abi2 are obsolete,
# # use ro.product.cpu.abilist instead.
# ro.product.cpu.abi=arm64-v8a
# ro.product.locale=en-GB
# ro.wifi.channels=
# # ro.build.product is obsolete; use ro.product.device
# # ro.product.name/ro.product.device should use PRODUCT_MODEL in case of jpn model
# ro.build.product=qssi
# # Do not try to parse description or thumbprint
# ro.build.description=gts7xlxx-user 13 TP1A.220624.014 T975XXU2DWB2 release-keys
# ro.build.bsp=
# # Samsung Specific Properties
# ro.build.PDA=T975XXU2DWB2
# ro.build.changelist=25884018
# ro.product_ship=true
# ro.build.official.release=true
# ro.build.official.developer=false
# ro.build.2ndbrand=false
# ro.config.rm_preload_enabled=1
# ro.build.characteristics=tablet
# # end build properties
# ####################################
# # from device/qcom/qssi/system.prop
# ####################################
# #
# # system.prop for qssi
# #
# rild.libpath=/vendor/lib64/libril-qc-hal-qmi.so
# #rild.libargs=-d /dev/smd0
# persist.rild.nitz_plmn=
# persist.rild.nitz_long_ons_0=
# persist.rild.nitz_long_ons_1=
# persist.rild.nitz_long_ons_2=
# persist.rild.nitz_long_ons_3=
# persist.rild.nitz_short_ons_0=
# persist.rild.nitz_short_ons_1=
# persist.rild.nitz_short_ons_2=
# persist.rild.nitz_short_ons_3=
# ril.subscription.types=NV,RUIM
# DEVICE_PROVISIONED=1
# # Set network mode to (NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA, NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA) for 8+8 mode device on DSDS mode
# #ro.telephony.default_network=33,33
# dalvik.vm.heapsize=36m
# dalvik.vm.dex2oat64.enabled=true
# dev.pm.dyn_samplingrate=1
# #ro.hdmi.enable=true
# #persist.speaker.prot.enable=false
# qcom.hw.aac.encoder=true
# #
# # system props for the cne module
# #
# persist.vendor.cne.feature=1
# #system props for the MM modules
# media.stagefright.enable-player=true
# media.stagefright.enable-http=true
# media.stagefright.enable-aac=true
# media.stagefright.enable-qcp=true
# media.stagefright.enable-fma2dp=true
# media.stagefright.enable-scan=true
# media.stagefright.thumbnail.prefer_hw_codecs=true
# mmp.enable.3g2=true
# media.aac_51_output_enabled=true
# #media.settings.xml=/vendor/etc/media_profiles_vendor.xml
# #16777215 is decimal sum of supported codecs in AAL
# #codecs:(PARSER_)AAC AC3 AMR_NB AMR_WB ASF AVI DTS FLV 3GP 3G2 MKV MP2PS MP2TS MP3 OGG QCP WAV FLAC AIFF APE DSD MOV MHAS
# vendor.mm.enable.qcom_parser=16777215
# persist.mm.enable.prefetch=true
# #
# # system props for the data modules
# #
# # Removed by post_process_props.py because ro.vendor.use_data_netmgrd is a disallowed key
# #ro.vendor.use_data_netmgrd=true
# persist.vendor.data.mode=concurrent
# #system props for time-services
# persist.timed.enable=true
# #
# # system prop for opengles version
# #
# # 196608 is decimal for 0x30000 to report version 3
# # 196609 is decimal for 0x30001 to report version 3.1
# # 196610 is decimal for 0x30002 to report version 3.2
# ro.opengles.version=196610
# #
# # System props for telephony
# # System prop to turn on CdmaLTEPhone always
# # Removed by post_process_props.py because telephony.lteOnCdmaDevice is a disallowed key
# #telephony.lteOnCdmaDevice=1
# #Simulate sdcard on /data/media
# #
# persist.fuse_sdcard=true
# #System props for BT
# ro.bluetooth.library_name=libbluetooth_qti.so
# persist.vendor.btstack.aac_frm_ctl.enabled=true
# #system prop for RmNet Data
# persist.rmnet.data.enable=true
# persist.data.wda.enable=true
# persist.data.df.dl_mode=5
# persist.data.df.ul_mode=5
# persist.data.df.agg.dl_pkt=10
# persist.data.df.agg.dl_size=4096
# persist.data.df.mux_count=8
# persist.data.df.iwlan_mux=9
# persist.data.df.dev_name=rmnet_usb0
# #property to enable user to access Google WFD settings
# persist.debug.wfd.enable=1
# ##property to choose between virtual/external wfd display
# persist.sys.wfd.virtual=0
# #property to enable HWC for VDS
# debug.sf.enable_hwc_vds=1
# #property to latch unsignaled buffer
# debug.sf.latch_unsignaled=1
# # enable tunnel encoding for amrwb
# tunnel.audio.encode=true
# #enable voice path for PCM VoIP by default
# use.voice.path.for.pcm.voip=true
# # system prop for NFC DT
# ro.nfc.port=I2C
# #initialize QCA1530 detection
# sys.qca1530=detect
# #Enable stm events
# persist.debug.coresight.config=stm-events
# #hwui properties
# ro.hwui.texture_cache_size=72
# ro.hwui.layer_cache_size=48
# ro.hwui.r_buffer_cache_size=8
# ro.hwui.path_cache_size=32
# ro.hwui.gradient_cache_size=1
# ro.hwui.drop_shadow_cache_size=6
# ro.hwui.texture_cache_flushrate=0.4
# ro.hwui.text_small_cache_width=1024
# ro.hwui.text_small_cache_height=1024
# ro.hwui.text_large_cache_width=2048
# ro.hwui.text_large_cache_height=1024
# debug.hwui.skia_atrace_enabled=false
# config.disable_rtt=true
# #Bringup properties
# persist.sys.force_sw_gles=1
# persist.vendor.radio.atfwd.start=true
# ro.kernel.qemu.gles=0
# qemu.hw.mainkeys=0
# #Expose aux camera for below packages
# vendor.camera.aux.packagelist=org.codeaurora.snapcam
# #Add snapcam in privapp list
# persist.vendor.camera.privapp.list=org.codeaurora.snapcam
# #enable IZat OptInApp overlay
# persist.vendor.overlay.izat.optin=rro
# # Property for backup NTP Server
# persist.backup.ntpServer="0.pool.ntp.org"
# #Property to enable Mag filter
# persist.vendor.sensors.enable.mag_filter=true
# #Partition source order for Product/Build properties pickup.
# ro.product.property_source_order=odm,vendor,product,system_ext,system
# #Property to enable Codec2 for audio and OMX for Video
# debug.stagefright.ccodec=1
# #Property to set native recorder's maximum base layer fps
# ro.media.recorder-max-base-layer-fps=60
# #Battery Property
# ro.charger.enable_suspend=1
# #Disable MTE Async for system server
# arm64.memtag.process.system_server=off
# # Disable blur on app launch
# ro.launcher.blur.appLaunch=0
# ####################################
# # from variable ADDITIONAL_SYSTEM_PROPERTIES
# ####################################
# ro.treble.enabled=true
# ro.actionable_compatible_property.enabled=true
# ro.postinstall.fstab.prefix=/system
# ro.config.knox=v30
# ro.build.version.sem=3301
# ro.build.version.sep=140100
# ro.build.version.oneui=50100
# ro.system.build.version.sehi=3303
# persist.log.semlevel=0xFFFFFF00
# persist.log.level=0xFFFFFFFF
# ro.build.selinux=1
# ro.config.iccc_version=3.0
# ro.config.dmverity=G
# ro.oem_unlock_supported=1
# ro.kernel.qemu=0
# ro.secure=1
# security.perf_harden=1
# ro.adb.secure=1
# ro.allow.mock.location=0
# ro.binary.type=user
# ro.debuggable=0
# dalvik.vm.lockprof.threshold=500
# net.bt.name=Android
# ro.vendor.qti.va_aosp.support=1
# ####################################
# # from variable PRODUCT_SYSTEM_PROPERTIES
# ####################################
# log.tag.ConnectivityManager=D
# log.tag.ConnectivityService=D
# log.tag.NetworkLogger=D
# log.tag.IptablesRestoreController=D
# log.tag.ClatdController=D
# ro.telephony.iwlan_operation_mode=legacy
# debug.atrace.tags.enableflags=0
# persist.traced.enable=1
# dalvik.vm.image-dex2oat-Xms=64m
# dalvik.vm.image-dex2oat-Xmx=64m
# dalvik.vm.dex2oat-Xms=64m
# dalvik.vm.dex2oat-Xmx=512m
# dalvik.vm.finalizer-timeout-ms=60000
# dalvik.vm.usejit=true
# dalvik.vm.usejitprofiles=true
# dalvik.vm.dexopt.secondary=true
# dalvik.vm.dexopt.thermal-cutoff=2
# dalvik.vm.appimageformat=lz4
# ro.dalvik.vm.native.bridge=0
# pm.dexopt.first-boot=verify
# pm.dexopt.boot-after-ota=verify
# pm.dexopt.post-boot=extract
# pm.dexopt.install=speed-profile
# pm.dexopt.install-fast=skip
# pm.dexopt.install-bulk=speed-profile
# pm.dexopt.install-bulk-secondary=verify
# pm.dexopt.install-bulk-downgraded=verify
# pm.dexopt.install-bulk-secondary-downgraded=extract
# pm.dexopt.bg-dexopt=speed-profile
# pm.dexopt.ab-ota=speed-profile
# pm.dexopt.inactive=verify
# pm.dexopt.cmdline=verify
# pm.dexopt.shared=speed
# dalvik.vm.dex2oat-resolve-startup-strings=true
# dalvik.vm.dex2oat-max-image-block-size=524288
# dalvik.vm.minidebuginfo=true
# dalvik.vm.dex2oat-minidebuginfo=true
# dalvik.vm.madvise.vdexfile.size=104857600
# dalvik.vm.madvise.odexfile.size=104857600
# dalvik.vm.madvise.artfile.size=4294967295
# ro.camerax.extensions.enabled=true
# sys.hqm.support=true
# ro.security.fips.ux=Enabled
# ro.security.fips_bssl.ver=1.7
# security.mdf.result=None
# security.mdf=None
# ro.security.mdf.ux=Enabled
# ro.security.mdf.ver=3.2
# ro.security.mdf.release=1
# ro.security.wlan.ver=1.0
# ro.security.wlan.release=2
# ro.security.bt.ver=1.0
# ro.security.bt.release=1
# persist.device_config.runtime_native_boot.iorap_perfetto_enable=true
# ro.audio.spatializer_enabled=true
# ro.audio.spatializer_enabled=true
# ro.netflix.bsp_rev=Q8250-19134-1
# ####################################
# # from variable PRODUCT_SYSTEM_DEFAULT_PROPERTIES
# ####################################
# com.samsung.speg.prelauncher.disable=true
# # Auto-added by post_process_props.py
# persist.sys.usb.config=mtp
# # end of file
# """
# a = a.split("\n")
path = "fetchFromGitlab/"
files = os.listdir(path)
for fIndex in range(len(files)):
    files[fIndex] = path + files[fIndex]
path = "fetchFromGitlab2/"
files2 = os.listdir(path)
for fIndex in range(len(files2)):
    files2[fIndex] = path + files2[fIndex]
files.extend(files2)
saveAs = open("preExecuted", mode="w+")
saveAs.write("[\n")
for f in files:
    phoneTemplate = [
        "0unset",  # 0 # ro.product.model / ro.product.system.model
        "1unset",  # ro.build.version.sdk
        "2unset",  # ro.build.version.release
        "3unset",  # ro.product.system.manufacturer
        "4unset",  # ro.product.system.brand
        "5unset",  # 5 #ro.build.fingerprint
        "6unset",  # ro.build.id
        "7unset",  # 7 #ro.build.flavor
        "8unset",  # ro.build.product,useless here
        "unset",  # "9unset",  # ro.soc.manufacturer
        "unset",  # 10 # gsm.version.ril-impl
        "unset",  # ro.soc.model
        "CookedPotato",  # 12 # ro.modversion
        {
            "ro.build.version.incremental": "V13.0.2.1.37.DEV",
            "ro.build.description": "aeon6580_weg_l_l300-user 5.1 LMY47I 1555741941 release-keys",
        },
        "14unset",  # 14 #ro.system.build.date
        "15unset",  # ro.system.build.date.utc
        "16unset",  # ro.build.version.security_patch
        "release-keys",
        "user",
        "19unset",  # 19
    ]
    continueFlag = False
    if f.endswith(".py") or ("unknown" in f):
        continue
    b = open(f, "r")
    a = b.read()
    b.close()
    if (
            "lineage" in a
            or "missi system image for arm64" in a
            or "missi_phone_global" in a
            or "windows" in a
            or "Winmax" in a
            or "Windows" in a
            or "oplus_mssi_64_cn" in a
            or "smartwatch" in a
    ):
        continue

    a = a.split("\n")
    for s in a:
        if s == "":
            continue
        if "#" in s:
            continue
        s = s.split("=")
        if len(s) < 2:
            continue
        if s[1] == '':
            continue
        if ("test-keys" in s[1]
                or "evolution_" in s[1]
                or "aosp" in s[1]
                or " TV " in s[1]
                or "custom" in s[1]):
            continueFlag = True
            break
        if s[0] in product_string_field_list:
            phoneTemplate[0] = s[1]
        elif s[0] in sdkver_field_list:
            phoneTemplate[1] = s[1]
        elif s[0] in codename_field_list:
            phoneTemplate[2] = s[1]
        elif s[0] in manufacturer_field_list:
            phoneTemplate[3] = s[1]
        elif s[0] in brand_field_list:
            phoneTemplate[4] = s[1]
        elif s[0] in fingerprint_field_list:
            phoneTemplate[5] = s[1]
        elif s[0] in build_id_list:
            phoneTemplate[6] = s[1]
        elif "flavor" in s[0]:
            phoneTemplate[7] = s[1]
        # elif s[0] in brand_field_list:
        #     phoneTemplate[8] = s[1]
        elif "ro.soc.manufacturer" in s[0]:
            phoneTemplate[9] = s[1]
        elif "gsm.version.ril-impl" in s[0]:
            phoneTemplate[10] = s[1]
        elif "ro.soc.model" in s[0]:
            phoneTemplate[9] = s[1]
        elif "modversion" in s[0]:
            phoneTemplate[12] = s[1]
            continueFlag = True
            break
        elif (
                s[0].startswith("media.")
                or s[0].startswith('gsm.')
                or s[0] in build_version_codename_list
                or s[0] in force_add_to_map_keys
                or "net.hostname" == s[0]
                or "ro.boot.bootloader" == s[0]
                or "ro.build.characteristics" == s[0]
                or "ro.product.model.display" == s[0]
                or ".miui." in s[0]
                or ".miui_" in s[0]
                or ".fota." in s[0]
                or ".mi." in s[0]
                or ".oplus." in s[0]
                or ".mediatek." in s[0]
                or ".mtk_" in s[0]
                or ".acer." in s[0]
                or ".recovery_id" in s[0]
                or ".hct_" in s[0]
                or ".vivo." in s[0]
                or ".fota." in s[0]
                or ".hwui." in s[0]
                or ".nubia." in s[0]
                or ".lmk." in s[0]
                or ".meizu." in s[0]
                or ".flyme." in s[0]
                or ".google." in s[0]
                or ".freeme" in s[0]
        ):
            phoneTemplate[13][s[0]] = s[1]
        elif "ro.build.product" == s[0]:
            phoneTemplate[13]["ro.build.product.backup"] = s[1]
        elif s[0] in build_date_list:
            phoneTemplate[14] = s[1]
        elif s[0] in build_date_stamp_list:
            phoneTemplate[15] = s[1]
        elif s[0] == "ro.build.version.security_patch":
            phoneTemplate[16] = s[1]
        elif s[0] in build_tags_list:
            phoneTemplate[17] = s[1]
        elif s[0] in build_type_list:
            phoneTemplate[18] = s[1]
        elif s[0] in bootimage_name_list:
            phoneTemplate[19] = s[1]
        elif s[0] in device_field_list:
            phoneTemplate[13]["ro.build.product.backup"] = s[1]
    if continueFlag:
        continue
    if phoneTemplate[9] == "unset":
        phoneTemplate[13]["ro.soc.manufacturer"] = ""
    if phoneTemplate[10] == "unset":
        phoneTemplate[13]["gsm.version.ril-impl"] = ""
    if phoneTemplate[11] == "unset":
        phoneTemplate[13]["ro.soc.model"] = ""
    if phoneTemplate[0] == "0unset":
        continue
    if phoneTemplate[5] == "5unset":
        continue
    if phoneTemplate[7] == "7unset":
        continue
    saveAs.write(str(phoneTemplate) + ",# auto generated from propFormat\n")
saveAs.write("]\n")
saveAs.close()
