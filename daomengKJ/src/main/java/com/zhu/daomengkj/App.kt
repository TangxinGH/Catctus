package com.zhu.daomengkj

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class App(dmc: Context, actLive: MutableLiveData<String>) { // 不能有多个application 实例

        var dao_meng_context: Context = dmc //在app的app on create里
        var activities: MutableLiveData<String> = actLive

    var main = Main1(activities)


    fun is_login(): Boolean {
        val sharedPreference =
            dao_meng_context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        val boolean = sharedPreference.getString(
            "username", null
        ) != null || sharedPreference.getString("password", null) != null
        if (!boolean) Toast.makeText(dao_meng_context, "请输入先登录！", Toast.LENGTH_SHORT).show()
        return boolean
    }




    fun login()=GlobalScope.launch { //相当于开线程效果，不但有返回值
        if (withContext(Dispatchers.Default) { main.login() }) {//等待结果
            main.read()
            main.get_id()
        }
    }
    fun chiken() {
        if( main.login() ) {
            main.chiken()
        }
        else{
            println("登录失败")
        }

    }

    fun join(toString: String, value: Int) {
        if( main.login()) {
            main.enter()
        }
        else{
            println("登录失败")
        }
    }


    fun can_join() {
        if( main.login()) {
            return main.can_join()
        }
        else{
            println("登录失败")

        }
    }

    fun joined() {
        if( main.login()) {
            main.get_joined()
        }
        else{
            println("登录失败")
        }
    }


    fun concle() {
        if( main.login()) {
            main.concle()
        }
        else{
            println("登录失败")

        }
    }

}


