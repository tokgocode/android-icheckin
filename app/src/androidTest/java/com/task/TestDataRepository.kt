package com.task

import com.task.data.DataRepositorySource
import com.task.data.Resource
import com.task.data.dto.checkin.CheckInInputDto
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.data.dto.checkin.CheckInResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestDataRepository @Inject constructor() : DataRepositorySource {
    
    override suspend fun checkIn(input: CheckInInputDto): Flow<Resource<CheckInResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCheckIns(stuNo: String): Flow<Resource<CheckInRecordResponse>> {
        TODO("Not yet implemented")
    }
}
