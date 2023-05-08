package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.checkin.CheckInInputDto
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.data.dto.checkin.CheckInResponse
import com.task.data.error.NETWORK_ERROR
import com.task.data.error.NO_INTERNET_CONNECTION
import com.task.data.remote.service.CheckInService
import com.task.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {

    override suspend fun checkIn(input: CheckInInputDto): Resource<CheckInResponse> {
        val service = serviceGenerator.createService(CheckInService::class.java)
        return when (val response = processCall { service.checkIn(input) }) {
            is CheckInResponse -> {
                Resource.Success(data = response as CheckInResponse)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun getCheckIns(stuNo: String): Resource<CheckInRecordResponse> {
        val service = serviceGenerator.createService(CheckInService::class.java)
        return when (val response = processCall { service.getCheckIns(stuNo) }) {
            is CheckInRecordResponse -> {
                Resource.Success(data = response as CheckInRecordResponse)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
