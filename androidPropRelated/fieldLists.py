product_string_field_list: set[str] = {
    "ro.product.bootimage.model", "ro.product.model", "ro.product.odm.model",
    "ro.product.odm_dlkm.model", "ro.product.product.model", "ro.product.system.model",
    "ro.product.system_ext.model", "ro.product.vendor.model", "ro.product.vendor_dlkm.model",
    "vendor.usb.product_string",
    "ro.product.marketname", "ro.product.vendor.marketname", "ro.product.odm_dlkm.marketname",
    "ro.product.product.marketname", "ro.product.system.marketname", "ro.product.vendor_dlkm.marketname",
    "ro.product.system_ext.marketname", "ro.product.odm.marketname",
    "bluetooth.device.default_name", 'ro.product.marketname',
}  # 0
sdkver_field_list: set[str] = {
    "ro.bootimage.build.version.sdk", "ro.build.version.sdk", "ro.odm.build.version.sdk",
    "ro.product.build.version.sdk", "ro.system.build.version.sdk", "ro.system_ext.build.version.sdk",
    "ro.vendor.build.version.sdk", "ro.vendor_dlkm.build.version.sdk", "ro.vndk.version",
}
codename_field_list: set[str] = {
    "ro.bootimage.build.version.release", "ro.bootimage.build.version.release_or_codename",
    "ro.build.version.release", "ro.build.version.release_or_codename",
    "ro.odm.build.version.release", "ro.odm.build.version.release_or_codename",
    "ro.product.build.version.release", "ro.product.build.version.release_or_codename",
    "ro.system.build.version.release", "ro.system.build.version.release_or_codename",
    "ro.system_ext.build.version.release", "ro.system_ext.build.version.release_or_codename",
    "ro.vendor.build.version.release", "ro.vendor.build.version.release_or_codename",
    "ro.vendor_dlkm.build.version.release", "ro.vendor_dlkm.build.version.release_or_codename",
}
manufacturer_field_list: set[str] = {
    "ro.product.bootimage.manufacturer", "ro.product.manufacturer",
    "ro.product.odm.manufacturer", "ro.product.product.manufacturer",
    "ro.product.system.manufacturer", "ro.product.system_ext.manufacturer",
    "ro.product.vendor.manufacturer", "ro.product.vendor_dlkm.manufacturer",
}  # 3
brand_field_list: set[str] = {
    "ro.product.bootimage.brand", "ro.product.brand", "ro.product.odm.brand",
    "ro.product.odm_dlkm.brand", "ro.product.product.brand", "ro.product.system.brand",
    "ro.product.system_ext.brand", "ro.product.vendor.brand", "ro.product.vendor_dlkm.brand",
}  # 4
fingerprint_field_list: set[str] = {
    "ro.bootimage.build.fingerprint", "ro.build.fingerprint", "ro.odm.build.fingerprint",
    "ro.odm_dlkm.build.fingerprint", "ro.product.build.fingerprint",
    "ro.system.build.fingerprint",
    "ro.system_ext.build.fingerprint", "ro.vendor.build.fingerprint",
    "ro.vendor_dlkm.build.fingerprint",
}  # 5
device_field_list: set[str] = {
    "ro.build.product", "ro.product.bootimage.device",'ro.hardware.audio.primary',
    "ro.product.device", "ro.product.odm.device", "ro.product.odm_dlkm.device",
    "ro.product.product.device", "ro.product.system.device", "ro.product.system_ext.device",
    "ro.product.vendor.device", "ro.product.vendor_dlkm.device", "ro.vendor.radio.midevice",
}
banned_field_list: set[str] = {
    'init.svc.vendor.lineage_health', 'init.svc_debug_pid.vendor.lineage_health', 'ro.boottime.vendor.lineage_health',
    'ro.com.google.clientidbase', 'ro.lineage.build.version', 'ro.lineage.build.version.plat.rev',
    'ro.lineage.build.version.plat.sdk', 'ro.lineage.device', 'ro.lineage.display.version',
    'ro.lineage.releasetype','ro.lineage.version', 'ro.lineagelegal.url',
    'ro.modversion',
    'persist.vendor.camera.frontMain.info','persist.vendor.camera.rearDepth.info','persist.vendor.camera.rearMacro.info',
    'persist.vendor.camera.rearMain.info','persist.vendor.camera.rearUltra.info',
}
incremental_list: set[str] = {
    "ro.bootimage.build.version.incremental",
    "ro.build.version.incremental",
    "ro.odm.build.version.incremental",
    "ro.product.build.version.incremental",
    "ro.system.build.version.incremental",
    "ro.system_ext.build.version.incremental",
    "ro.vendor.build.version.incremental",
    "ro.vendor_dlkm.build.version.incremental",
}  # 13
build_id_list: set[str] = {
    "ro.bootimage.build.id", "ro.build.id", "ro.odm.build.id",
    "ro.product.build.id", "ro.system.build.id", "ro.system_ext.build.id",
    "ro.vendor.build.id", "ro.vendor_dlkm.build.id",
}  # 6
build_date_list: set[str] = {
    "ro.bootimage.build.date", "ro.build.date", "ro.odm.build.date", "ro.product.build.date",
    "ro.system.build.date", "ro.system_ext.build.date", "ro.vendor.build.date", "ro.vendor_dlkm.build.date",
}  # 14
build_date_stamp_list: set[str] = {
    "ro.bootimage.build.date.utc", "ro.build.date.utc", "ro.odm.build.date.utc", "ro.product.build.date.utc",
    "ro.system.build.date.utc", "ro.system_ext.build.date.utc", "ro.vendor.build.date.utc",
    "ro.vendor_dlkm.build.date.utc",
}  # 15
build_tags_list: set[str] = {
    "ro.bootimage.build.tags", "ro.build.tags", "ro.odm.build.tags",
    "ro.product.build.tags", "ro.system.build.tags", "ro.system_ext.build.tags",
    "ro.vendor.build.tags", "ro.vendor_dlkm.build.tags",
}  # 17
build_type_list: set[str] = {
    "ro.bootimage.build.type", "ro.odm.build.type", "ro.product.build.type",
    "ro.system.build.type", "ro.system_ext.build.type", "ro.vendor.build.type", "ro.vendor_dlkm.build.type",
}  # 18
bootimage_name_list: set[str] = {
    "ro.product.bootimage.name", "ro.product.name", "ro.product.odm.name",
    "ro.product.product.name", "ro.product.system.name", "ro.product.system_ext.name",
    "ro.product.vendor.name", "ro.product.vendor_dlkm.name",
}  # 19
build_version_codename_list: set[str] = {
    "ro.build.version.all_codenames",
    "ro.build.version.codename",
    "ro.build.version.preview_sdk_fingerprint",
}
force_add_to_map_keys: set[str] = {
    "ro.build.user", "ro.build.host", "ro.build.description",
    "ro.build.version.incremental",
}
