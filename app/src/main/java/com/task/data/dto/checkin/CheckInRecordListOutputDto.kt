package com.task.data.dto.checkin


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class CheckInRecordListOutputDto(
    @Json(name = "items")
    val items: List<CheckInRecordOutputDto>,
    @Json(name = "count")
    val count: Int = 0
) : Parcelable
