package com.task.ui.ibeacon_widgets;

public class Algorithm {
    Sma sma1;

    int x;
    int y;

    public Algorithm(Sma sma) {
        sma1 = sma;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double algorithm(iBeacon currentBeacon) {
        sma1.run(currentBeacon.bluetoothAddress, currentBeacon.rssi);//入队
        double buff = 0;
        int temp = 0;
        for (int i = 0; i < 4; i++) {//循环遍历5个队列
            if (sma1.arr_queue.get(i).full()) {//如果大于5)
                for (int j = i + 1; j < 4; j++)
                    if (sma1.arr_queue.get(j).full()) {//如果大于5
                        buff += twoPoints(sma1.arr_ineacon_device.get(i), sma1.arr_ineacon_device.get(j), sma1.arr_queue.get(i).getRelute(), sma1.arr_queue.get(j).getRelute());
                        temp++;
                    }
            }

        }
        buff = buff / temp;
        return buff;
    }

    public double twoPoints(iBeaconDevice currentBeacon, iBeaconDevice lastBeacon, double now_dis, double last_dis) {
        double relute;
        double buf1, buf2;
        if (now_dis + last_dis > Math.abs(currentBeacon.y - lastBeacon.y)) {//如果是在两点外
            if (currentBeacon.y > lastBeacon.y) {//now是距离更远的信标
                buf1 = currentBeacon.y + now_dis;
                buf2 = lastBeacon.y + last_dis;
            } else {//now是距离更近的信标
                buf1 = currentBeacon.y - now_dis;
                buf2 = lastBeacon.y - last_dis;
            }
        } else {//在两点内
            if (currentBeacon.y > lastBeacon.y) {//now是距离更远的信标
                buf1 = currentBeacon.y - now_dis;
                buf2 = lastBeacon.y + last_dis;
            } else {//last是距离更远的信标
                buf1 = currentBeacon.y + now_dis;
                buf2 = lastBeacon.y - last_dis;
            }
        }
        relute = (buf1 + buf2) / 2;
        return relute;
    }
}

