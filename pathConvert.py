import re

a = """--------- beginning of main
       --------- beginning of kernel
       --------- beginning of system
       2024-11-17 16:04:35.786  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]package_native|com.android.server.pm.PackageManagerNative@e14e960
       2024-11-17 16:04:35.803  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]otadexopt|com.android.server.pm.OtaDexoptService@e3c9bb6
       2024-11-17 16:04:35.806  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]activity|com.android.server.am.ActivityManagerService@ef2892b
       2024-11-17 16:04:35.807  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]procstats|com.android.server.am.ProcessStatsService@58f7024
       2024-11-17 16:04:35.808  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]meminfo|com.android.server.am.ActivityManagerService$MemBinder@215e78d
       2024-11-17 16:04:35.809  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]gfxinfo|com.android.server.am.ActivityManagerService$GraphicsBinder@3eebd42
       2024-11-17 16:04:35.809  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]dbinfo|com.android.server.am.ActivityManagerService$DbBinder@6656a90
       2024-11-17 16:04:35.810  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]cpuinfo|com.android.server.am.AppProfiler$CpuBinder@4af6b8e
       2024-11-17 16:04:35.810  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]permission|com.android.server.am.ActivityManagerService$PermissionController@f4a3faf
       2024-11-17 16:04:35.810  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]processinfo|com.android.server.am.ActivityManagerService$ProcessInfoService@25e9fbc
       2024-11-17 16:04:35.812  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]cacheinfo|com.android.server.am.ActivityManagerService$CacheBinder@e5d7b45
       2024-11-17 16:04:36.001  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]sec_key_att_app_id_provider|com.android.server.security.KeyAttestationApplicationIdProviderService@4b9b4be
       2024-11-17 16:04:36.003  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]scheduling_policy|com.android.server.os.SchedulingPolicyService@4747b58
       2024-11-17 16:04:36.004  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]telephony.registry|com.android.server.TelephonyRegistry@13d84b1
       2024-11-17 16:04:36.358  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]external_vibrator_service|com.android.server.vibrator.VibratorManagerService$ExternalVibratorService@fcf624a
       2024-11-17 16:04:36.358  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]external_vibrator_service|com.android.server.vibrator.VibratorManagerService$ExternalVibratorService@fcf624a
       2024-11-17 16:04:36.358  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]external_vibrator_service|com.android.server.vibrator.VibratorManagerService$ExternalVibratorService@fcf624a
       2024-11-17 16:04:36.358  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]external_vibrator_service|com.android.server.vibrator.VibratorManagerService$ExternalVibratorService@fcf624a
       2024-11-17 16:04:36.359  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]android.frameworks.vibrator.IVibratorControlService/default|com.android.server.vibrator.VibratorControlService@a7b95bb
       2024-11-17 16:04:36.359  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]android.frameworks.vibrator.IVibratorControlService/default|com.android.server.vibrator.VibratorControlService@a7b95bb
       2024-11-17 16:04:36.359  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]android.frameworks.vibrator.IVibratorControlService/default|com.android.server.vibrator.VibratorControlService@a7b95bb
       2024-11-17 16:04:36.359  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]android.frameworks.vibrator.IVibratorControlService/default|com.android.server.vibrator.VibratorControlService@a7b95bb
       2024-11-17 16:04:36.360  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]dynamic_system|com.android.server.DynamicSystemService@7dfead8
       2024-11-17 16:04:36.360  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]dynamic_system|com.android.server.DynamicSystemService@7dfead8
       2024-11-17 16:04:36.360  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]dynamic_system|com.android.server.DynamicSystemService@7dfead8
       2024-11-17 16:04:36.360  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]dynamic_system|com.android.server.DynamicSystemService@7dfead8
       2024-11-17 16:04:36.361  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]consumer_ir|com.android.server.ConsumerIrService@ef4fe31
       2024-11-17 16:04:36.361  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]consumer_ir|com.android.server.ConsumerIrService@ef4fe31
       2024-11-17 16:04:36.361  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]consumer_ir|com.android.server.ConsumerIrService@ef4fe31
       2024-11-17 16:04:36.361  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]consumer_ir|com.android.server.ConsumerIrService@ef4fe31
       2024-11-17 16:04:36.404  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]window|com.android.server.wm.WindowManagerService@6f274ac
       2024-11-17 16:04:36.404  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]window|com.android.server.wm.WindowManagerService@6f274ac
       2024-11-17 16:04:36.404  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]window|com.android.server.wm.WindowManagerService@6f274ac
       2024-11-17 16:04:36.405  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]window|com.android.server.wm.WindowManagerService@6f274ac
       2024-11-17 16:04:36.405  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]input|com.android.server.input.InputManagerService@c7c8c1a
       2024-11-17 16:04:36.405  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]input|com.android.server.input.InputManagerService@c7c8c1a
       2024-11-17 16:04:36.405  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]input|com.android.server.input.InputManagerService@c7c8c1a
       2024-11-17 16:04:36.405  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]input|com.android.server.input.InputManagerService@c7c8c1a
       2024-11-17 16:04:36.586  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]statusbar|com.android.server.statusbar.StatusBarManagerService@db42393
       2024-11-17 16:04:36.586  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]statusbar|com.android.server.statusbar.StatusBarManagerService@db42393
       2024-11-17 16:04:36.586  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]statusbar|com.android.server.statusbar.StatusBarManagerService@db42393
       2024-11-17 16:04:36.586  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]statusbar|com.android.server.statusbar.StatusBarManagerService@db42393
       2024-11-17 16:04:36.595  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_management|com.android.server.net.NetworkManagementService@579092c
       2024-11-17 16:04:36.595  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_management|com.android.server.net.NetworkManagementService@579092c
       2024-11-17 16:04:36.595  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_management|com.android.server.net.NetworkManagementService@579092c
       2024-11-17 16:04:36.595  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_management|com.android.server.net.NetworkManagementService@579092c
       2024-11-17 16:04:36.623  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]netpolicy|com.android.server.net.NetworkPolicyManagerService@d3a5cf2
       2024-11-17 16:04:36.623  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]netpolicy|com.android.server.net.NetworkPolicyManagerService@d3a5cf2
       2024-11-17 16:04:36.623  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]netpolicy|com.android.server.net.NetworkPolicyManagerService@d3a5cf2
       2024-11-17 16:04:36.623  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]netpolicy|com.android.server.net.NetworkPolicyManagerService@d3a5cf2
       2024-11-17 16:04:36.674  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]pac_proxy|com.android.server.connectivity.PacProxyService@3e2bfad
       2024-11-17 16:04:36.674  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]pac_proxy|com.android.server.connectivity.PacProxyService@3e2bfad
       2024-11-17 16:04:36.674  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]pac_proxy|com.android.server.connectivity.PacProxyService@3e2bfad
       2024-11-17 16:04:36.674  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]pac_proxy|com.android.server.connectivity.PacProxyService@3e2bfad
       2024-11-17 16:04:36.703  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]security_state|com.android.server.SecurityStateManagerService@b42706b
       2024-11-17 16:04:36.703  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]security_state|com.android.server.SecurityStateManagerService@b42706b
       2024-11-17 16:04:36.703  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]security_state|com.android.server.SecurityStateManagerService@b42706b
       2024-11-17 16:04:36.703  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]security_state|com.android.server.SecurityStateManagerService@b42706b
       2024-11-17 16:04:36.705  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vpn_management|com.android.server.VpnManagerService@554e6a4
       2024-11-17 16:04:36.705  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vpn_management|com.android.server.VpnManagerService@554e6a4
       2024-11-17 16:04:36.705  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vpn_management|com.android.server.VpnManagerService@554e6a4
       2024-11-17 16:04:36.705  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vpn_management|com.android.server.VpnManagerService@554e6a4
       2024-11-17 16:04:36.708  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vcn_management|com.android.server.VcnManagementService@1fcd627
       2024-11-17 16:04:36.708  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vcn_management|com.android.server.VcnManagementService@1fcd627
       2024-11-17 16:04:36.708  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vcn_management|com.android.server.VcnManagementService@1fcd627
       2024-11-17 16:04:36.708  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]vcn_management|com.android.server.VcnManagementService@1fcd627
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]system_update|com.android.server.SystemUpdateManagerService@c56a979
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]system_update|com.android.server.SystemUpdateManagerService@c56a979
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]system_update|com.android.server.SystemUpdateManagerService@c56a979
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]system_update|com.android.server.SystemUpdateManagerService@c56a979
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]updatelock|com.android.server.UpdateLockService@d0f6435
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]updatelock|com.android.server.UpdateLockService@d0f6435
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]updatelock|com.android.server.UpdateLockService@d0f6435
       2024-11-17 16:04:36.710  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]updatelock|com.android.server.UpdateLockService@d0f6435
       2024-11-17 16:04:36.770  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]country_detector|com.android.server.CountryDetectorService@10b59e9
       2024-11-17 16:04:36.770  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]country_detector|com.android.server.CountryDetectorService@10b59e9
       2024-11-17 16:04:36.770  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]country_detector|com.android.server.CountryDetectorService@10b59e9
       2024-11-17 16:04:36.770  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]country_detector|com.android.server.CountryDetectorService@10b59e9
       2024-11-17 16:04:37.833  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]hardware_properties|com.android.server.HardwarePropertiesManagerService@6d44120
       2024-11-17 16:04:37.833  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]hardware_properties|com.android.server.HardwarePropertiesManagerService@6d44120
       2024-11-17 16:04:37.833  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]hardware_properties|com.android.server.HardwarePropertiesManagerService@6d44120
       2024-11-17 16:04:37.833  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]hardware_properties|com.android.server.HardwarePropertiesManagerService@6d44120
       2024-11-17 16:04:37.919  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]diskstats|com.android.server.DiskStatsService@e815306
       2024-11-17 16:04:37.919  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]diskstats|com.android.server.DiskStatsService@e815306
       2024-11-17 16:04:37.919  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]diskstats|com.android.server.DiskStatsService@e815306
       2024-11-17 16:04:37.919  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]diskstats|com.android.server.DiskStatsService@e815306
       2024-11-17 16:04:37.920  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]runtime|com.android.server.RuntimeService@1005dc7
       2024-11-17 16:04:37.920  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]runtime|com.android.server.RuntimeService@1005dc7
       2024-11-17 16:04:37.920  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]runtime|com.android.server.RuntimeService@1005dc7
       2024-11-17 16:04:37.920  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]runtime|com.android.server.RuntimeService@1005dc7
       2024-11-17 16:04:37.923  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_time_update_service|com.android.server.timedetector.NetworkTimeUpdateService@6f20e24
       2024-11-17 16:04:37.923  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_time_update_service|com.android.server.timedetector.NetworkTimeUpdateService@6f20e24
       2024-11-17 16:04:37.923  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_time_update_service|com.android.server.timedetector.NetworkTimeUpdateService@6f20e24
       2024-11-17 16:04:37.923  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_time_update_service|com.android.server.timedetector.NetworkTimeUpdateService@6f20e24
       2024-11-17 16:04:37.932  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]graphicsstats|android.graphics.GraphicsStatsService@ae6cc66
       2024-11-17 16:04:37.932  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]graphicsstats|android.graphics.GraphicsStatsService@ae6cc66
       2024-11-17 16:04:37.932  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]graphicsstats|android.graphics.GraphicsStatsService@ae6cc66
       2024-11-17 16:04:37.932  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]graphicsstats|android.graphics.GraphicsStatsService@ae6cc66
       2024-11-17 16:04:37.955  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]media_router|com.android.server.media.MediaRouterService@6c475c6
       2024-11-17 16:04:37.955  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]media_router|com.android.server.media.MediaRouterService@6c475c6
       2024-11-17 16:04:37.955  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]media_router|com.android.server.media.MediaRouterService@6c475c6
       2024-11-17 16:04:37.955  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]media_router|com.android.server.media.MediaRouterService@6c475c6
       2024-11-17 16:04:38.511  1787-1787  LSPosed-Bridge          system_server                        I  1787[linearity-datsr]1787[linearity-datsr]com.topjohnwu.superuser.NoShellException: Unable to create a shell!
                                                                                                                   at com.topjohnwu.superuser.internal.BuilderImpl.exec(BuilderImpl.java:122)
                                                                                                                   at com.topjohnwu.superuser.internal.BuilderImpl.start(BuilderImpl.java:109)
                                                                                                                   at com.topjohnwu.superuser.internal.BuilderImpl.build(BuilderImpl.java:158)
                                                                                                                   at com.topjohnwu.superuser.internal.MainShell.get(MainShell.java:53)
                                                                                                                   at com.topjohnwu.superuser.Shell.getShell(Shell.java:164)
                                                                                                                   at com.linearity.datservicereplacement.StartHook.setOtherProperties(StartHook.java:492)
                                                                                                                   at com.linearity.datservicereplacement.StartHook.callOnStarted(StartHook.java:485)
                                                                                                                   at com.linearity.datservicereplacement.ActivityManagerService.HookAMS$1.beforeHookedMethod(HookAMS.java:28)
                                                                                                                   at de.robv.android.xposed.XposedBridge$LegacyApiSupport.handleBefore(Unknown Source:24)
                                                                                                                   at J.callback(Unknown Source:180)
                                                                                                                   at LSPHooker_.systemReady(Unknown Source:14)
                                                                                                                   at com.android.server.SystemServer.startOtherServices(SystemServer.java:3067)
                                                                                                                   at com.android.server.SystemServer.run(SystemServer.java:980)
                                                                                                                   at com.android.server.SystemServer.main(SystemServer.java:701)
                                                                                                                   at java.lang.reflect.Method.invoke(Native Method)
                                                                                                                   at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:580)
                                                                                                                   at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:856)
       2024-11-17 16:04:38.511  1787-1787  LSPosed-Bridge          system_server                        I  1787[linearity-datsr]--------------------------------
       2024-11-17 16:04:40.741  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]telecom|com.android.server.telecom.TelecomServiceImpl$1@de17565
       2024-11-17 16:04:40.741  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]telecom|com.android.server.telecom.TelecomServiceImpl$1@de17565
       2024-11-17 16:04:40.741  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]telecom|com.android.server.telecom.TelecomServiceImpl$1@de17565
       2024-11-17 16:04:40.741  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]telecom|com.android.server.telecom.TelecomServiceImpl$1@de17565
       2024-11-17 16:04:40.741  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]telecom|com.android.server.telecom.TelecomServiceImpl$1@de17565
       2024-11-17 16:04:40.749  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_stack|android.os.BinderProxy@20d6cf4
       2024-11-17 16:04:40.749  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_stack|android.os.BinderProxy@20d6cf4
       2024-11-17 16:04:40.749  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_stack|android.os.BinderProxy@20d6cf4
       2024-11-17 16:04:40.749  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_stack|android.os.BinderProxy@20d6cf4
       2024-11-17 16:04:40.749  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]network_stack|android.os.BinderProxy@20d6cf4
       2024-11-17 16:04:40.750  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]tethering|android.os.BinderProxy@fa1ec1d
       2024-11-17 16:04:40.750  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]tethering|android.os.BinderProxy@fa1ec1d
       2024-11-17 16:04:40.750  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]tethering|android.os.BinderProxy@fa1ec1d
       2024-11-17 16:04:40.750  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]tethering|android.os.BinderProxy@fa1ec1d
       2024-11-17 16:04:40.750  1787-1787  LSPosed-Bridge          system_server                        I  [linearity-datsr-addservice]tethering|android.os.BinderProxy@fa1ec1d
       2024-11-17 16:04:41.189  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isub|com.android.internal.telephony.subscription.SubscriptionManagerService@18407ee
       2024-11-17 16:04:41.191  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isub|com.android.internal.telephony.subscription.SubscriptionManagerService@18407ee
       2024-11-17 16:04:41.191  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isub|com.android.internal.telephony.subscription.SubscriptionManagerService@18407ee
       2024-11-17 16:04:41.192  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isub|com.android.internal.telephony.subscription.SubscriptionManagerService@18407ee
       2024-11-17 16:04:41.192  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isub|com.android.internal.telephony.subscription.SubscriptionManagerService@18407ee
       2024-11-17 16:04:41.192  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isub|com.android.internal.telephony.subscription.SubscriptionManagerService@18407ee
       2024-11-17 16:04:41.620  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]simphonebook|com.android.internal.telephony.UiccPhoneBookController@abb3e3e
       2024-11-17 16:04:41.620  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]simphonebook|com.android.internal.telephony.UiccPhoneBookController@abb3e3e
       2024-11-17 16:04:41.620  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]simphonebook|com.android.internal.telephony.UiccPhoneBookController@abb3e3e
       2024-11-17 16:04:41.620  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]simphonebook|com.android.internal.telephony.UiccPhoneBookController@abb3e3e
       2024-11-17 16:04:41.620  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]simphonebook|com.android.internal.telephony.UiccPhoneBookController@abb3e3e
       2024-11-17 16:04:41.620  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]simphonebook|com.android.internal.telephony.UiccPhoneBookController@abb3e3e
       2024-11-17 16:04:41.621  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]iphonesubinfo|com.android.internal.telephony.PhoneSubInfoController@a4949f
       2024-11-17 16:04:41.621  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]iphonesubinfo|com.android.internal.telephony.PhoneSubInfoController@a4949f
       2024-11-17 16:04:41.621  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]iphonesubinfo|com.android.internal.telephony.PhoneSubInfoController@a4949f
       2024-11-17 16:04:41.621  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]iphonesubinfo|com.android.internal.telephony.PhoneSubInfoController@a4949f
       2024-11-17 16:04:41.621  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]iphonesubinfo|com.android.internal.telephony.PhoneSubInfoController@a4949f
       2024-11-17 16:04:41.621  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]iphonesubinfo|com.android.internal.telephony.PhoneSubInfoController@a4949f
       2024-11-17 16:04:41.623  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isms|com.android.internal.telephony.SmsController@d55e9ec
       2024-11-17 16:04:41.623  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isms|com.android.internal.telephony.SmsController@d55e9ec
       2024-11-17 16:04:41.623  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isms|com.android.internal.telephony.SmsController@d55e9ec
       2024-11-17 16:04:41.623  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isms|com.android.internal.telephony.SmsController@d55e9ec
       2024-11-17 16:04:41.623  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isms|com.android.internal.telephony.SmsController@d55e9ec
       2024-11-17 16:04:41.623  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]isms|com.android.internal.telephony.SmsController@d55e9ec
       2024-11-17 16:04:41.646  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]phone|com.android.phone.PhoneInterfaceManager@32a0ffa
       2024-11-17 16:04:41.646  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]phone|com.android.phone.PhoneInterfaceManager@32a0ffa
       2024-11-17 16:04:41.647  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]phone|com.android.phone.PhoneInterfaceManager@32a0ffa
       2024-11-17 16:04:41.647  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]phone|com.android.phone.PhoneInterfaceManager@32a0ffa
       2024-11-17 16:04:41.647  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]phone|com.android.phone.PhoneInterfaceManager@32a0ffa
       2024-11-17 16:04:41.647  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]phone|com.android.phone.PhoneInterfaceManager@32a0ffa
       2024-11-17 16:04:41.648  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]telephony_ims|com.android.phone.ImsRcsController@ded0808
       2024-11-17 16:04:41.648  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]telephony_ims|com.android.phone.ImsRcsController@ded0808
       2024-11-17 16:04:41.648  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]telephony_ims|com.android.phone.ImsRcsController@ded0808
       2024-11-17 16:04:41.648  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]telephony_ims|com.android.phone.ImsRcsController@ded0808
       2024-11-17 16:04:41.648  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]telephony_ims|com.android.phone.ImsRcsController@ded0808
       2024-11-17 16:04:41.648  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]telephony_ims|com.android.phone.ImsRcsController@ded0808
       2024-11-17 16:04:41.652  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]carrier_config|com.android.phone.CarrierConfigLoader@a4e9cb4
       2024-11-17 16:04:41.652  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]carrier_config|com.android.phone.CarrierConfigLoader@a4e9cb4
       2024-11-17 16:04:41.652  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]carrier_config|com.android.phone.CarrierConfigLoader@a4e9cb4
       2024-11-17 16:04:41.652  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]carrier_config|com.android.phone.CarrierConfigLoader@a4e9cb4
       2024-11-17 16:04:41.652  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]carrier_config|com.android.phone.CarrierConfigLoader@a4e9cb4
       2024-11-17 16:04:41.652  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]carrier_config|com.android.phone.CarrierConfigLoader@a4e9cb4
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7
       2024-11-17 16:04:45.552  3112-3112  LSPosed-Bridge          com.android.phone                    I  [linearity-datsr-addservice]ions|com.android.ons.OpportunisticNetworkService$5@ff0b5a7

"""
a = re.sub(
    r"[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\.[0-9]{3}  [0-9]{4}-[0-9]{4}  [a-zA-Z0-9\- ]{22}  system_server                        I  \[linearity-addservice]",
    "", a)
# a = re.sub(r"[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\.[0-9]{3}  [0-9]{4}-[0-9]{4}  [a-zA-Z0-9\- ]{22}  system_process                       I  [0-9]{4}\[linearity-datsr]", "", b)
a = a.split("\n")

b = {}
for s in a:
    if s == '':
        continue
    s = s.split("@")[0];
    b[s] = 1
for s in b.keys():
    s0 = s.split("|")
    print("|", s0[0], format(s0[1], ">" + str(130 - len(s0[0]))), "|")
