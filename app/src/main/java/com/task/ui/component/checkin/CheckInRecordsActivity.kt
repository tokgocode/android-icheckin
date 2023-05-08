package com.task.ui.component.checkin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.data.Resource
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.databinding.ActivityCheckInRecordsBinding
import com.task.ui.base.BaseActivity
import com.task.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckInRecordsActivity : BaseActivity() {

    private val viewModel: CheckInRecordsModel by viewModels()
    private lateinit var binding: ActivityCheckInRecordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCheckInRecords()
    }

    override fun observeViewModel() {
        observe(viewModel.checkInLiveData, ::handleCheckInRecords)
    }

    override fun initViewBinding() {
        binding = ActivityCheckInRecordsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun handleCheckInRecords(result: Resource<CheckInRecordResponse?>) {
        when (result) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                if (result.data?.data.isNullOrEmpty()) {
                    return
                }
                binding.rv.apply {
                    addItemDecoration(
                        DividerItemDecoration(
                            this@CheckInRecordsActivity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    layoutManager = LinearLayoutManager(this@CheckInRecordsActivity)
                    adapter = CheckInRecordAdapter(result.data?.data!!, this@CheckInRecordsActivity)
                }
            }
            is Resource.DataError -> {
            }
        }
    }
}