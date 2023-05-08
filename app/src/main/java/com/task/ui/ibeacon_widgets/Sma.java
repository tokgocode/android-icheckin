package com.task.ui.ibeacon_widgets;

import java.util.ArrayList;

public class Sma {
    iBeaconDevice dev1, dev2, dev3, dev4, dev5;

    // private queue queue_x;
    public ArrayList<Queue> arr_queue;// 存放 5个ibeacon的动态数组 每个队列有5个值
    public ArrayList<iBeaconDevice> arr_ineacon_device;
    // private  int LENGTH=5;

    public Sma() {
        arr_queue = new ArrayList<>();
        arr_ineacon_device = new ArrayList<>();

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
                case 4:
                    dev5 = Config.getDefaultDevices().get(i);
                    break;
            }
        }

        arr_queue.add(new Queue());
        arr_queue.add(new Queue());
        arr_queue.add(new Queue());
        arr_queue.add(new Queue());

        arr_ineacon_device.add(dev1);
        arr_ineacon_device.add(dev2);
        arr_ineacon_device.add(dev3);
        arr_ineacon_device.add(dev4);
    }

    public void run(String address, int rssi) {
        if (address.equals(dev1.bluetoothAddress)) {
            arr_queue.get(0).offer(rssi);
        } else if (address.equals(dev2.bluetoothAddress)) {
            arr_queue.get(1).offer(rssi);
        } else if (address.equals(dev3.bluetoothAddress)) {
            arr_queue.get(2).offer(rssi);
        } else if (address.equals(dev4.bluetoothAddress)) {
            arr_queue.get(3).offer(rssi);
        }
    }
}
