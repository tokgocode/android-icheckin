package com.task.ui.component.splash

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import com.task.SPLASH_DELAY
import com.task.databinding.SplashLayoutBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.home.HomeActivity
import com.task.ui.component.studentInfo.StudentInfoActivity
import com.task.utils.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: SplashLayoutBinding

    private val mPermissionList = mutableListOf<String>()

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            requestBluetoothPermissions()
        }, SPLASH_DELAY.toLong())
    }

    private fun requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mPermissionList.add(Manifest.permission.BLUETOOTH_CONNECT)
            mPermissionList.add(Manifest.permission.BLUETOOTH_SCAN)
            mPermissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE)
        }
        mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, mPermissionList.toTypedArray(), 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val prefHelper = PreferencesHelper.getInstance()
        val studentName = prefHelper.stuName
        val studentNo = prefHelper.stuNo

        if (studentName.isNullOrEmpty() || studentNo.isNullOrEmpty()) {
            val nextScreenIntent = Intent(this, StudentInfoActivity::class.java)
            startActivity(nextScreenIntent)
        } else {
            val nextScreenIntent = Intent(this, HomeActivity::class.java)
            startActivity(nextScreenIntent)
        }

        finish()
    }
}
