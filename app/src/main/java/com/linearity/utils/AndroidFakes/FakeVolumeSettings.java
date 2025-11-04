package com.linearity.utils.AndroidFakes;

import android.media.AudioManager;

import com.linearity.utils.ExtendedRandom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeVolumeSettings {
    private static Map<Integer, FakeVolumeSettings> volumeSettingsForUid = new ConcurrentHashMap<>();
    private final int uid;
    private AtomicInteger piid = new AtomicInteger();
    private AtomicInteger riid = new AtomicInteger();
    private final Map<Integer,VolumeStats> volumeStatsMap = new ConcurrentHashMap<>();
    private final ExtendedRandom randomWithTimestamp;
    private final AtomicBoolean masterMute = new AtomicBoolean(false);
    private FakeVolumeSettings(int uid){
        this.randomWithTimestamp = new ExtendedRandom((System.currentTimeMillis() ^ FakeVolumeSettings.class.getName().hashCode()) * uid);
        this.piid.set(this.randomWithTimestamp.nextInt(1000)+100);
        this.riid.set(this.randomWithTimestamp.nextInt(100)+10);
        this.uid = uid;
    }
    public boolean getMasterMute(){
        return masterMute.get();
    }
    public void setMasterMute(boolean mute){
        masterMute.set(mute);
    }
    public int getPiid(){
        return piid.get();
    }
    public int getRiid(){
        return riid.get();
    }
    public boolean isMute(int streamType){
        return getVolumeStats(streamType).isSilenced();
    }
    public VolumeStats getVolumeStats(int streamType){
        return volumeStatsMap.computeIfAbsent(uid, uid -> new VolumeStats(streamType, new ExtendedRandom(((long) uid) ^ VolumeStats.class.getName().hashCode())));
    }
    public int nextPiid(){
        synchronized (randomWithTimestamp){
            return piid.addAndGet(randomWithTimestamp.nextInt(10)+1);
        }
    }
    public int nextRiid(){
        synchronized (randomWithTimestamp){
            return riid.addAndGet(randomWithTimestamp.nextInt(10)+1);
        }
    }
    public int changeVolume(int streamType,int direction){
        VolumeStats stats = getVolumeStats(streamType);
        return stats.changeVolumeIndexByDirection(direction);
    }
    public int setVolume(int streamType,int index){
        VolumeStats stats = getVolumeStats(streamType);
        return stats.setVolumeIndex(index);
    }
    public static FakeVolumeSettings fakeVolumeSettingsForUID(int uid){
        return volumeSettingsForUid.computeIfAbsent(uid, FakeVolumeSettings::new);
    }


    public static class VolumeStats{
        private final AtomicInteger volumeIndex = new AtomicInteger();
        public final int maxVolumeIndex;
        public final int streamType;
        private final AtomicBoolean silenced = new AtomicBoolean(false);
        VolumeStats(int streamType, ExtendedRandom random){
            this.streamType = streamType;
            if (streamType == AudioManager.STREAM_MUSIC){
                maxVolumeIndex = 15;
            }else {
                maxVolumeIndex = 7;
            }
            volumeIndex.set(random.nextInt(maxVolumeIndex/2) + random.nextInt(1));
        }
        public int getVolumeIndex(){
            int result = volumeIndex.get();
            if (result < 0){
                volumeIndex.set(0);
                return 0;
            }else if(result > maxVolumeIndex){
                volumeIndex.set(maxVolumeIndex);
                return maxVolumeIndex;
            }
            return result;
        }
        public boolean isSilenced(){
            return silenced.get();
        }
        public int changeVolumeIndexByDirection(int direction){
            switch (direction){
                case AudioManager.ADJUST_RAISE->{
                    if (getVolumeIndex() < maxVolumeIndex){
                        volumeIndex.incrementAndGet();
                    }
                }
                case AudioManager.ADJUST_LOWER-> {
                    if (getVolumeIndex() > 0) {
                        volumeIndex.decrementAndGet();
                    }
                }
                case AudioManager.ADJUST_MUTE-> silenced.set(true);
                case AudioManager.ADJUST_UNMUTE-> silenced.set(false);
                case AudioManager.ADJUST_TOGGLE_MUTE-> silenced.set(!silenced.get());
                default->{
                    return volumeIndex.get();
                }
            }
            return volumeIndex.get();
        }
        public int setVolumeIndex(int index){
            if (index < 0){
                volumeIndex.set(0);
                return 0;
            }else if(index > maxVolumeIndex){
                volumeIndex.set(maxVolumeIndex);
                return maxVolumeIndex;
            }
            volumeIndex.set(index);
            return index;
        }
    }
}
