package com.task.data

import com.task.data.dto.checkin.CheckInInputDto
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.data.dto.checkin.CheckInResponse
import com.task.data.local.LocalData
import com.task.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {

    override suspend fun checkIn(input: CheckInInputDto): Flow<Resource<CheckInResponse>> {
        return flow {
            emit(remoteRepository.checkIn(input))
        }.flowOn(ioDispatcher)
    }

    override suspend fun getCheckIns(stuNo: String): Flow<Resource<CheckInRecordResponse>> {
        return flow {
            emit(remoteRepository.getCheckIns(stuNo))
        }.flowOn(ioDispatcher)
    }
}
