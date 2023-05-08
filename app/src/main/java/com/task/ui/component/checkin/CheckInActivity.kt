package com.task.ui.component.checkin

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.task.R
import com.task.data.Resource
import com.task.data.dto.checkin.BeaconDto
import com.task.data.dto.checkin.CheckInResponse
import com.task.databinding.ActivityCheckInBinding
import com.task.ui.anim.BreathInterpolator
import com.task.ui.base.BaseActivity
import com.task.ui.ibeacon_widgets.Sma
import com.task.ui.ibeacon_widgets.iBeacon
import com.task.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CheckInActivity : BaseActivity() {
    private val loggedBeacons = mutableListOf<iBeacon>()
    private val TX = -61
    private var bluetoothAdapter: BluetoothAdapter? = null

    private val viewModel: CheckInModel by viewModels()
    private lateinit var binding: ActivityCheckInBinding
    private var startTime: Long = 0
    private var isStopped = false
    private var animatorSet: AnimatorSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        binding.tvConsole.text = "scanning......"

        bluetoothAdapter!!.startLeScan(leScanCallback)
    }

    override fun initViewBinding() {
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startScaleBreathAnimation()
    }

    override fun observeViewModel() {
        observe(viewModel.status, ::handleConsole)
        observe(viewModel.checkInLiveData, ::handleCheckInResult)
    }

    private fun handleConsole(status: String?) {
        if (!status.isNullOrEmpty()) {
            binding.tvConsole.append(status)
        }
    }

    private fun handleCheckInResult(result: Resource<CheckInResponse?>) {
        when (result) {
            is Resource.Loading -> {
                binding.tvConsole.text = "正在签到..."
            }
            is Resource.Success -> {
                binding.tvConsole.text = "签到成功！"
                animatorSet?.cancel()

            }
            is Resource.DataError -> {
                binding.tvConsole.text = "签到失败:" + result?.data?.message
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val leScanCallback = LeScanCallback { device: BluetoothDevice, rssi: Int, scanRecord: ByteArray? ->
        if (isStopped) {
            return@LeScanCallback
        }

        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
        }

        if (System.currentTimeMillis() - startTime > 5000) {
            stopScan()
            if (loggedBeacons.size > 0) {
                binding.icon.setImageResource(R.drawable.icon_check)
            }
            val beacons = loggedBeacons.map { b -> BeaconDto(b.proximityUuid, b.rssi) }
            viewModel.checkIn(beacons)

            return@LeScanCallback
        }

        if (!device.address.startsWith("58:06")) {
            return@LeScanCallback
        }

        val beacon = iBeacon(device.name, device.address, TX, rssi)
        if (scanRecord != null) {
            beacon.proximityUuid = parseUUID(scanRecord)
        }

        var alreadyLogged = false
        run {
            var i = 0
            while (i < loggedBeacons.size) {
                val loggedBeacon: iBeacon = loggedBeacons.get(i)
                if (loggedBeacon.bluetoothAddress.equals(beacon.bluetoothAddress)) {
                    loggedBeacons.set(i, beacon)
                    alreadyLogged = true
                    break
                }
                i++
            }
        }

        if (!alreadyLogged) {
            loggedBeacons.add(beacon)
        }

        loggedBeacons.sortByDescending { d -> d.rssi }
        binding.tvConsole.text = "scanning......\n"
        loggedBeacons.forEach { d ->
            binding.tvConsole.append(d.name + " " + d.bluetoothAddress + " " + d.rssi + "\n")
        }
    }

    private fun stopScan() {
        isStopped = true
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        bluetoothAdapter!!.stopLeScan(leScanCallback)
    }

    private fun startScaleBreathAnimation() {
        val scaleX = ObjectAnimator.ofFloat(binding.ivBreathing, "scaleX", 0.8f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.ivBreathing, "scaleY", 0.8f, 1f)
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatCount = ValueAnimator.INFINITE
        animatorSet = AnimatorSet()
        animatorSet?.playTogether(scaleX, scaleY)
        animatorSet?.duration = 3000
        animatorSet?.interpolator = BreathInterpolator()
        animatorSet?.start()
    }

    override fun onPause() {
        super.onPause()

        stopScan()
    }

    private fun parseUUID(scanRecord: ByteArray): String? {
        var uuid: String? = ""
        for (i in scanRecord.indices) {
            if (scanRecord[i].toInt() == 0x02 && scanRecord[i + 1].toInt() == 0x15) {
                val uuidBytes = Arrays.copyOfRange(scanRecord, i + 2, i + 18)
                uuid = bytesToHexString(uuidBytes)
                break
            }
        }
        return uuid
    }

    private fun bytesToHexString(bytes: ByteArray): String? {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}