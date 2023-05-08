package com.task.ui.component.studentInfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.task.data.Resource
import com.task.databinding.ActivityStudentInfoBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.home.HomeActivity
import com.task.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentInfoActivity : BaseActivity() {

    private val viewModel: StudentInfoModel by viewModels()
    private lateinit var binding: ActivityStudentInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.login.setOnClickListener { saveStuInfo() }
    }

    override fun initViewBinding() {
        binding = ActivityStudentInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun observeViewModel() {
        observe(viewModel.saveStuInfoResult, ::handleSaveStuInfoResult)
        observe(viewModel.loginLiveData, ::handleLoginResult)
        observeSnackBarMessages(viewModel.showSnackBar)
        observeToast(viewModel.showToast)
    }

    private fun saveStuInfo() {
        viewModel.saveStudentInfo(
            binding.studentName.text.trim().toString(),
            binding.studentNumber.text.trim().toString()
        )
    }

    private fun handleSaveStuInfoResult(isSuccess: Boolean?) {
        if (isSuccess == true) {
            val nextScreenIntent = Intent(this, HomeActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }
    }

    private fun handleLoginResult(status: Resource<String?>) {
        when (status) {
            is Resource.Loading -> binding.loaderView.toVisible()
            is Resource.Success -> status.data?.let {
                binding.loaderView.toGone()
            }
            is Resource.DataError -> {
                binding.loaderView.toGone()
                status.errorCode?.let { viewModel.showToastMessage(it) }
            }
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }
}
