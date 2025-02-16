import os
from pathlib import Path
import requests
import bs4

listOfPath = ["android/bluetooth/IBluetooth.aidl",
              "android/bluetooth/IBluetoothA2dp.aidl",
              "android/bluetooth/IBluetoothA2dpSink.aidl",
              "android/bluetooth/IBluetoothActivityEnergyInfoListener.aidl",
              "android/bluetooth/IBluetoothAvrcpController.aidl",
              "android/bluetooth/IBluetoothAvrcpTarget.aidl",
              "android/bluetooth/IBluetoothBattery.aidl",
              "android/bluetooth/IBluetoothCallback.aidl",
              "android/bluetooth/IBluetoothConnectionCallback.aidl",
              "android/bluetooth/IBluetoothCsipSetCoordinator.aidl",
              "android/bluetooth/IBluetoothCsipSetCoordinatorCallback.aidl",
              "android/bluetooth/IBluetoothCsipSetCoordinatorLockCallback.aidl",
              "android/bluetooth/IBluetoothGatt.aidl",
              "android/bluetooth/IBluetoothGattCallback.aidl",
              "android/bluetooth/IBluetoothGattServerCallback.aidl",
              "android/bluetooth/IBluetoothHapClient.aidl",
              "android/bluetooth/IBluetoothHapClientCallback.aidl",
              "android/bluetooth/IBluetoothHeadset.aidl",
              "android/bluetooth/IBluetoothHeadsetClient.aidl",
              "android/bluetooth/IBluetoothHearingAid.aidl",
              "android/bluetooth/IBluetoothHidDevice.aidl",
              "android/bluetooth/IBluetoothHidDeviceCallback.aidl",
              "android/bluetooth/IBluetoothHidHost.aidl",
              "android/bluetooth/IBluetoothLeAudio.aidl",
              "android/bluetooth/IBluetoothLeAudioCallback.aidl",
              "android/bluetooth/IBluetoothLeBroadcastAssistant.aidl",
              "android/bluetooth/IBluetoothLeBroadcastAssistantCallback.aidl",
              "android/bluetooth/IBluetoothLeBroadcastCallback.aidl",
              "android/bluetooth/IBluetoothLeCallControl.aidl",
              "android/bluetooth/IBluetoothLeCallControlCallback.aidl",
              "android/bluetooth/IBluetoothMap.aidl",
              "android/bluetooth/IBluetoothMapClient.aidl",
              "android/bluetooth/IBluetoothMcpServiceManager.aidl",
              "android/bluetooth/IBluetoothMetadataListener.aidl",
              "android/bluetooth/IBluetoothOobDataCallback.aidl",
              "android/bluetooth/IBluetoothPan.aidl",
              "android/bluetooth/IBluetoothPanCallback.aidl",
              "android/bluetooth/IBluetoothPbap.aidl",
              "android/bluetooth/IBluetoothPbapClient.aidl",
              "android/bluetooth/IBluetoothPreferredAudioProfilesCallback.aidl",
              "android/bluetooth/IBluetoothProfileServiceConnection.aidl",
              "android/bluetooth/IBluetoothQualityReportReadyCallback.aidl",
              "android/bluetooth/IBluetoothSap.aidl",
              "android/bluetooth/IBluetoothScan.aidl",
              "android/bluetooth/IBluetoothSocketManager.aidl",
              "android/bluetooth/IBluetoothVolumeControl.aidl",
              "android/bluetooth/IBluetoothVolumeControlCallback.aidl",
              "android/bluetooth/IncomingRfcommSocketInfo.aidl",
              "android/bluetooth/le/IAdvertisingSetCallback.aidl",
              "android/bluetooth/le/IDistanceMeasurementCallback.aidl",
              "android/bluetooth/le/IPeriodicAdvertisingCallback.aidl",
              "android/bluetooth/le/IScannerCallback.aidl", ]
baseLink = "https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/heads/main/android/app/aidl/"
testLink = ("https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/heads/main/android/app/aidl/android/bluetooth/IBluetoothHeadsetClient.aidl")

fakeHeaders = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 '
        'Safari/537.36'
}
pathBase = "aidlProcessor/"
for path in listOfPath:
    path = pathBase + path
    if os.path.exists(path):
        continue
    Path(path).parent.mkdir(exist_ok=True, parents=True)
    file2save = open(path, mode="w+")
    req = requests.get(baseLink + path, headers=fakeHeaders)
    soup = bs4.BeautifulSoup(req.text, 'lxml')
    lines = soup.find(name="table", class_="FileContents")
    lines = lines.find_all(name="tr", class_="u-pre u-monospace FileContents-line")
    for line in lines:
        file2save.write(line.text)
        file2save.write('\n')
    file2save.close()
