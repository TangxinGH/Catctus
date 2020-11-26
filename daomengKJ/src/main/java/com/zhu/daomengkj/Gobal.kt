package com.zhu.daomengkj

import actsJSON
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.norbsoft.typefacehelper.TypefaceHelper

object Gobal {
    /**
     * 全局使用 从 MainActivity 来*/
    lateinit var context: Context
    var typeface: Boolean = false

    /**
     * MutableLiveData
     * */
  val acts_info= MutableLiveData<actsJSON>()
}