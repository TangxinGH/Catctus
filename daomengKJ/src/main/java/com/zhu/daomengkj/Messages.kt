package com.zhu.daomengkj

import android.app.AlertDialog
import android.content.Context

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

    fun showinfo(title: String, message: String) {
        println("showinfo${title}和${message}")
        //Android是不能直接在子线程中弹出Toast的，
        try {
            App.toast.postValue("${title}，${message}")
//        Looper.prepare()
//            Toast.makeText(context, "${title}，${message}", Toast.LENGTH_SHORT).show()
//        Looper.loop()
        }catch (e: Exception){
            println("弹出消息异常$e")
        }

    }

    fun showDialog(title: String,context: String){
        val hashMap=HashMap<Int,String>()
        hashMap[0] = title
        hashMap[1] = context
        try {
            App.Dialog.postValue( hashMap)
        }catch (e: Exception){
            println("弹出框异常$e")
        }

    }

    /*----------message end-------*/

    fun get_path(): String {
        return context.cacheDir.absolutePath // 内部路径，不是外部
//        /data/user/0/com.zhu.daomengkj/files/
    }
}