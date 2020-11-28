package com.zhu.daomengkj

import actsJSON
import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.lifecycle.MutableLiveData
import com.norbsoft.typefacehelper.TypefaceHelper

object Global {
    /**
     * 全局使用 从 MainActivity 来*/
    lateinit var context: Context
    var typeface: Boolean = false

    /**
     * MutableLiveData
     * */
  val acts_info= MutableLiveData<actsJSON>()

    fun isApkInDebug(context: Context): Boolean {
        return try {
            val info = context.applicationInfo
            info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {
            false
        }
    }


}