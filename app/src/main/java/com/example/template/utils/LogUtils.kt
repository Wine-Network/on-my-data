package com.example.template.utils


import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.template.utils.Utils.TAG
import de.robv.android.xposed.XposedBridge

object LogUtils {
    private const val maxLength = 4000
    private val handler by lazy { Handler(Looper.getMainLooper()) }


    @JvmStatic
    fun log(obj: Any?, toXposed: Boolean = false, toLogd: Boolean = false) {
        val content = if (obj is Throwable) Log.getStackTraceString(obj) else obj.toString()
        if (content.length > maxLength) {
            val chunkCount = content.length / maxLength
            for (i in 0..chunkCount) {
                val max = 4000 * (i + 1)
                if (max >= content.length) {
                    if (toXposed) XposedBridge.log("$TAG: " + content.substring(maxLength * i))
                    if (toLogd) Log.d(TAG, content.substring(maxLength * i))
                } else {
                    if (toXposed) XposedBridge.log("$TAG: " + content.substring(maxLength * i, max))
                    if (toLogd) Log.d(TAG, content.substring(maxLength * i, max))
                }
            }
        } else {
            if (toXposed) XposedBridge.log("$TAG: $content")
            if (toLogd) Log.d(TAG, content)
        }
    }

    fun e(obj: Any?) {
        log(obj, toXposed = true)
    }

    fun i(obj: Any?) {
        log(obj, toXposed = true, toLogd = true)
    }

    fun d(obj: Any?) {
        log(obj, toLogd = true)
    }

}
