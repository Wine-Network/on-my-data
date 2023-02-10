package com.example.template.utils


import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.template.utils.Utils.isNull
import de.robv.android.xposed.XSharedPreferences

class ConfigUtils {
    private var xSP: XSharedPreferences? = null
    private var mSP: SharedPreferences? = null
    private var mSPEditor: SharedPreferences.Editor? = null

    constructor(xSharedPreferences: XSharedPreferences?) {
        xSP = xSharedPreferences
        mSP = xSharedPreferences
    }

    @SuppressLint("CommitPrefEdits")
    constructor(sharedPreferences: SharedPreferences) {
        mSP = sharedPreferences
        mSPEditor = sharedPreferences.edit()
    }

    fun update() {
        if (xSP.isNull()) {
            xSP = Utils.getPref("Fuck_Home_Config")
            mSP = xSP
            return
        }
        xSP?.reload()
    }

    fun put(key: String?, any: Any) {
        when (any) {
            is Int -> mSPEditor?.putInt(key, any)
            is String -> mSPEditor?.putString(key, any)
            is Boolean -> mSPEditor?.putBoolean(key, any)
            is Float -> mSPEditor?.putFloat(key, any)
        }
        mSPEditor?.apply()
    }

    fun optInt(key: String, i: Int): Int {
        if (mSP.isNull()) {
            return i
        }
        return mSP!!.getInt(key, i)
    }

    fun optBoolean(key: String, bool: Boolean): Boolean {
        if (mSP.isNull()) {
            return bool
        }
        return mSP!!.getBoolean(key, bool)
    }

    fun optString(key: String, str: String): String {
        if (mSP.isNull()) {
            return str
        }
        return mSP!!.getString(key, str).toString()
    }

    fun optFloat(key: String, f: Float): Float {
        if (mSP.isNull()) {
            return f
        }
        return mSP!!.getFloat(key, f)
    }

    fun clearConfig() {
        mSPEditor?.clear()?.apply()
    }
}