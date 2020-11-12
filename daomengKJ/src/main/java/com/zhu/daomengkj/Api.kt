package com.zhu.daomengkj

import java.io.File
import java.lang.Exception


var path = ""

class Main1 constructor() : Post() {


    //    """类的帮助信息"""  // 类文档字符串
    var acc = ""
    var pwd = ""
    var path = ""
    var instance:Py_invoke_Java

    init {
        // 构造函数 如果子类定义了自己的初始化函数 ， 而在子类中没有显示调用父类的初始化函数 ， 则父类的属性不会被初始化
        println("call __init__ from Child class")
//        super().__init__()   //要将子类Child和this传递进去
         instance =  Py_invoke_Java
       val account =  instance.get_account()
        this.acc = account.get("username").toString()
        this.pwd = account.get("password").toString()
        this.path = this.instance.get_path() // android内部存储文件路径
        println(this.acc+this.pwd+this.path)
    }

    fun read() {
        try {
            val value= File(this.path + '/' + "a.ini").readLines()


            this.token = value[0]
            this.name =value[1]
            this.uid =value[2]
        }catch (e:Exception){
            println(e)
        }


    }


    fun login( ): Boolean {
        println("文件ini是否存在"+ File(path + '/' + "a.ini").exists( ))
        if( File(path + '/' + "a.ini").exists()) {
            if ( test_token(path)) return true
            else {
                this.instance.showwarning("出错了", "登录失效，请重新登录")
                File(path + '/' + "a.ini").delete()
                println("删除" + this.path + "a.ini文件,重新登录，将新生成")
                return false
            }
        }                else{
                if( get_token(this.acc, this.pwd, this.path))
                    return true
                    else{
                    this.instance.showwarning("出错了", "请检查账号密码")
                    return false
                }
                }
            }

    fun get_id( ) {
        this.get_ids(this.token, this.uid) // 仅我可以报名的活动
        this.instance.showinfo("欢迎您", this.name)
//        names = []
//        for name, id, statusText in zip(this.names, this.ids, this.statusTexts){
//            names.append(name + '   {}   {}'.format(id, statusText))
//            return names
//        }
    }

    fun can_join( ) {
//        names = ['可报名活动']
        if( this.get_can_join(this.token, this.uid)) {
            println(
                "打印this.names, this.ids, this.statusTexts"+
                this.names+
                this.ids+
                this.statusTexts
            )
//            for name, id, statusText in zip(this.names, this.ids, this.statusTexts){
//                names.append(name + '   {}   {}'.format(id, statusText))
//                println("can_join中的names", names)
//                return names
//            }
        }
            else{
            this.instance.showwarning("出错了", "没有活动")
        }

    }

    fun chiken( ): String {
        val id = this.instance.get_id1()
       val res = this.get_info(id, this.token, this.uid)
        if( res!=null )
            return res
            else{
            this.instance.showwarning("出错了", "查询失败，请检查活动id")
        }
        return "预存款有东西"
        }

    fun get_start_time( ) {
       val to_join_act_id = this.instance.get_id2()  // 活动id

       var res =
            this.get_info(to_join_act_id, this.token, this.uid)
        println("get_start_time中的res 可能返回了false"+ res)
        if( res!=null) {
//          var  text = res.["data"]["joindate"]  // 报名时间
//            text = text.split(
//                '-',
//                1
//            )[0]  // split() 通过指定分隔符对字符串进行切片 第二个参数为 1，返回两个参数列表
//            return text
        }
            else{
            this.instance.showwarning("出错了", "查询报名时间，请检查活动id")
//            return false
        }
        }

    fun enter( ) {
       val id = this.instance.get_id2()
        println("报名的id为"+ id)
       val start_time =
            this.get_start_time()  // post 类的 属性 start_time 赋值 ，保存这个值
        println("start_time为"+ start_time)

       var res = this.join(id, this.token, this.uid, start_time)
//        if( res ) {
//            if (res['code'] == '100') {
//                this.instance.showinfo('报名详情', '报名成功')
//
//            } else {
//                this.instance.showinfo('报名详情', res['msg'])
//            }
//        }
//                else{
//                this.instance.showwarning('出错了', '查询失败，请检查id')
//            }



    }

    fun get_joined( ) {
      val  res = this.get_activity(this.token, this.uid)
//        names = []
//        ids = []
//        heights = ['已报名活动']
//        if (res) {
//            for (li in res['data']['list']) {
//                if (li['statusText'] == '报名中') {
//                    names.append(li['name'])
//                    ids.append(li['aid'])
//                }
//            }
//
//            if (names) {
//                for (name, id in zip(names, ids)){
//                    heights.append(name + '   {}'.format(id))
//                    return names
//                }
//            } else {
//                this.instance.showwarning(
//                    '出错了',
//                    '没有已报名活动'
//                )
//            }
//        }
    }



    fun concle( ) {
//        id = this.instance.get_id3()
//        res = this.get_info(id, this.token, this.uid)
//        if( res) {
//            signUpId = str(res['data']['signUpId'])
//            if( this.get_cancle(
//                signUpId,
//                this.token,
//                this.uid
//            )["code"] == "100"
//            ) {
//                this.instance.showinfo("成功", "取消报名成功")
//            }
//            else{
//                this.instance.showwarning("出错了", "失败，请检查活动id")
//            }
//            }
        }
    }



open class Post {
    fun test_token(path: String): Boolean { return false}
    fun get_token(acc: String, pwd: String, path: String): Boolean {return false}
    fun get_ids(token: String, uid: String) {}
    fun get_can_join(token: String, uid: String):Boolean {return false}
    fun get_info(toJoinActId: String, token: String, uid: String):String {return "sdf"}
    fun join(id: String, token: String, uid: String, startTime: Unit): Boolean {
        TODO("Not yet implemented")
    }

    fun get_activity(token: String, uid: String): Any {
    return "ss"
    }

    val statusTexts: String = ""
    val ids: String = ""
    val names:String = ""
    lateinit var uid: String
    lateinit var name: String
    lateinit var token: String
}

var main = Main1()


fun login(): Unit? {
    if( main.login()) {
        println("登录成功了")
        main.read()
        return main.get_id()
    }
        else{
        return null
    }

}

fun chiken() {
    if( main.login() ) {
        main.read()
        main.chiken()
    }
        else{
        println("登录失败")
    }

}

fun join() {
    if( main.login()) {
        main.read()  // 读取最新的 token
        main.enter()
    }
        else{
        println("登录失败")
    }
    }


fun can_join() {
    if( main.login()) {
        main.read()
        return main.can_join()
    }
        else{
        println("登录失败")

    }
}

fun joined() {
    if( main.login()) {
        main.read()
        main.get_joined()
    }
        else{
        println("登录失败")
    }
    }


fun concle() {
    if( main.login()) {
        main.read()
        main.concle()
    }
        else{
        println("登录失败")

    }
    }
