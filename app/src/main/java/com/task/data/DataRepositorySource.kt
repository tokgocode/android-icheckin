package com.task.data

import com.task.data.dto.checkin.CheckInInputDto
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.data.dto.checkin.CheckInResponse
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun checkIn(input: CheckInInputDto): Flow<Resource<CheckInResponse>>

    suspend fun getCheckIns(stuNo: String): Flow<Resource<CheckInRecordResponse>>

}
