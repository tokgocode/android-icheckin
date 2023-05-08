package com.task.ui.component.checkin

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.data.DataRepository
import com.task.data.Resource
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.ui.base.BaseViewModel
import com.task.utils.PreferencesHelper
import com.task.utils.SingleEvent
import com.task.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckInRecordsModel @Inject constructor(
    private val dataRepository: DataRepository
) : BaseViewModel() {
    private val prefHelper: PreferencesHelper = PreferencesHelper.getInstance()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _checkInLiveData = MutableLiveData<Resource<CheckInRecordResponse?>>()
    val checkInLiveData = _checkInLiveData as LiveData<Resource<CheckInRecordResponse?>>

    private val _status = MutableLiveData<String?>()
    val status = _status as LiveData<String?>

    /** Error handling as UI **/

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun getCheckInRecords() {
        viewModelScope.launch {
            _checkInLiveData.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepository.getCheckIns(prefHelper.stuNo).collect {
                    when (it) {
                        is Resource.Success -> {
                            _checkInLiveData.value = Resource.Success(it.data)
                        }
                        is Resource.DataError -> {
                            _checkInLiveData.value = Resource.DataError(1)
                        }
                        is Resource.Loading -> {
                            _checkInLiveData.value = Resource.Loading()
                        }
                    }
                }
            }
        }
    }
}
