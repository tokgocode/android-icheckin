package com.task.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    private const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val LOCAL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * 将UTC时间字符串格式化为本地时间字符串
     * @param utcTime UTC时间字符串，格式为yyyy-MM-dd'T'HH:mm:ss'Z'
     * @return 本地时间字符串，格式为yyyy-MM-dd HH:mm:ss
     */
    fun formatUtcTime(utcTime: String): String? {
        val format = SimpleDateFormat(UTC_FORMAT, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        var localTime: String? = null
        try {
            val date = format.parse(utcTime)
            val localFormat = SimpleDateFormat(LOCAL_TIME_FORMAT, Locale.getDefault())
            localTime = localFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return localTime
    }
}