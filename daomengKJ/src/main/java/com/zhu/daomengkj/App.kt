package com.zhu.daomengkj

import android.content.Context

class App { // 不能有多个application 实例
    companion object{
        lateinit var dao_meng_context: Context //在app的app on create里

    }
//    private fun  _init_(context: Context){
//        //私有是为了 编译期的安全，反射时无用
//         App.dao_meng_context=context
//        //初始化内容，假设现在写在 ModuleA 的 A 类，ModuleB 的 B 类中的 init() 方法中。
////最终 Application 的 onCreate 中通过反射拿到 A 类 和 B 类,调用各自的 init() 方法
////https://blog.csdn.net/qq_32452623/article/details/82086470
//    }
    fun start(){
        println("开始任务")
    }
    /*-------------------*/
    fun login(){
//        调用相应python代码
//        登录查询
    }
    fun chiken(){
//        活动信息查询
    }
    fun join(){
//        报名活动
    }
    fun concle(){
//        退出活动
    }
    fun joined(){
//        查询已报名活动
    }
    fun can_join(){
//        查询可报名活动
    }
}


object Py_invoke_Java{
//    python 调用 java

    /*--------- UI -------------*/
    fun get_account(): HashMap<String, String> {
        val sharedPreference = App.dao_meng_context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        val map = HashMap<String, String>()
        sharedPreference.getString("username", null)?.let { map.put("username", it) }
        sharedPreference.getString("password", null)?.let { map.put("password", it) }
        return map
    }

    fun get_id1(): String {
        return "45745"
    }
    fun get_id2(): String {
        return "44454"
    }
    fun get_id3(): String {
return "4545"
    }
    /*---------ui end --------------------*/
    fun get_join_id(){

}

/*=------------------message-------*/
    fun result_messagebox(message:String){
        /*从python中返回一些消息提示！！*/
    }
    fun showwarning(message: String){

    }
    fun showinfo(message: String){

    }
    /*----------message end-------*/

    fun get_path(): String {
        return App.dao_meng_context.cacheDir.absolutePath // 内部路径，不是外部
//        /data/user/0/com.zhu.daomengkj/files/
    }
}