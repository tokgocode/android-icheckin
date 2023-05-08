package com.task.ui.component.studentInfo

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.data.Resource
import com.task.data.error.CHECK_YOUR_FIELDS
import com.task.data.error.STU_NAME_ERROR
import com.task.data.error.STU_NUM_ERROR
import com.task.ui.base.BaseViewModel
import com.task.utils.PreferencesHelper
import com.task.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentInfoModel @Inject constructor() : BaseViewModel() {
    private val prefHelper: PreferencesHelper = PreferencesHelper.getInstance()

    private val _saveStuInfoResult = MutableLiveData<Boolean?>()
    val saveStuInfoResult = _saveStuInfoResult as LiveData<Boolean?>

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val loginLiveDataPrivate = MutableLiveData<Resource<String?>>()
    val loginLiveData: LiveData<Resource<String?>> get() = loginLiveDataPrivate

    /** Error handling as UI **/

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun saveStudentInfo(name: String?, number: String?) {
        val isNameValid = !name.isNullOrEmpty()
        val isNumberValid = !number.isNullOrEmpty() && number.length == 12
        if (isNameValid && !isNumberValid) {
            loginLiveDataPrivate.value = Resource.DataError(STU_NUM_ERROR)
        } else if (!isNameValid && isNumberValid) {
            loginLiveDataPrivate.value = Resource.DataError(STU_NAME_ERROR)
        } else if (!isNameValid) {
            loginLiveDataPrivate.value = Resource.DataError(CHECK_YOUR_FIELDS)
        } else {
            viewModelScope.launch {
                prefHelper.putStuName(name)
                prefHelper.putStuNo(number)

                _saveStuInfoResult.postValue(true)
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}
