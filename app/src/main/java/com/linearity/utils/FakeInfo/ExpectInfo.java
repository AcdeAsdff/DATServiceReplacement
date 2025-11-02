package com.linearity.utils.FakeInfo;

import android.util.Pair;

import com.linearity.utils.ExtendedRandom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpectInfo {
    public static final HashMap<Long,ExpectInfo> INSTANCES = new HashMap<>();

    public static ExpectInfo getInstance(long randomSeed){
        if (INSTANCES.containsKey(randomSeed) && INSTANCES.get(randomSeed) != null){
            return INSTANCES.get(randomSeed);
        }
        ExpectInfo newResult = new ExpectInfo(new ExtendedRandom(randomSeed));
        INSTANCES.put(randomSeed,newResult);
        return newResult;
    }
    public static final Pair<Pattern,Object>[] bannedRegexPatterns = new Pair[]{
            new Pair<>(Pattern.compile("[0-9a-zA-Z]{16}"),null),
    };
    public boolean isBannedKey(String key){
        for (Pair<Pattern,Object> p:bannedRegexPatterns){
            Matcher matcher = p.first.matcher(key);
            if (matcher.matches()
                    && matcher.start() == 0
                    && matcher.end() == (key.length()-1)
            ){
                return true;
            }
        }
        return false;
    }
    public Object bannedValue(String key){
        for (Pair<Pattern,Object> p:bannedRegexPatterns){
            Matcher matcher = p.first.matcher(key);
            if (matcher.matches()
                    && matcher.start() == 0
                    && matcher.end() == (key.length()-1)
            ){
                return p.first;
            }
        }
        return null;
    }
    public boolean hasElement(String key){
        return isBannedKey(key)
                || nopSet.contains(key)
                || constMap.containsKey(key)
                || kvMap.containsKey(key);
    }
    public Object getElement(String key){
        if (isBannedKey(key)){
            return bannedValue(key);
        }
        if (nopSet.contains(key)){
            return null;
        }
        if (constMap.containsKey(key)){
            return constMap.get(key);
        }
        return kvMap.get(key);
    }
    public final Map<String, Object> kvMap = new HashMap<>();
    public static final Set<String> nopSet = new HashSet<>();

    static {
        nopSet.add("activity_manager_constants");
        nopSet.add("doze_enabled");
        nopSet.add("backup_enabled");
        nopSet.add("stylus_handwriting_enabled");
        nopSet.add("bluetooth_voip_state");
        nopSet.add("selected_search_engine");
        nopSet.add("89884a87498ef44f");
        nopSet.add("font_weight_adjustment");
        nopSet.add("com.q.zi.i");
        nopSet.add("bd_setting_i");
        nopSet.add("com.baidu.deviceid");
        nopSet.add("com.baidu.deviceid.v2");
        nopSet.add("DED0AFDB1AD0CC4CA974D5EBA0165141");
        nopSet.add("accessibility_captioning_font_scale");
        nopSet.add("accessibility_captioning_preset");
        nopSet.add("simple_mode_enabled");
        nopSet.add("new_simple_mode");
        nopSet.add("bluetooth_address");
        nopSet.add("7934039a7252be16");
        nopSet.add("amap_device_id");
        nopSet.add("hicar_running_status");
        nopSet.add("stylus_pointer_icon_enabled");
        nopSet.add("debug_app");
        nopSet.add("allow_user_switching_when_system_user_locked");
        nopSet.add("one_handed_mode_timeout");
        nopSet.add("taps_app_to_exit");
        nopSet.add("accessibility_button_targets");
        nopSet.add("accessibility_shortcut_target_service");
        nopSet.add("always_on_display_constants");
        nopSet.add("systemui/buffer/BroadcastDispatcherLog");
        nopSet.add("systemui/tag/BroadcastDispatcherLog");
        nopSet.add("doze_always_on");
        nopSet.add("accessibility_display_inversion_enabled");
        nopSet.add("qs_media_resumption");
        nopSet.add("qs_media_controls");
        nopSet.add("qs_media_recommend");
        nopSet.add("systemui/flags/101");
        nopSet.add("lockscreenSimpleUserSwitcher");
        nopSet.add("render_shadows_in_compositor");
        nopSet.add("sysui_volume_down_silent");
        nopSet.add("sysui_volume_up_silent");
        nopSet.add("roaming_settings");
        nopSet.add("rtt_mode");
        nopSet.add("qti.settings.auto_reject0");
        nopSet.add("pdp_watchdog_poll_interval_ms");
        nopSet.add("data_stall_recovery_on_bad_network");
        nopSet.add("captive_portal_mode");
        nopSet.add("captive_portal_https_url");
        nopSet.add("captive_portal_http_url");
        nopSet.add("captive_portal_fallback_url");
        nopSet.add("telecom.stale_session_cleanup_timeout_millis");
        nopSet.add("volte_vt_enabled");
        nopSet.add("vt_ims_enabled");
        nopSet.add("wfc_ims_enabled");
        nopSet.add("wfc_ims_mode");
        nopSet.add("wfc_ims_roaming_mode");
        nopSet.add("wfc_ims_roaming_enabled");
        nopSet.add("status_bar_show_vibrate_icon");
        nopSet.add("telecom.call_recording_tone_repeat_interval");
        nopSet.add("sms_outgoing_check_max_count");
        nopSet.add("systemui/buffer/CollapsedSbFragmentLog");
        nopSet.add("sms_outgoing_check_interval_ms");
        nopSet.add("systemui/tag/CollapsedSbFragment");
        nopSet.add("sms_short_code_confirmation");
        nopSet.add("preferred_tty_mode");
        nopSet.add("systemui/buffer/QSLog");
        nopSet.add("systemui/tag/QSLog");
        nopSet.add("sms_short_code_rule");
        nopSet.add("controls_enabled");
        nopSet.add("systemui/flags/300");
        nopSet.add("sysui_pm_lite");
        nopSet.add("qs_less_rows");
        nopSet.add("qs_show_brightness");
        nopSet.add("systemui/buffer/PrivacyLog");
        nopSet.add("systemui/tag/PrivacyLog");
        nopSet.add("sysui_qs_fancy_anim");
        nopSet.add("sysui_qs_move_whole_rows");
        nopSet.add("systemui/buffer/QSFragmentDisableFlagsLog");
        nopSet.add("systemui/tag/QSFragmentDisableFlagsLog");
        nopSet.add("systemui/tag/NotCurrentUserFilter");
        nopSet.add("systemui/tag/ShadeListBuilder");
        nopSet.add("systemui/flags/202");
        nopSet.add("systemui/flags/401");
        nopSet.add("enable_fullscreen_user_switcher");
        nopSet.add("use_blast_adapter_sv");
        nopSet.add("show_password");
        nopSet.add("force_emergency_affordance");
        nopSet.add("emergency_affordance_needed");
        nopSet.add("binder_calls_stats");
        nopSet.add("systemui/tag/NotifCollection");
        nopSet.add("systemui/tag/RowContentBindStage");
        nopSet.add("show_notification_snooze");
        nopSet.add("volume_key_cursor_control");
        nopSet.add("high_text_contrast_enabled");
        nopSet.add("gb_boosting");
        nopSet.add("quick_reply");
        nopSet.add("backup_local_transport_parameters");
        nopSet.add("enabled_accessibility_services");
        nopSet.add("download_manager_max_bytes_over_mobile");
        nopSet.add("download_manager_recommended_max_bytes_over_mobile");
        nopSet.add("google_play_store_system_component_update");
        nopSet.add("selected_search_engine_aga");
        nopSet.add("google_ota_automatic_download");
        nopSet.add("location_access_check_interval_millis");
        nopSet.add("locationPackagePrefixBlacklist");
        nopSet.add("locationPackagePrefixWhitelist");
        nopSet.add("instant_apps_enabled");
        nopSet.add("migrate_backup_enabled");
        nopSet.add("migrate_full_data_aware_original");
        nopSet.add("sysui_do_not_disturb");
        nopSet.add("heads_up_snooze_length_ms");
        nopSet.add("icon_blacklist");
        nopSet.add("device_demo_mode");
        nopSet.add("settings_provider_model");
        nopSet.add("systemui/flags/601");
        nopSet.add("systemui/buffer/DozeLog");
        nopSet.add("systemui/tag/DozeLog");
        nopSet.add("show_media_when_bypassing");
        nopSet.add("back_gesture_inset_scale_left");
        nopSet.add("back_gesture_inset_scale_right");
        nopSet.add("systemui/flags/203");
        nopSet.add("systemui/flags/700");
        nopSet.add("systemui/flags/701");
        nopSet.add("nfc_payment_default_component");
        nopSet.add("systemui/flags/501");
        nopSet.add("notification_history_enabled");
        nopSet.add("high_priority");
        nopSet.add("controls_providers");
        nopSet.add("double_tap_to_wake");
        nopSet.add("doze_pulse_on_double_tap");
        nopSet.add("doze_tap_gesture");
        nopSet.add("sqlite_compatibility_wal_flags");
        nopSet.add("assist_long_press_home_enabled");
        nopSet.add("assist_touch_gesture_enabled");
        nopSet.add("tether_force_usb_functions");
        nopSet.add("systemui/buffer/NotifLog");
        nopSet.add("systemui/tag/NotifBindPipeline");
        nopSet.add("systemui/flags/100");
        nopSet.add("systemui/flags/402");
        nopSet.add("lock_screen_show_silent_notifications");
        nopSet.add("accelerometer_rotation_angles");
        nopSet.add("show_temperature_warning");
        nopSet.add("show_usb_temperature_alarm");
        nopSet.add("sysui_force_enable_leak_reporting");
        nopSet.add("systemui/flags/800");
        nopSet.add("systemui/tag/NotificationEntryMgr");
        nopSet.add("systemui/buffer/NotifSectionLog");
        nopSet.add("systemui/tag/NotifSections");
        nopSet.add("sysui_rounded_size");
        nopSet.add("sysui_nav_bar_inverse");
        nopSet.add("sysui_keyguard_right");
        nopSet.add("sysui_keyguard_left");
        nopSet.add("clock_seconds");
        nopSet.add("lock_screen_custom_clock_face");
        nopSet.add("lockscreen_use_double_line_clock");
        nopSet.add("keyguard_slice_uri");
        nopSet.add("tether_supported");
        nopSet.add("enable_radio_bug_detection");
        nopSet.add("radio_bug_wakelock_timeout_count_threshold");
        nopSet.add("radio_bug_system_error_count_threshold");
        nopSet.add("settings_support_large_screen");
        nopSet.add("show_angle_in_use_dialog_box");
        nopSet.add("ims_profile_override");
        nopSet.add("lock_screen_lock_after_timeout");
        nopSet.add("nr_nsa_tracking_screen_off_mode");
        nopSet.add("systemui/flags/201");
        nopSet.add("doze_quick_pickup_gesture");
        nopSet.add("pdp_watchdog_long_poll_interval_ms");
        nopSet.add("contacts_database_wal_enabled");
        nopSet.add("new_contact_aggregator");
        nopSet.add("nfc_payment_foreground");
        nopSet.add("accessibility_captioning_locale");
        nopSet.add("accessibility_captioning_enabled");
        nopSet.add("POWER_SAVE_MODE_OPEN");
        nopSet.add("screenshot.scroll_capture_delay");
    }

    public static final Map<String,Object> constMap = new HashMap<>();
    static {

        constMap.put("package_verifier_user_consent","1");
        constMap.put("mock_location","0");
        constMap.put("rtt_calling_mode","0");
        constMap.put("airplane_mode_on","0");
        constMap.put("device_provisioned","1");
        constMap.put("odi_captions_enabled","1");
        constMap.put("zen_mode","0");
        constMap.put("user_setup_complete","1");
        constMap.put("add_users_when_locked","0");
        constMap.put("data_roaming5","0");
        constMap.put("radio.data.stall.recovery.action","0");
        constMap.put("notification_badging","1");
        constMap.put("tether_offload_disabled","1");
        constMap.put("voice_interaction_service","");
        constMap.put("accessibility_display_magnification_enabled","0");
        constMap.put("_boot_Phenotype_flags","");
        constMap.put("Phenotype_flags","");
        constMap.put("enabled_notification_listeners","");
        constMap.put("assistant","");
        constMap.put("lock_screen_show_notifications","1");
        constMap.put("user_switcher_enabled","0");
        constMap.put("force_resizable_activities","0");
        constMap.put("sysui_qs_tiles","internet,bt,flashlight,dnd,alarm,airplane,controls,rotation,battery,cast,screenrecord,mictoggle,cameratoggle,dark,saver,hotspot,work");
        constMap.put("qs_auto_tiles","cast,work,saver,hotspot");
        constMap.put("development_settings_enabled","0");
        constMap.put("cell_on","1");
        constMap.put("default_input_method","com.sohu.inputmethod.sogou/com.sohu.inputmethod.sogou.SogouIME");
        constMap.put("system_locales","zh-CN");
        constMap.put("speak_password","1");
        constMap.put("adb_wifi_enabled","0");
        constMap.put("adb_enabled","0");
        constMap.put("peak_refresh_rate","0");
        constMap.put("verifier_verify_adb_installs","1");
        constMap.put("accessibility_enabled","0");
    }
    public ExpectInfo(ExtendedRandom random){
        kvMap.put("screen_brightness", String.valueOf(random.nextInt(100)));
        kvMap.put("time_12_24", random.nextBoolean()?"24":"12");
        kvMap.put("haptic_feedback_enabled", random.next0or1());
        kvMap.put("sound_effects_enabled", random.next0or1());
        kvMap.put("screen_brightness_mode", random.next0or1());
        kvMap.put("enable_freeform_support", random.next0or1());
        kvMap.put("one_handed_mode_enabled", random.next0or1());
        kvMap.put("swipe_bottom_to_notification_enabled", random.next0or1());
        kvMap.put("battery_estimates_last_update_time",String.valueOf(Math.min(Math.max(random.nextLong(),1717751189 + random.nextInt(1000000)),System.currentTimeMillis() - 10000)));
        kvMap.put("time_remaining_estimate_millis",random.nextBoolean()?"-1":String.valueOf(random.nextLong()));
        kvMap.put("animator_duration_scale",String.valueOf(random.nextSmallDouble(1.1) + 0.01));
        kvMap.put("transition_animation_scale",String.valueOf(random.nextSmallDouble(1.1) + 0.01));
        kvMap.put("sysui_tuner_demo_on", random.next0or1());
        kvMap.put("sysui_demo_allowed", random.next0or1());
        kvMap.put("sysui_tuner_version",String.valueOf(random.nextInt(5)));
        kvMap.put("low_battery_sound",random.nextPackageName("/") + ".ogg");
        kvMap.put("lock_sound",random.nextPackageName("/") + ".ogg");
        kvMap.put("unlock_sound",random.nextPackageName("/") + ".ogg");
        kvMap.put("trusted_sound",random.nextPackageName("/") + ".ogg");
        kvMap.put("wifi_on",random.next0or1());
        kvMap.put("carrier_app_whitelist",random.nextRandomHexUpper(64)+":com.google.android.apps.tycho");
        kvMap.put("lockscreen_sounds_enabled",random.next0or1());
        kvMap.put("multi_cb",String.valueOf(random.nextInt(3)));
        kvMap.put("android_id",random.nextRandomHexLower(16));
        kvMap.put("show_ime_with_hard_keyboard",random.next0or1());
        kvMap.put("font_scale",String.valueOf(random.nextSmallDouble(1.1)));
        kvMap.put("ringtone_set",random.next0or1());
        kvMap.put("notification_sound_set",random.next0or1());
        kvMap.put("alarm_alert_set",random.next0or1());
        kvMap.put("location_mode",String.valueOf(random.nextInt(4)));
        kvMap.put("device_name",random.nextString(random.nextInt(10)+5));
        kvMap.put("low_power_trigger_level",random.nextInt(30) + 1);
        kvMap.put("low_power",random.next0or1());
        int tempInt = random.nextInt(574);
        kvMap.put("Phenotype_boot_count",String.valueOf(tempInt));
        kvMap.put("boot_count",String.valueOf(tempInt+1));
        kvMap.put("upload_apk_enable",random.next0or1());
        kvMap.put("ota_disable_automatic_update",random.next0or1());
        kvMap.put("heads_up_notifications_enabled",random.next0or1());
        kvMap.put("lockscreen_show_controls",random.next0or1());
        kvMap.put("accessibility_button_mode",String.valueOf(random.nextInt(3)));
        kvMap.put("accelerometer_rotation",String.valueOf(random.nextInt(3)));
        kvMap.put("hide_rotation_lock_toggle_for_accessibility",random.next0or1());
        kvMap.put("subscription_mode",random.next0or1());
        String temp = String.valueOf(random.nextInt(5)+1);
        kvMap.put("multi_sim_voice_call",temp);
        kvMap.put("multi_sim_sms",temp);
        kvMap.put("multi_sim_data_call",temp);
        
        kvMap.put("zen_duration","0");
        kvMap.put("low_power_warning_acknowledged",random.next0or1());
        kvMap.put("enable_cellular_on_boot",random.next0or1());
        kvMap.put("mobile_data",random.next0or1());
        kvMap.put("theme_customization_overlay_packages",
                "{\"android.theme.customization.color_both\":\"1\",\"android.theme.customization.color_source\":\"home_wallpaper\",\"_applied_timestamp\":"
                + Math.min(
                        Math.max(random.nextLong(), 1717751189 + random.nextInt(1000000)),
                        System.currentTimeMillis() - 10000)
                        +"}");
        
        kvMap.put("lock_screen_allow_private_notifications",random.next0or1());
        kvMap.put("hearing_aid",random.next0or1());
        kvMap.put("data_roaming",random.next0or1());
        kvMap.put("notification_bubbles",random.next0or1());
        kvMap.put("mobile_data5",random.next0or1());

        kvMap.put("screen_off_timeout",String.valueOf(
                Consts.SCREEN_OFF_TIMEOUT_ARR[random.nextInt(Consts.SCREEN_OFF_TIMEOUT_ARR.length)]
        ));
        kvMap.put("wifi_sleep_policy",String.valueOf(random.nextInt(3)));
        kvMap.put("alarm_alert",random.next0or1());
        kvMap.put("bluetooth_name",random.nextString(random.nextInt(10)+3));
        kvMap.put("ringtone",random.next0or1());
        kvMap.put("volume_alarm_speaker",String.valueOf(random.nextInt(255)));
        kvMap.put("volume_ring_speaker",String.valueOf(random.nextInt(255)));
        kvMap.put("volume_voice_earpiece",String.valueOf(random.nextInt(255)));
        kvMap.put("volume_music_speaker",String.valueOf(random.nextInt(255)));
        kvMap.put("require_password_to_decrypt",String.valueOf(random.nextInt(255)));
        kvMap.put("display_color_mode",String.valueOf(random.nextInt(255)));
        kvMap.put("average_time_to_discharge",String.valueOf(random.nextInt(255)));
        kvMap.put("volume_voice",random.next0or1());
        kvMap.put("volume_system",random.next0or1());
        kvMap.put("volume_ring",random.next0or1());
        kvMap.put("volume_music",random.next0or1());
        kvMap.put("volume_bluetooth_sco",random.next0or1());
        kvMap.put("volume_alarm",random.next0or1());
        kvMap.put("mobile_data_always_on",random.next0or1());
        kvMap.put("night_display_forced_auto_mode_available",random.next0or1());
        kvMap.put("wifi_connected_mac_randomization_enabled",random.next0or1());
        kvMap.put("default_customed_apk",random.next0or1());
        kvMap.put("vibrate_when_ringing",random.next0or1());
        kvMap.put("date_format",random.next0or1());
        kvMap.put("stay_on_while_plugged_in",random.next0or1());
        kvMap.put("mode_ringer",random.next0or1());
        kvMap.put("bluetooth_discoverability",random.next0or1());
        kvMap.put("auto_time_zone",random.next0or1());
        kvMap.put("auto_time",random.next0or1());
        kvMap.put("dark_mode_enable",random.next0or1());
        kvMap.put("next_alarm_formatted",random.next0or1());
        kvMap.put("wifi_scan_always_enabled",random.nextBoolean());
        kvMap.put("enabled_input_methods",random.nextBoolean());
        kvMap.put("ui_night_mode",random.nextBoolean());
        kvMap.put("auto_test_mode_on",false);
        kvMap.put("accountNickname",random.nextString(random.nextInt(8)+5));
        kvMap.put("bluetooth_on",String.valueOf(random.next0or1()));
        kvMap.put("navigation_mode",String.valueOf(random.next0or1()));
        kvMap.put("miui_terms_agreed_time",String.valueOf(1672545458L + random.nextInt(31536000*2)));

    }
    
}
