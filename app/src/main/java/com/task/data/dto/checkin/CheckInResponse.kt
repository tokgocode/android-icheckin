package com.task.data.dto.checkin


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class CheckInResponse(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "message")
    val message: String? = "",
    @Json(name = "data")
    val data: CheckInResult?
) : Parcelable
