package com.task.data.error.mapper

import android.content.Context
import com.task.R
import com.task.data.error.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) : ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(PASS_WORD_ERROR, getErrorString(R.string.invalid_password)),
            Pair(USER_NAME_ERROR, getErrorString(R.string.invalid_username)),
            Pair(CHECK_YOUR_FIELDS, "请检查姓名和学号"),
            Pair(SEARCH_ERROR, getErrorString(R.string.search_error)),
            Pair(STU_NAME_ERROR, "请检查姓名"),
            Pair(STU_NUM_ERROR, "请检查学号")
        ).withDefault { getErrorString(R.string.network_error) }
}
