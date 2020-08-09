package com.zhu.cactus

import android.app.Application
import com.gyf.cactus.callback.CactusCallback

class App: Application(),CactusCallback {
    override fun doWork(times: Int) {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }
//    https://juejin.im/entry/6844903496806825998  Application的介绍 单例模式 Android系统的入口是Application类的 onCreate（），默认为空实现
}