package com.example.template.hook.app


import com.example.template.hook.BaseHook
import com.github.kyuubiran.ezxhelper.utils.*


object Android : BaseHook() {
    override fun init() {
        findMethod("") { name == "" }.hookBefore { }
        findMethod("") { name == "" }.hookAfter { }
        findConstructor("") { parameterCount == 2 }.hookAfter {}
        findConstructor("") { parameterCount == 2 }.hookAfter {}
    }
}