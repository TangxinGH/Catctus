package com.zhu.daomengkj

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class App { // 不能有多个application 实例
    companion object {
        lateinit var dao_meng_context: Context //在app的app on create里
        lateinit var py_dm: PyObject
        lateinit var activities: MutableLiveData<String>
    }

    fun _init_() {
        //私有是为了 编译期的安全，反射时无用
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(dao_meng_context))
        }
        py_dm = Python.getInstance().getModule("daomeng")
        //初始化内容，假设现在写在 ModuleA 的 A 类，ModuleB 的 B 类中的 init() 方法中。
//最终 Application 的 onCreate 中通过反射拿到 A 类 和 B 类,调用各自的 init() 方法
//https://blog.csdn.net/qq_32452623/article/details/82086470
    }

    fun start() {
        println("开始任务")
    }

    /*-------------------*/
    fun login(): String {
//        调用相应python代码
//        登录查询

        // 调用hello.py模块中的greet函数，并传一个参数
        // 等价用法：py.getModule("hello").get("greet").call("Android");
        py_dm.callAttr("login")?.let {
//            activities.postValue(it.toString())
            return it.toString().replace(',','\n')
        }

        return "返回空了"
    }

    fun is_login(): Boolean {
        val sharedPreference =
            dao_meng_context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        val boolean = sharedPreference.getString(
            "username", null
        ) != null || sharedPreference.getString("password", null) != null
        if (!boolean) Toast.makeText(dao_meng_context, "请输入先登录！", Toast.LENGTH_SHORT).show()
        return boolean
    }

    fun chiken() {
//        活动信息查询
        py_dm.callAttr("chiken")
    }

    fun join(value: Int) {
//        报名活动
        py_dm.callAttr("join").call(value)
    }

    fun concle() {
//        退出活动
        py_dm.callAttr("concle")
    }

    fun joined() {
//        查询已报名活动
        py_dm.callAttr("joined")
    }

    fun can_join(): String {
//        查询可报名活动
        py_dm.callAttr("can_join")?.let {
            return it.toString().replace(',','\n')
        }
        return "为空了或者没有活动"
    }


}


object Py_invoke_Java {
    //    python 调用 java
    var id1 = "5655"
    var id2 = "5655"
    var id3 = "5655"

    /*--------- UI -------------*/
    fun get_account(): HashMap<String, String> {
        val sharedPreference =
            App.dao_meng_context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        val map = HashMap<String, String>()
        sharedPreference.getString("username", null)?.let { map.put("username", it) }
        sharedPreference.getString("password", null)?.let { map.put("password", it) }
        return map
    }

    fun get_id1(): String {
        return id1
    }

    fun get_id2(): String {
        return id2
    }

    fun get_id3(): String {
        return id3
    }

    /*---------ui end --------------------*/
    fun get_join_id() {

    }

    /*=------------------message-------*/

    fun result_messagebox(title: String, message: String) {
        /*从python中返回一些消息提示！！*/
        Toast.makeText(App.dao_meng_context, "${title}，${message}", Toast.LENGTH_SHORT).show()
    }


    fun showwarning(title: String, message: String) {
        Toast.makeText(App.dao_meng_context, "${title}，${message}", Toast.LENGTH_SHORT).show()

    }


    fun showinfo(title: String, message: String) {
        println("showinfo${title}和${message}")
        Toast.makeText(App.dao_meng_context, "${title}，${message}", Toast.LENGTH_SHORT).show()

    }

    /*----------message end-------*/

    fun get_path(): String {
        return App.dao_meng_context.cacheDir.absolutePath // 内部路径，不是外部
//        /data/user/0/com.zhu.daomengkj/files/
    }
}