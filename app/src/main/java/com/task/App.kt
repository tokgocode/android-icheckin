package com.task

import android.app.Application
import com.task.utils.ContextUtil
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
open class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ContextUtil.init(this)
    }
}
