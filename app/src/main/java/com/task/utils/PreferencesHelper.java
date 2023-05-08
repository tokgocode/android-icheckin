package com.task.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "icheckin_pref_file";
    public static final String PREF_KEY_STU_NAME = "PREF_KEY_STU_NAME";
    public static final String PREF_KEY_STU_NO = "PREF_KEY_STU_NO";

    private static SharedPreferences mPref;
    private static volatile PreferencesHelper preferencesHelper;

    public static PreferencesHelper getInstance() {
        if (preferencesHelper == null) {
            synchronized (PreferencesHelper.class) {
                if (preferencesHelper == null) {
                    preferencesHelper = new PreferencesHelper();
                }
            }
        }
        return preferencesHelper;
    }

    private PreferencesHelper() {
        if (mPref == null) {
            mPref = ContextUtil.getContext().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        }
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putStuName(String name) {
        mPref.edit().putString(PREF_KEY_STU_NAME, name).apply();
    }

    public String getStuName() {
        return mPref.getString(PREF_KEY_STU_NAME, null);
    }

    public void putStuNo(String number) {
        mPref.edit().putString(PREF_KEY_STU_NO, number).apply();
    }

    public String getStuNo() {
        return mPref.getString(PREF_KEY_STU_NO, null);
    }

}