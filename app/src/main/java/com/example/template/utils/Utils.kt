@file:Suppress("DEPRECATION")

package com.example.template.utils


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.template.BuildConfig
import com.example.template.config.Config
import de.robv.android.xposed.XSharedPreferences


object Utils {
    const val TAG = "template"
    val XConfig: Config by lazy { Config(getPref("${TAG}_Config")) }


    fun getPref(key: String?): XSharedPreferences? {
        val pref = XSharedPreferences(BuildConfig.APPLICATION_ID, key)
        return if (pref.file.canRead()) pref else null
    }

    @SuppressLint("WorldReadableFiles")
    fun getSP(context: Context, key: String?): SharedPreferences? {
        return context.createDeviceProtectedStorageContext()
            .getSharedPreferences(key, Context.MODE_WORLD_READABLE)
    }


    fun catchNoClass(callback: () -> Unit) {
        runCatching { callback() }.exceptionOrNull().let {
            Log.i(TAG, "${callback.javaClass.simpleName}错误")
        }
    }


    fun Any?.isNull(callback: () -> Unit) {
        if (this == null) callback()
    }

    fun Any?.isNotNull(callback: () -> Unit) {
        if (this != null) callback()
    }

    fun Any?.isNull() = this == null

    fun Any?.isNotNull() = this != null
}