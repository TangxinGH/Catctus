package com.zhu.daomengkj

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.zhu.daomengkj.Py_invoke_Java.showinfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class App(dmc: Context, actLive: MutableLiveData<String>) { // 不能有多个application 实例

    var dao_meng_context: Context = dmc //在app的app on create里
    var activities: MutableLiveData<String> = actLive

    var main = Main(activities)


    fun is_login(): Boolean {
        val sharedPreference =
            dao_meng_context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        val boolean = sharedPreference.getString(
            "username", null
        ) != null || sharedPreference.getString("password", null) != null
        if (!boolean) Toast.makeText(dao_meng_context, "请输入先登录！", Toast.LENGTH_SHORT).show()
        return boolean
    }


    fun login() { //相当于开线程效果，不但有返回值
        main.login()
        showinfo("login", "登录中")
        main.read()
    }
    fun getids(){
        main.read()
        main.get_id()
    }

    fun chiken() {
        main.read()
        main.chiken()

        println("登录失败")

    }

    fun join(actId: String, delay: Int) {
        println("join 进入")
        main.read()
        main.enter(actId, delay)
    }


    fun can_join() {
        println("进来can_join")
        main.read()
        main.can_join()
    }

    fun joined() {
        main.read()
        main.get_joined()
    }


    fun concle() {
        main.read()
        main.concle()
    }

}


