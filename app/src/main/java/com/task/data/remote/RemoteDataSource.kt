package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.checkin.CheckInInputDto
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.data.dto.checkin.CheckInResponse


internal interface RemoteDataSource {
    suspend fun checkIn(input: CheckInInputDto): Resource<CheckInResponse>
    suspend fun getCheckIns(stuNo: String): Resource<CheckInRecordResponse>
}
