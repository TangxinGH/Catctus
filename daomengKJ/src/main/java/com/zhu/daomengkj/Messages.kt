package com.zhu.daomengkj

import android.app.Application
import android.content.Context
import android.widget.Toast

object Py_invoke_Java {
  lateinit var context: Context

    /*--------- UI -------------*/
    fun get_account(): HashMap<String, String> {
        val sharedPreference =
            context .getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        val map = HashMap<String, String>()
        sharedPreference.getString("username", null)?.let { map.put("username", it) }
        sharedPreference.getString("password", null)?.let { map.put("password", it) }
        return map
    }



    /*---------ui end --------------------*/
    fun get_join_id() {

    }

    /*=------------------message-------*/

    fun result_messagebox(title: String, message: String) {
        /*从python中返回一些消息提示！！*/
        Toast.makeText(context, "${title}，${message}", Toast.LENGTH_SHORT).show()
    }


    fun showwarning(title: String, message: String) {
        Toast.makeText(context, "${title}，${message}", Toast.LENGTH_SHORT).show()

    }


    fun showinfo(title: String, message: String) {
        println("showinfo${title}和${message}")
        Toast.makeText(context, "${title}，${message}", Toast.LENGTH_SHORT).show()

    }

    /*----------message end-------*/

    fun get_path(): String {
        return context.cacheDir.absolutePath // 内部路径，不是外部
//        /data/user/0/com.zhu.daomengkj/files/
    }
}