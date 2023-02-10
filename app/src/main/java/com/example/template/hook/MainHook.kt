package com.example.template.hook


import android.app.Application
import android.content.Context
import com.example.template.hook.app.SystemUi
import com.example.template.utils.ContextUtils.androidContext
import com.example.template.utils.ContextUtils.systemUiContext
import com.example.template.utils.LogUtils
import com.example.template.utils.hookBeforeMethod
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.initHandleLoadPackage
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.setEzClassLoader
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {


    private val systemUiPackage = "com.android.systemui"
    private val androidPackage = "android"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {


        initHandleLoadPackage(lpparam)
        setEzClassLoader(lpparam.classLoader)
        when (lpparam.packageName) {
            systemUiPackage -> {
                initHooks(SystemUi)
                Application::class.java.hookBeforeMethod("attach", Context::class.java) {
                    systemUiContext = it.args[0] as Context
                }
            }

            androidPackage -> {
                initHooks(SystemUi)
                Application::class.java.hookBeforeMethod("attach", Context::class.java) {
                    androidContext = it.args[0] as Context
                }
            }
        }

    }


    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                LogUtils.i("Inited hook: ${it.javaClass.simpleName}")
            }.exceptionOrNull()?.let {
                LogUtils.i("Failed init hook: ${it.javaClass.simpleName}")
            }
        }
    }
}