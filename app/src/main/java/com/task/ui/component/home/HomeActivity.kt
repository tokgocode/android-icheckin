package com.task.ui.component.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.task.databinding.ActivityCheckInBinding
import com.task.databinding.ActivityHomeBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.checkin.CheckInActivity
import com.task.ui.component.checkin.CheckInRecordsActivity
import com.task.ui.component.studentInfo.StudentInfoActivity
import com.task.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            btnCheckIn.setOnClickListener { v ->
                startActivity(Intent(this@HomeActivity, CheckInActivity::class.java))
            }

            btnHistory.setOnClickListener { v ->
                startActivity(Intent(this@HomeActivity, CheckInRecordsActivity::class.java))
            }
        }
    }

    override fun initViewBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun observeViewModel() {
    }

}