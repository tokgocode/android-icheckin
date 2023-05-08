package com.task.ui.ibeacon_widgets;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;

import java.util.HashMap;
import java.util.Map;

public class iBeaconClass {
    static Map<String, Kalman> kalmanMap = new HashMap<>();

    static iBeaconDevice dev1, dev2, dev3, dev4, dev5;
    static final int TX = -59;

    private static void initDevices() {
        for (int i = 0; i < Config.getDefaultDevices().size(); i++) {
            switch (i) {
                case 0:
                    dev1 = Config.getDefaultDevices().get(i);
                    break;
                case 1:
                    dev2 = Config.getDefaultDevices().get(i);
                    break;
                case 2:
                    dev3 = Config.getDefaultDevices().get(i);
                    break;
                case 3:
                    dev4 = Config.getDefaultDevices().get(i);
                    break;
            }
        }
    }

    //解析数据
    @SuppressLint("MissingPermission")
    public static iBeacon fromScanData(BluetoothDevice device, int rssi, byte[] scanData, Sma sma1) {
        // rssi=sma1.run(device.getAddress(),rssi);//平滑数据算法

        // 卡尔曼滤波
        Kalman kalman = kalmanMap.get(device.getAddress());
        if (kalman == null) {
            kalman = new Kalman(16, 100);
            kalmanMap.put(device.getAddress(), kalman);
        }
        rssi = (int) (kalman).KalmanFilter(rssi);

        int startByte = 2;
        boolean patternFound = false;
        while (startByte <= 5) {
            if (((int) scanData[startByte + 2] & 0xff) == 0x02 &&
                    ((int) scanData[startByte + 3] & 0xff) == 0x15) {
                // yes!  This is an iBeacon
                patternFound = true;

                System.out.println("from scandata =============0");
                break;
            } else if (((int) scanData[startByte] & 0xff) == 0x2d &&
                    ((int) scanData[startByte + 1] & 0xff) == 0x24 &&
                    ((int) scanData[startByte + 2] & 0xff) == 0xbf &&
                    ((int) scanData[startByte + 3] & 0xff) == 0x16) {
                iBeacon iBeacon = new iBeacon();
                iBeacon.major = 0;
                iBeacon.minor = 0;
                iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000";
                iBeacon.txPower = TX;
                iBeacon.distance = getDistance(iBeacon.txPower, rssi);

                System.out.println("from scan data0: distance = " + iBeacon.distance
                        + " rssi= " + rssi);
                return iBeacon;
            } else if (((int) scanData[startByte] & 0xff) == 0xad &&
                    ((int) scanData[startByte + 1] & 0xff) == 0x77 &&
                    ((int) scanData[startByte + 2] & 0xff) == 0x00 &&
                    ((int) scanData[startByte + 3] & 0xff) == 0xc6) {

                iBeacon iBeacon = new iBeacon();
                iBeacon.major = 0;
                iBeacon.minor = 0;
                iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000";
                iBeacon.txPower = TX;
                iBeacon.distance = getDistance(iBeacon.txPower, rssi);
                System.out.println("from scan data1: distance = " + iBeacon.distance
                        + " rssi= " + rssi);
                return iBeacon;
            }
            startByte++;
        }


        if (!patternFound) {
            // This is not an iBeacon
            return null;
        }

        iBeacon iBeacon = new iBeacon();
        iBeacon.major = (scanData[startByte + 20] & 0xff) * 0x100 + (scanData[startByte + 21] & 0xff);
        iBeacon.minor = (scanData[startByte + 22] & 0xff) * 0x100 + (scanData[startByte + 23] & 0xff);
        iBeacon.txPower = scanData[startByte + 24]; // this one is signed
        iBeacon.rssi = rssi;
        iBeacon.distance = getDistance(iBeacon.txPower, rssi);
        System.out.println("from scan data2: distance = " + iBeacon.distance
                + " rssi= " + rssi);

        // AirLocate:
        // 02 01 1a 1a ff 4c 00 02 15  # Apple's fixed iBeacon advertising prefix
        // e2 c5 6d b5 df fb 48 d2 b0 60 d0 f5 a7 10 96 e0 # iBeacon profile uuid
        // 00 00 # major
        // 00 00 # minor
        // c5 # The 2's complement of the calibrated Tx Power

        // Estimote:
        // 02 01 1a 11 07 2d 24 bf 16
        // 394b31ba3f486415ab376e5c0f09457374696d6f7465426561636f6e00000000000000000000000000000000000000000000000000

        byte[] proximityUuidBytes = new byte[16];
        System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16);
        String hexString = bytesToHexString(proximityUuidBytes);
        StringBuilder sb = new StringBuilder();
        sb.append(hexString.substring(0, 8));
        sb.append("-");
        sb.append(hexString.substring(8, 12));
        sb.append("-");
        sb.append(hexString.substring(12, 16));
        sb.append("-");
        sb.append(hexString.substring(16, 20));
        sb.append("-");
        sb.append(hexString.substring(20, 32));
        iBeacon.proximityUuid = sb.toString();

        iBeacon.bluetoothAddress = device.getAddress();
        iBeacon.name = device.getName();

        return iBeacon;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    // 计算距离
    private static double getDistance(int txPower, int rssi) {
        if (0 == rssi) {
            return -1.00;
        }

        double ratio = (rssi * 1.0) / (double) (txPower - 3);
        System.out.println("txPower = " + (double) txPower
                + " rssi= " + (double) rssi
                + "ratio= " + ratio);

        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            return (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
        }

//        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2));
    }
}
