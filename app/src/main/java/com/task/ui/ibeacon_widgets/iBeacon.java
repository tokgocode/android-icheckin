package com.task.ui.ibeacon_widgets;


public class iBeacon {
    public iBeacon() {

    }

    public iBeacon(String name, String bluetoothAddress, int txPower, int rssi) {
        this.name = name;
        this.bluetoothAddress = bluetoothAddress;
        this.txPower = txPower;
        this.rssi = rssi;
    }

    public String name;
    public int major;
    public int minor;
    public String proximityUuid;
    public String bluetoothAddress;
    public int txPower;
    public int rssi;
    public double distance = 0;

    public double x;
    public double y;

    public int getRssi() {
        return rssi;
    }
}
