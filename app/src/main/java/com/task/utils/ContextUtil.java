package com.task.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;

/**
 * ContextUtil，为Android程序提供全局Context对象，要在Application.onCreate中初始化。
 */
public final class ContextUtil {
    private static Context context;
    private static Application application;
    private static String mAppName = "";
    private static String mPackageName = "";

    public static void init(Application appContext) {
        application = appContext;
        context = appContext.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static Application getAplication() {
        return application;
    }

    public static int getDimensionPixelOffset(@DimenRes int dimenId) {
        return  ContextUtil.getContext().getResources().getDimensionPixelOffset(dimenId);
    }

    public static String getString(@StringRes int stringId) {
        return  ContextUtil.getContext().getString(stringId);
    }

    public static String getPackageName() {
        if (TextUtils.isEmpty(mPackageName)) {
            mPackageName = context.getPackageName();
        }
        return mPackageName;
    }
}
