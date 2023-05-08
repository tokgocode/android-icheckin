package com.task.data.dto.checkin


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class CheckInRecordOutputDto(
    @Json(name = "classroom")
    val classroomName: String = "",
    @Json(name = "createdTime")
    val dateTime: String = ""
) : Parcelable
