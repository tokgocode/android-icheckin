package com.task.ui.ibeacon_widgets;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static List<iBeaconDevice> getDefaultDevices() {
        List<iBeaconDevice> devices = new ArrayList<>();
        devices.add(new iBeaconDevice("58:06:23:03:03:00", 3.44, 0.8));
        devices.add(new iBeaconDevice("58:06:23:03:03:2C", 0, 0));
        devices.add(new iBeaconDevice("58:06:23:03:03:45", 3.44, 5.6));
        devices.add(new iBeaconDevice("58:06:23:03:03:85", 2, 3));
        devices.add(new iBeaconDevice("58:06:23:03:03:E2", 0, 5.22));

        return devices;
    }

    private static int screenX(int physicalX) {
        return (int) ((1080.0 / 3500) * physicalX);
    }

    private static int screenY(int physicalY) {
        return (int) ((1920 / 4775.0) * physicalY);
    }
}


