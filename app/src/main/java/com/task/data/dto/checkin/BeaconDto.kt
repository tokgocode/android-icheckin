package com.task.data.dto.checkin


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class BeaconDto(
    @Json(name = "beaconName")
    val beaconName: String? = "",
    @Json(name = "rssi")
    val rssi: Int = 0,
) : Parcelable
