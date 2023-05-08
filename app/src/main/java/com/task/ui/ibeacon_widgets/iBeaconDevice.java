package com.task.ui.ibeacon_widgets;


public class iBeaconDevice {
    public int major;
    public int minor;
    public String proximityUuid;
    public String bluetoothAddress;
    double x;
    double y;

    public iBeaconDevice(String bluetoothAddress, double x, double y) {
        this.bluetoothAddress = bluetoothAddress;
        this.x = x;
        this.y = y;
        major = 10;
        minor = 7;
        proximityUuid = "fda50693-a4e2-4fb1-afcf-c6eb07647825";
    }

    public int getMajor() {
        return major;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getMinor() {
        return minor;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public String getProximityUuid() {
        return proximityUuid;
    }
}
