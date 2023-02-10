package com.example.template.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ContextUtils {
    lateinit var systemUiContext: Context
    lateinit var androidContext: Context
}