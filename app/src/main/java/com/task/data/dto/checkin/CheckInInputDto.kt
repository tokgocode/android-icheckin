package com.task.data.dto.checkin


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class CheckInInputDto(
    @Json(name = "studentNo")
    val studentNo: String = "",
    @Json(name = "studentName")
    val studentName: String = "",
    @Json(name = "beacons")
    var beacons: List<BeaconDto>
) : Parcelable
