package com.task.data.remote.service

import com.task.data.dto.checkin.CheckInInputDto
import com.task.data.dto.checkin.CheckInRecordResponse
import com.task.data.dto.checkin.CheckInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CheckInService {
    @POST("checkin")
    suspend fun checkIn(@Body input: CheckInInputDto): Response<CheckInResponse>

    @GET("checkin")
    suspend fun getCheckIns(@Query("studentNo") stuNo: String): Response<CheckInRecordResponse>
}
