package com.linearity.datservicereplacement.androidhooking.com.android.server.audio;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.ADJUST_TOGGLE_MUTE;
import static com.linearity.datservicereplacement.ReturnIfNonSys.findArgByClassInArgs;
import static com.linearity.datservicereplacement.ReturnIfNonSys.hookAllMethodsWithCache_Auto;
import static com.linearity.datservicereplacement.StartHook.classesAndHooks;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_ARRAYLIST;
import static com.linearity.datservicereplacement.ReturnIfNonSys.EMPTY_HASHMAP;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_AttributionSourceAt;
import static com.linearity.datservicereplacement.ReturnIfNonSys.getSystemChecker_PackageNameAt;
import static com.linearity.utils.AndroidFakes.FakeVolumeSettings.fakeVolumeSettingsForUID;

import android.media.AudioDeviceVolumeManager;
import android.media.AudioManager;
import android.media.VolumeInfo;
import android.os.Binder;
import android.util.Log;
import android.view.KeyEvent;

import com.linearity.datservicereplacement.androidhooking.com.android.server.biometrics.HookBiometric;
import com.linearity.utils.AndroidFakes.FakeVolumeSettings;
import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.FakeClass.java.util.EmptyArrays;
import com.linearity.utils.SimpleExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.robv.android.xposed.XposedHelpers;

public class HookAudioService {
    public static void doHook(){
        classesAndHooks.put("com.android.server.audio.AudioService",HookAudioService::hookIAudioService);
    }
    private static void hookIAudioService(Class<?> hookClass){
        hookAllMethodsWithCache_Auto(hookClass,"trackPlayer",
                (SimpleExecutor)param -> param.setResult(fakeVolumeSettingsForUID(Binder.getCallingUid()).nextPiid()));
        hookAllMethodsWithCache_Auto(hookClass,"playerAttributes",null);
        hookAllMethodsWithCache_Auto(hookClass,"playerEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"releasePlayer",null);
        hookAllMethodsWithCache_Auto(hookClass,"trackRecorder",
                (SimpleExecutor)param -> param.setResult(fakeVolumeSettingsForUID(Binder.getCallingUid()).nextRiid()));
        hookAllMethodsWithCache_Auto(hookClass,"recorderEvent",null);
        hookAllMethodsWithCache_Auto(hookClass,"releaseRecorder",null);
        hookAllMethodsWithCache_Auto(hookClass,"playerSessionId",null);
        hookAllMethodsWithCache_Auto(hookClass,"portEvent",null);
        SimpleExecutor adjustVolume = param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            settings.changeVolume((int)param.args[0],(int)param.args[1]);
            param.setResult(null);
        };
        SimpleExecutor setVolume = param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            settings.setVolume((int)param.args[0],(int)param.args[1]);
            param.setResult(null);
        };
        hookAllMethodsWithCache_Auto(hookClass,"adjustStreamVolume", adjustVolume, getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"adjustStreamVolumeWithAttribution",adjustVolume,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setStreamVolume",setVolume,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setStreamVolumeWithAttribution",setVolume,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceVolume",(SimpleExecutor)param -> {
            VolumeInfo info = findArgByClassInArgs(param.args,VolumeInfo.class);
            if (info == null){
                return;
            }
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            settings.setVolume(info.getStreamType(),info.getVolumeIndex());
            param.setResult(null);
        },getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceVolume",(SimpleExecutor)param -> {
            VolumeInfo info = findArgByClassInArgs(param.args,VolumeInfo.class);
            if (info == null){
                return;
            }
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            FakeVolumeSettings.VolumeStats stats = settings.getVolumeStats(info.getStreamType());
            XposedHelpers.setIntField(info,"mVolIndex",stats.getVolumeIndex());
            XposedHelpers.setBooleanField(info,"mIsMuted",stats.isSilenced());
            param.setResult(info);
        },getSystemChecker_PackageNameAt(2));//VolumeInfo
        hookAllMethodsWithCache_Auto(hookClass,"handleVolumeKey",(SimpleExecutor)param -> {
            KeyEvent event = findArgByClassInArgs(param.args,KeyEvent.class);
            if (event == null){return;}
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_VOLUME_UP->{
                    FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
                    settings.changeVolume(AudioManager.USE_DEFAULT_STREAM_TYPE, ADJUST_RAISE);
                }
                case KeyEvent.KEYCODE_VOLUME_DOWN->{
                    FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
                    settings.changeVolume(AudioManager.USE_DEFAULT_STREAM_TYPE, ADJUST_LOWER);
                }
                case KeyEvent.KEYCODE_VOLUME_MUTE->{
                    FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
                    settings.changeVolume(AudioManager.USE_DEFAULT_STREAM_TYPE, ADJUST_TOGGLE_MUTE);
                }
                default -> {
                }
            }
            param.setResult(null);
        },getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"isStreamMute",(SimpleExecutor)param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            param.setResult(settings.isMute((Integer) param.args[0]));
        });
        hookAllMethodsWithCache_Auto(hookClass,"forceRemoteSubmixFullVolume",null);
        hookAllMethodsWithCache_Auto(hookClass,"isMasterMute",(SimpleExecutor)param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            param.setResult(settings.getMasterMute());
        });
        hookAllMethodsWithCache_Auto(hookClass,"setMasterMute",(SimpleExecutor)param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            settings.setMasterMute((Boolean) param.args[0]);
            param.setResult(null);
        },getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getStreamVolume",(SimpleExecutor)param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            param.setResult(settings.getVolumeStats((Integer) param.args[0]).getVolumeIndex());
        });
        hookAllMethodsWithCache_Auto(hookClass,"getStreamMinVolume",0);
        hookAllMethodsWithCache_Auto(hookClass,"getStreamMaxVolume",(SimpleExecutor)param -> {
            FakeVolumeSettings settings = fakeVolumeSettingsForUID(Binder.getCallingUid());
            param.setResult(settings.getVolumeStats((Integer) param.args[0]).maxVolumeIndex);
        });
        //TODO:Mock
        hookAllMethodsWithCache_Auto(hookClass,"getAudioVolumeGroups",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setVolumeGroupVolumeIndex",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"getVolumeGroupVolumeIndex",0);
        hookAllMethodsWithCache_Auto(hookClass,"getVolumeGroupMaxVolumeIndex",0);
        hookAllMethodsWithCache_Auto(hookClass,"getVolumeGroupMinVolumeIndex",0);
        hookAllMethodsWithCache_Auto(hookClass,"getLastAudibleVolumeForVolumeGroup",0);
        hookAllMethodsWithCache_Auto(hookClass,"isVolumeGroupMuted",true);
        hookAllMethodsWithCache_Auto(hookClass,"adjustVolumeGroupVolume",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"getLastAudibleStreamVolume",0);
        hookAllMethodsWithCache_Auto(hookClass,"setSupportedSystemUsages",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedSystemUsages",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getAudioProductStrategies",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"isMicrophoneMuted",true);
        hookAllMethodsWithCache_Auto(hookClass,"isUltrasoundSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"isHotwordStreamSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"setMicrophoneMute",null,getSystemChecker_PackageNameAt(1));
        hookAllMethodsWithCache_Auto(hookClass,"setMicrophoneMuteFromSwitch",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRingerModeExternal",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRingerModeInternal",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRingerModeExternal",0);
        hookAllMethodsWithCache_Auto(hookClass,"getRingerModeInternal",0);
        hookAllMethodsWithCache_Auto(hookClass,"isValidRingerMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVibrateSetting",null);
        hookAllMethodsWithCache_Auto(hookClass,"getVibrateSetting",0);
        hookAllMethodsWithCache_Auto(hookClass,"shouldVibrate",true);
        hookAllMethodsWithCache_Auto(hookClass,"setMode",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"playSoundEffect",null);
        hookAllMethodsWithCache_Auto(hookClass,"playSoundEffectVolume",null);
        hookAllMethodsWithCache_Auto(hookClass,"loadSoundEffects",true);
        hookAllMethodsWithCache_Auto(hookClass,"unloadSoundEffects",null);
        hookAllMethodsWithCache_Auto(hookClass,"reloadAudioSettings",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSurroundFormats",EMPTY_HASHMAP);
        hookAllMethodsWithCache_Auto(hookClass,"getReportedSurroundFormats",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setSurroundFormatEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isSurroundFormatEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setEncodedSurroundMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"getEncodedSurroundMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"setSpeakerphoneOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"isSpeakerphoneOn",true);
        hookAllMethodsWithCache_Auto(hookClass,"setBluetoothScoOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"setA2dpSuspended",null);
        hookAllMethodsWithCache_Auto(hookClass,"setLeAudioSuspended",null);
        hookAllMethodsWithCache_Auto(hookClass,"isBluetoothScoOn",true);
        hookAllMethodsWithCache_Auto(hookClass,"setBluetoothA2dpOn",null);
        hookAllMethodsWithCache_Auto(hookClass,"isBluetoothA2dpOn",true);
        hookAllMethodsWithCache_Auto(hookClass,"requestAudioFocus",0,getSystemChecker_PackageNameAt(5));
        hookAllMethodsWithCache_Auto(hookClass,"abandonAudioFocus",0,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAudioFocusClient",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCurrentAudioFocus",0);
        hookAllMethodsWithCache_Auto(hookClass,"startBluetoothSco",null);
        hookAllMethodsWithCache_Auto(hookClass,"startBluetoothScoVirtualCall",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopBluetoothSco",null);
        hookAllMethodsWithCache_Auto(hookClass,"forceVolumeControlStream",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRingtonePlayer",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRingtonePlayer",null);//IRingtonePlayer
        hookAllMethodsWithCache_Auto(hookClass,"getUiSoundsStreamType",0);
        hookAllMethodsWithCache_Auto(hookClass,"getIndependentStreamTypes",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getStreamTypeAlias",0);
        hookAllMethodsWithCache_Auto(hookClass,"isVolumeControlUsingVolumeGroups",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerStreamAliasingDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"setNotifAliasRingForTest",null);
        hookAllMethodsWithCache_Auto(hookClass,"setWiredDeviceConnectionState",null);
        hookAllMethodsWithCache_Auto(hookClass,"startWatchingRoutes",null);//AudioRoutesInfo
        hookAllMethodsWithCache_Auto(hookClass,"isCameraSoundForced",true);
        hookAllMethodsWithCache_Auto(hookClass,"setVolumeController",null);
        hookAllMethodsWithCache_Auto(hookClass,"getVolumeController",null);//IVolumeController
        hookAllMethodsWithCache_Auto(hookClass,"notifyVolumeControllerVisible",null);
        hookAllMethodsWithCache_Auto(hookClass,"isStreamAffectedByRingerMode",true);
        hookAllMethodsWithCache_Auto(hookClass,"isStreamAffectedByMute",true);
        hookAllMethodsWithCache_Auto(hookClass,"isStreamMutableByUi",true);
        hookAllMethodsWithCache_Auto(hookClass,"disableSafeMediaVolume",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"lowerVolumeToRs1",null,getSystemChecker_PackageNameAt(0));
        hookAllMethodsWithCache_Auto(hookClass,"getOutputRs2UpperBound",0.f);
        hookAllMethodsWithCache_Auto(hookClass,"setOutputRs2UpperBound",null);
        hookAllMethodsWithCache_Auto(hookClass,"getCsd",0.f);
        hookAllMethodsWithCache_Auto(hookClass,"setCsd",null);
        hookAllMethodsWithCache_Auto(hookClass,"forceUseFrameworkMel",null);
        hookAllMethodsWithCache_Auto(hookClass,"forceComputeCsdOnAllDevices",null);
        hookAllMethodsWithCache_Auto(hookClass,"isCsdEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isCsdAsAFeatureAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"isCsdAsAFeatureEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setCsdAsAFeatureEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setBluetoothAudioDeviceCategory_legacy",null);
        hookAllMethodsWithCache_Auto(hookClass,"getBluetoothAudioDeviceCategory_legacy",0);
        hookAllMethodsWithCache_Auto(hookClass,"setBluetoothAudioDeviceCategory",true);
        hookAllMethodsWithCache_Auto(hookClass,"getBluetoothAudioDeviceCategory",0);
        hookAllMethodsWithCache_Auto(hookClass,"isBluetoothAudioDeviceCategoryFixed",true);
        hookAllMethodsWithCache_Auto(hookClass,"setHdmiSystemAudioSupported",0);
        hookAllMethodsWithCache_Auto(hookClass,"isHdmiSystemAudioSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerAudioPolicy",null,getSystemChecker_AttributionSourceAt(7));//String
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAudioPolicyAsync",null);
        hookAllMethodsWithCache_Auto(hookClass,"getRegisteredPolicyMixes",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAudioPolicy",null);
        hookAllMethodsWithCache_Auto(hookClass,"addMixForPolicy",0);
        hookAllMethodsWithCache_Auto(hookClass,"removeMixForPolicy",0);
        hookAllMethodsWithCache_Auto(hookClass,"updateMixingRulesForPolicy",0);
        hookAllMethodsWithCache_Auto(hookClass,"setFocusPropertiesForPolicy",0);
        hookAllMethodsWithCache_Auto(hookClass,"setVolumePolicy",null);
        hookAllMethodsWithCache_Auto(hookClass,"hasRegisteredDynamicPolicy",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerRecordingCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterRecordingCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveRecordingConfigurations",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"registerPlaybackCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterPlaybackCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"getActivePlaybackConfigurations",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusRampTimeMs",0);
        hookAllMethodsWithCache_Auto(hookClass,"dispatchFocusChange",0);
        hookAllMethodsWithCache_Auto(hookClass,"dispatchFocusChangeWithFade",0);
        hookAllMethodsWithCache_Auto(hookClass,"playerHasOpPlayAudio",null);
        hookAllMethodsWithCache_Auto(hookClass,"handleBluetoothActiveDeviceChanged",null);
        hookAllMethodsWithCache_Auto(hookClass,"setFocusRequestResultFromExtPolicy",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerAudioServerStateDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterAudioServerStateDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"isAudioServerRunning",true);
        hookAllMethodsWithCache_Auto(hookClass,"setUidDeviceAffinity",0);
        hookAllMethodsWithCache_Auto(hookClass,"removeUidDeviceAffinity",0);
        hookAllMethodsWithCache_Auto(hookClass,"setUserIdDeviceAffinity",0);
        hookAllMethodsWithCache_Auto(hookClass,"removeUserIdDeviceAffinity",0);
        hookAllMethodsWithCache_Auto(hookClass,"hasHapticChannels",true);
        hookAllMethodsWithCache_Auto(hookClass,"isCallScreeningModeSupported",true);
        hookAllMethodsWithCache_Auto(hookClass,"setPreferredDevicesForStrategy",0);
        hookAllMethodsWithCache_Auto(hookClass,"removePreferredDevicesForStrategy",0);
        hookAllMethodsWithCache_Auto(hookClass,"getPreferredDevicesForStrategy",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceAsNonDefaultForStrategy",0);
        hookAllMethodsWithCache_Auto(hookClass,"removeDeviceAsNonDefaultForStrategy",0);
        hookAllMethodsWithCache_Auto(hookClass,"getNonDefaultDevicesForStrategy",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesForAttributes",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getDevicesForAttributesUnprotected",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"addOnDevicesForAttributesChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeOnDevicesForAttributesChangedListener",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAllowedCapturePolicy",0);
        hookAllMethodsWithCache_Auto(hookClass,"getAllowedCapturePolicy",0);
        hookAllMethodsWithCache_Auto(hookClass,"registerStrategyPreferredDevicesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterStrategyPreferredDevicesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerStrategyNonDefaultDevicesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterStrategyNonDefaultDevicesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"setRttEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDeviceVolumeBehavior",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceVolumeBehavior",0);
        hookAllMethodsWithCache_Auto(hookClass,"setMultiAudioFocusEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setPreferredDevicesForCapturePreset",0);
        hookAllMethodsWithCache_Auto(hookClass,"clearPreferredDevicesForCapturePreset",0);
        hookAllMethodsWithCache_Auto(hookClass,"getPreferredDevicesForCapturePreset",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"registerCapturePresetDevicesRoleDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCapturePresetDevicesRoleDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"adjustStreamVolumeForUid",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"adjustSuggestedStreamVolumeForUid",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"setStreamVolumeForUid",null,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"adjustVolume",null);
        hookAllMethodsWithCache_Auto(hookClass,"adjustSuggestedStreamVolume",null);
        hookAllMethodsWithCache_Auto(hookClass,"isMusicActive",true);
        hookAllMethodsWithCache_Auto(hookClass,"getDeviceMaskForStream",0);
        hookAllMethodsWithCache_Auto(hookClass,"getAvailableCommunicationDeviceIds", EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"setCommunicationDevice",true);
        hookAllMethodsWithCache_Auto(hookClass,"getCommunicationDevice",0);
        hookAllMethodsWithCache_Auto(hookClass,"registerCommunicationDeviceDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterCommunicationDeviceDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"areNavigationRepeatSoundEffectsEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setNavigationRepeatSoundEffectsEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isHomeSoundEffectEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"setHomeSoundEffectEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"setAdditionalOutputDeviceDelay",true);
        hookAllMethodsWithCache_Auto(hookClass,"getAdditionalOutputDeviceDelay",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getMaxAdditionalOutputDeviceDelay",0L);
        hookAllMethodsWithCache_Auto(hookClass,"requestAudioFocusForTest",0,getSystemChecker_PackageNameAt(5));
        hookAllMethodsWithCache_Auto(hookClass,"abandonAudioFocusForTest",0,getSystemChecker_PackageNameAt(3));
        hookAllMethodsWithCache_Auto(hookClass,"getFadeOutDurationOnFocusLossMillis",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusDuckedUidsForTest",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusFadeOutDurationForTest",0L);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusUnmuteDelayAfterFadeOutForTest",0L);
        hookAllMethodsWithCache_Auto(hookClass,"enterAudioFocusFreezeForTest",true);
        hookAllMethodsWithCache_Auto(hookClass,"exitAudioFocusFreezeForTest",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerModeDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterModeDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSpatializerImmersiveAudioLevel",0);
        hookAllMethodsWithCache_Auto(hookClass,"isSpatializerEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isSpatializerAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"isSpatializerAvailableForDevice",true);
        hookAllMethodsWithCache_Auto(hookClass,"hasHeadTracker",true);
        hookAllMethodsWithCache_Auto(hookClass,"setHeadTrackerEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isHeadTrackerEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"isHeadTrackerAvailable",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerSpatializerHeadTrackerAvailableCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSpatializerEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"canBeSpatialized",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerSpatializerCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSpatializerCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerSpatializerHeadTrackingCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSpatializerHeadTrackingCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerHeadToSoundstagePoseCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterHeadToSoundstagePoseCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSpatializerCompatibleAudioDevices",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"addSpatializerCompatibleAudioDevice",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeSpatializerCompatibleAudioDevice",null);
        hookAllMethodsWithCache_Auto(hookClass,"setDesiredHeadTrackingMode",null);
        hookAllMethodsWithCache_Auto(hookClass,"getDesiredHeadTrackingMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"getSupportedHeadTrackingModes",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getActualHeadTrackingMode",0);
        hookAllMethodsWithCache_Auto(hookClass,"setSpatializerGlobalTransform",null);
        hookAllMethodsWithCache_Auto(hookClass,"recenterHeadTracker",null);
        hookAllMethodsWithCache_Auto(hookClass,"setSpatializerParameter",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSpatializerParameter",null);
        hookAllMethodsWithCache_Auto(hookClass,"getSpatializerOutput",0);
        hookAllMethodsWithCache_Auto(hookClass,"registerSpatializerOutputCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterSpatializerOutputCallback",null);
        hookAllMethodsWithCache_Auto(hookClass,"isVolumeFixed",true);
        hookAllMethodsWithCache_Auto(hookClass,"getDefaultVolumeInfo",null);//VolumeInfo
        hookAllMethodsWithCache_Auto(hookClass,"isPstnCallAudioInterceptable",true);
        hookAllMethodsWithCache_Auto(hookClass,"muteAwaitConnection",null);
        hookAllMethodsWithCache_Auto(hookClass,"cancelMuteAwaitConnection",null);
        hookAllMethodsWithCache_Auto(hookClass,"getMutingExpectedDevice",null);//AudioDeviceAttributes
        hookAllMethodsWithCache_Auto(hookClass,"registerMuteAwaitConnectionDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"setTestDeviceConnectionState",null);
        hookAllMethodsWithCache_Auto(hookClass,"registerDeviceVolumeBehaviorDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"getFocusStack",EMPTY_ARRAYLIST);
        hookAllMethodsWithCache_Auto(hookClass,"sendFocusLoss",true);
        hookAllMethodsWithCache_Auto(hookClass,"addAssistantServicesUids",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeAssistantServicesUids",null);
        hookAllMethodsWithCache_Auto(hookClass,"setActiveAssistantServiceUids",null);
        hookAllMethodsWithCache_Auto(hookClass,"getAssistantServicesUids",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"getActiveAssistantServiceUids",EmptyArrays.EMPTY_INT_ARRAY);
        hookAllMethodsWithCache_Auto(hookClass,"registerDeviceVolumeDispatcherForAbsoluteVolume",null,getSystemChecker_PackageNameAt(2));
        hookAllMethodsWithCache_Auto(hookClass,"getHalVersion",null);//AudioHalVersionInfo
        hookAllMethodsWithCache_Auto(hookClass,"setPreferredMixerAttributes",0);
        hookAllMethodsWithCache_Auto(hookClass,"clearPreferredMixerAttributes",0);
        hookAllMethodsWithCache_Auto(hookClass,"registerPreferredMixerAttributesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterPreferredMixerAttributesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"supportsBluetoothVariableLatency",true);
        hookAllMethodsWithCache_Auto(hookClass,"setBluetoothVariableLatencyEnabled",null);
        hookAllMethodsWithCache_Auto(hookClass,"isBluetoothVariableLatencyEnabled",true);
        hookAllMethodsWithCache_Auto(hookClass,"registerLoudnessCodecUpdatesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"unregisterLoudnessCodecUpdatesDispatcher",null);
        hookAllMethodsWithCache_Auto(hookClass,"startLoudnessCodecUpdates",null);
        hookAllMethodsWithCache_Auto(hookClass,"stopLoudnessCodecUpdates",null);
        hookAllMethodsWithCache_Auto(hookClass,"addLoudnessCodecInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"removeLoudnessCodecInfo",null);
        hookAllMethodsWithCache_Auto(hookClass,"getLoudnessParams",null);//PersistableBundle
        hookAllMethodsWithCache_Auto(hookClass,"setFadeManagerConfigurationForFocusLoss",0);
        hookAllMethodsWithCache_Auto(hookClass,"clearFadeManagerConfigurationForFocusLoss",0);
        hookAllMethodsWithCache_Auto(hookClass,"getFadeManagerConfigurationForFocusLoss",null);//FadeManagerConfiguration
        hookAllMethodsWithCache_Auto(hookClass,"shouldNotificationSoundPlay",true);
    }

}
