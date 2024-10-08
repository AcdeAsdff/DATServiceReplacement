import bs4

a = """<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
     package="com.android.bluetooth"
     android:sharedUserId="android.uid.bluetooth">
    <original-package android:name="com.android.bluetooth"/>
    <!-- Allows access to the Bluetooth Share Manager -->
    <permission android:name="android.permission.ACCESS_BLUETOOTH_SHARE"
         android:label="@string/permlab_bluetoothShareManager"
         android:description="@string/permdesc_bluetoothShareManager"
         android:protectionLevel="signature"/>
    <!--  Allows temporarily acceptlisting Bluetooth addresses for sharing -->
    <permission android:name="com.android.permission.ALLOWLIST_BLUETOOTH_DEVICE"
         android:label="@string/permlab_bluetoothAcceptlist"
         android:description="@string/permdesc_bluetoothAcceptlist"
         android:protectionLevel="signature"/>
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
    <uses-permission android:name="android.permission.ACCESS_BLUETOOTH_SHARE"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.BLUETOOTH"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE"/>
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT"/>
    <uses-permission android:name="android.permission.BLUETOOTH_SCAN"/>
    <uses-permission android:name="android.permission.BLUETOOTH_PRIVILEGED"/>
    <uses-permission android:name="android.permission.BLUETOOTH_MAP"/>
    <uses-permission android:name="android.permission.CONTROL_INCALL_EXPERIENCE" />
    <uses-permission android:name="android.permission.DUMP"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_CONTACTS"/>
    <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
    <uses-permission android:name="android.permission.READ_CALL_LOG"/>
    <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
    <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE"/>
    <uses-permission android:name="android.permission.LISTEN_ALWAYS_REPORTED_SIGNAL_STRENGTH"/>
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
    <uses-permission android:name="android.permission.NFC_HANDOVER_STATUS"/>
    <uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>
    <uses-permission android:name="android.permission.WRITE_APN_SETTINGS"/>
    <uses-permission android:name="android.permission.NET_ADMIN"/>
    <uses-permission android:name="android.permission.CALL_PRIVILEGED"/>
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
    <uses-permission android:name="android.permission.NET_TUNNELING"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CONNECTIVITY_INTERNAL"/>
    <uses-permission android:name="android.permission.READ_DEVICE_CONFIG"/>
    <uses-permission android:name="android.permission.NETWORK_FACTORY"/>
    <uses-permission android:name="android.permission.TETHER_PRIVILEGED"/>
    <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>
    <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL"/>
    <uses-permission android:name="android.permission.BLUETOOTH_STACK"/>
    <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS"/>
    <uses-permission android:name="android.permission.MANAGE_USERS"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    <uses-permission android:name="android.permission.RECEIVE_SMS"/>
    <uses-permission android:name="android.permission.SEND_SMS"/>
    <uses-permission android:name="android.permission.READ_SMS"/>
    <uses-permission android:name="android.permission.WRITE_SMS"/>
    <uses-permission android:name="android.permission.MEDIA_CONTENT_CONTROL"/>
    <uses-permission android:name="android.permission.UPDATE_APP_OPS_STATS"/>
    <uses-permission android:name="android.permission.MANAGE_APP_OPS_MODES"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.DEVICE_POWER"/>
    <uses-permission android:name="android.permission.REAL_GET_TASKS"/>
    <uses-permission android:name="android.permission.MODIFY_AUDIO_ROUTING"/>
    <uses-permission android:name="android.permission.UPDATE_DEVICE_STATS"/>
    <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>
    <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES"/>
    <uses-permission android:name="android.permission.MANAGE_COMPANION_DEVICES"/>
    <uses-permission android:name="android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND"/>
    <uses-permission android:name="android.permission.HIDE_OVERLAY_WINDOWS"/>
    <uses-sdk android:minSdkVersion="14"/>
    <!-- For PBAP Owner Vcard Info -->
    <uses-permission android:name="android.permission.READ_PROFILE"/>
    <application android:name=".btservice.AdapterApp"
         android:icon="@mipmap/bt_share"
         android:persistent="false"
         android:label="@string/app_name"
         android:supportsRtl="true"
         android:usesCleartextTraffic="false"
         android:directBootAware="true"
         android:defaultToDeviceProtectedStorage="true"
         android:memtagMode="async">
        <uses-library android:name="javax.obex"/>
        <provider android:name=".opp.BluetoothOppProvider"
             android:authorities="com.android.bluetooth.opp"
             android:exported="true"
             android:process="@string/process">
            <path-permission android:pathPrefix="/btopp"
                 android:permission="android.permission.ACCESS_BLUETOOTH_SHARE"/>
        </provider>
        <provider android:name=".opp.BluetoothOppFileProvider"
             android:authorities="com.android.bluetooth.opp.fileprovider"
             android:grantUriPermissions="true"
             android:exported="false">
            <meta-data android:name="android.support.FILE_PROVIDER_PATHS"
                 android:resource="@xml/file_paths"/>
        </provider>
        <service android:process="@string/process"
             android:name=".btservice.AdapterService"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetooth"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".opp.BluetoothOppService"
             android:permission="android.permission.ACCESS_BLUETOOTH_SHARE"
             android:enabled="@bool/profile_supported_opp"/>
        <receiver android:process="@string/process"
             android:exported="true"
             android:name=".opp.BluetoothOppReceiver"
             android:enabled="@bool/profile_supported_opp">
            <intent-filter>
                <action android:name="android.btopp.intent.action.OPEN_RECEIVED_FILES"/>
            </intent-filter>
        </receiver>
         <receiver android:process="@string/process"
              android:name=".opp.BluetoothOppHandoverReceiver"
              android:permission="com.android.permission.ALLOWLIST_BLUETOOTH_DEVICE"
              android:exported="true">
            <intent-filter>
                <action android:name="android.btopp.intent.action.ACCEPTLIST_DEVICE"/>
                <action android:name="android.btopp.intent.action.STOP_HANDOVER_TRANSFER"/>
            </intent-filter>
            <intent-filter>
                <action android:name="android.nfc.handover.intent.action.HANDOVER_SEND"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="*/*"/>
            </intent-filter>
            <intent-filter>
                <action android:name="android.nfc.handover.intent.action.HANDOVER_SEND_MULTIPLE"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="*/*"/>
            </intent-filter>
        </receiver>
        <activity android:name=".opp.BluetoothOppLauncherActivity"
             android:process="@string/process"
             android:theme="@android:style/Theme.Material.Light.Dialog"
             android:label="@string/bt_share_picker_label"
             android:enabled="@bool/profile_supported_opp"
             android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.SEND"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
                <data android:mimeType="video/*"/>
                <data android:mimeType="audio/*"/>
                <data android:mimeType="text/x-vcard"/>
                <data android:mimeType="text/x-vcalendar"/>
                <data android:mimeType="text/calendar"/>
                <data android:mimeType="text/plain"/>
                <data android:mimeType="text/html"/>
                <data android:mimeType="text/xml"/>
                <data android:mimeType="application/zip"/>
                <data android:mimeType="application/vnd.ms-excel"/>
                <data android:mimeType="application/msword"/>
                <data android:mimeType="application/vnd.ms-powerpoint"/>
                <data android:mimeType="application/pdf"/>
                <data android:mimeType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                <data android:mimeType="application/vnd.openxmlformats-officedocument.wordprocessingml.document"/>
                <data android:mimeType="application/vnd.openxmlformats-officedocument.presentationml.presentation"/>
                <data android:mimeType="application/x-hwp"/>
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.SEND_MULTIPLE"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
                <data android:mimeType="video/*"/>
                <data android:mimeType="x-mixmedia/*"/>
                <data android:mimeType="text/x-vcard"/>
            </intent-filter>
            <intent-filter>
                <action android:name="android.btopp.intent.action.OPEN"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="vnd.android.cursor.item/vnd.android.btopp"/>
            </intent-filter>
        </activity>
        <activity android:name=".opp.BluetoothOppBtEnableActivity"
             android:process="@string/process"
             android:excludeFromRecents="true"
             android:theme="@style/dialog"
             android:enabled="@bool/profile_supported_opp">
        </activity>
        <activity android:name=".opp.BluetoothOppBtErrorActivity"
             android:process="@string/process"
             android:excludeFromRecents="true"
             android:theme="@style/dialog">
        </activity>
        <activity android:name=".opp.BluetoothOppBtEnablingActivity"
             android:process="@string/process"
             android:excludeFromRecents="true"
             android:theme="@style/dialog"
             android:enabled="@bool/profile_supported_opp">
        </activity>
        <activity android:name=".opp.BluetoothOppIncomingFileConfirmActivity"
             android:process="@string/process"
             android:excludeFromRecents="true"
             android:theme="@style/dialog"
             android:enabled="@bool/profile_supported_opp">
        </activity>
        <activity android:name=".opp.BluetoothOppTransferActivity"
             android:process="@string/process"
             android:excludeFromRecents="true"
             android:theme="@style/dialog"
             android:enabled="@bool/profile_supported_opp">
        </activity>
        <activity android:name=".opp.BluetoothOppTransferHistory"
             android:process="@string/process"
             android:label=""
             android:excludeFromRecents="true"
             android:configChanges="orientation|keyboardHidden"
             android:enabled="@bool/profile_supported_opp"
             android:theme="@android:style/Theme.DeviceDefault.Settings"
             android:exported="true">
            <intent-filter>
                <action android:name="com.android.bluetooth.action.TransferHistory"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </activity>
        <activity android:name=".pbap.BluetoothPbapActivity"
             android:process="@string/process"
             android:excludeFromRecents="true"
             android:theme="@style/dialog"
             android:enabled="@bool/profile_supported_pbap">
        </activity>
        <service android:process="@string/process"
             android:permission="android.permission.BLUETOOTH_PRIVILEGED"
             android:name=".pbap.BluetoothPbapService"
             android:enabled="@bool/profile_supported_pbap"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothPbap"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".map.BluetoothMapService"
             android:enabled="@bool/profile_supported_map"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothMap"/>
                <action android:name="android.btmap.intent.action.SHOW_MAPS_SETTINGS"/>
                <action android:name="com.android.bluetooth.map.USER_CONFIRM_TIMEOUT"/>
            </intent-filter>
        </service>
         <activity android:name=".map.BluetoothMapSettings"
              android:process="@string/process"
              android:label="@string/bluetooth_map_settings_title"
              android:excludeFromRecents="true"
              android:configChanges="orientation|keyboardHidden"
              android:enabled="@bool/profile_supported_map">
        </activity>
        <provider android:name=".map.MmsFileProvider"
             android:authorities="com.android.bluetooth.map.MmsFileProvider"
             android:enabled="true"
             android:grantUriPermissions="true"
             android:exported="false">
        </provider>
        <service android:process="@string/process"
             android:name=".mapclient.MapClientService"
             android:enabled="@bool/profile_supported_mapmce"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothMapClient"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".sap.SapService"
             android:enabled="@bool/profile_supported_sap"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothSap"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".gatt.GattService"
             android:enabled="@bool/profile_supported_gatt"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothGatt"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".hfp.HeadsetService"
             android:enabled="@bool/profile_supported_hs_hfp"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothHeadset"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".a2dp.A2dpService"
             android:enabled="@bool/profile_supported_a2dp"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothA2dp"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".a2dpsink.A2dpSinkService"
             android:enabled="@bool/profile_supported_a2dp_sink"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothA2dpSink"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".avrcpcontroller.BluetoothMediaBrowserService"
             android:exported="true"
             android:enabled="@bool/profile_supported_a2dp_sink"
             android:label="@string/a2dp_sink_mbs_label">
            <intent-filter>
                <action android:name="android.media.browse.MediaBrowserService"/>
            </intent-filter>
        </service>
        <activity android:name=".BluetoothPrefs"
             android:exported="@bool/profile_supported_a2dp_sink"
             android:enabled="@bool/profile_supported_a2dp_sink">
            <intent-filter>
                <action android:name="android.intent.action.APPLICATION_PREFERENCES"/>
            </intent-filter>
        </activity>
        <service android:process="@string/process"
             android:name=".avrcp.AvrcpTargetService"
             android:enabled="@bool/profile_supported_avrcp_target"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothAvrcp"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".avrcpcontroller.AvrcpControllerService"
             android:enabled="@bool/profile_supported_avrcp_controller"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothAvrcpController"/>
            </intent-filter>
        </service>
        <provider android:process="@string/process"
             android:name=".avrcpcontroller.AvrcpCoverArtProvider"
             android:authorities="com.android.bluetooth.avrcpcontroller.AvrcpCoverArtProvider"
             android:enabled="@bool/avrcp_controller_enable_cover_art"
             android:grantUriPermissions="true"
             android:exported="true">
        </provider>
        <service android:process="@string/process"
             android:name=".hid.HidHostService"
             android:enabled="@bool/profile_supported_hid_host"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothHidHost"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".hid.HidDeviceService"
             android:enabled="@bool/profile_supported_hid_device"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothHidDevice"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".mcp.McpService"
             android:exported="true"
             android:enabled="@bool/profile_supported_mcp_server">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothMcpServiceManager" />
            </intent-filter>
        </service>
        <service
             android:process="@string/process"
             android:name=".pan.PanService"
             android:enabled="@bool/profile_supported_pan"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothPan"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".hfpclient.HeadsetClientService"
             android:enabled="@bool/profile_supported_hfpclient"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothHeadsetClient"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".hfpclient.connserv.HfpClientConnectionService"
             android:permission="android.permission.BIND_CONNECTION_SERVICE"
             android:enabled="@bool/hfp_client_connection_service_enabled"
             android:exported="true">
            <intent-filter>
                <!-- Mechanism for Telecom stack to connect -->
                <action android:name="android.telecom.ConnectionService"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".pbapclient.PbapClientService"
             android:enabled="@bool/profile_supported_pbapclient"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothPbapClient"/>
            </intent-filter>
        </service>
        <service android:process="@string/process"
             android:name=".hearingaid.HearingAidService"
             android:exported="true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothHearingAid"/>
            </intent-filter>
        </service>
        <service
            android:process="@string/process"
            android:name=".vc.VolumeControlService"
            android:exported="true"
            android:enabled="@bool/profile_supported_vc">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothVolumeControl" />
            </intent-filter>
        </service>
        <service
            android:process="@string/process"
            android:name = ".le_audio.LeAudioService"
            android:enabled="@bool/profile_supported_le_audio"
            android:exported = "true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothLeAudio" />
            </intent-filter>
        </service>
        <service
            android:process="@string/process"
            android:name = ".csip.CsipSetCoordinatorService"
            android:enabled="@bool/profile_supported_csip_set_coordinator"
            android:exported = "true">
            <intent-filter>
                <action android:name="android.bluetooth.IBluetoothCsipSetCoordinator" />
            </intent-filter>
        </service>
        <!-- Authenticator for PBAP account. -->
        <service android:process="@string/process"
             android:name=".pbapclient.AuthenticationService"
             android:exported="true"
             android:enabled="@bool/profile_supported_pbapclient">
            <intent-filter>
                <action android:name="android.accounts.AccountAuthenticator"/>
            </intent-filter>
            <meta-data android:name="android.accounts.AccountAuthenticator"
                 android:resource="@xml/authenticator"/>
        </service>
        <service
            android:name=".telephony.BluetoothInCallService"
            android:permission="android.permission.BIND_INCALL_SERVICE"
            android:process="@string/process"
            android:enabled="@bool/profile_supported_hfp_incallservice"
            android:exported="true">
            <meta-data android:name="android.telecom.INCLUDE_SELF_MANAGED_CALLS"
                       android:value="true" />
            <intent-filter>
              <action android:name="android.telecom.InCallService"/>
            </intent-filter>
         </service>
    </application>
</manifest>"""# https://android.googlesource.com/platform/packages/apps/Bluetooth/+/refs/heads/main/AndroidManifest.xml

soup = bs4.BeautifulSoup(a,"xml")
pkgName = soup.find("manifest")["package"]
outputMap = {}
for item in soup.find_all("service"):
    exactClassName = pkgName + item['android:name']
    interfaceName = item.find("action")
    if (interfaceName != None):
        interfaceName = interfaceName["android:name"]
        outputMap[interfaceName] = exactClassName
        print("""hookClass = XposedHelpers.findClassIfExists(\"""" + exactClassName + """\",lpparam.classLoader);
if (hookClass != null){
    hook""" + interfaceName.split('.')[-1] + """(hookClass);
}""")
    else:
        interfaceName = 'null'
        # print(interfaceName + " -> " + exactClassName)
