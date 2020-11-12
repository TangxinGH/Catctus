package com.zhu.daomengkj

import com.zhu.daomengkj.Py_invoke_Java.get_account
import com.zhu.daomengkj.Py_invoke_Java.get_path
import com.zhu.daomengkj.Py_invoke_Java.showwarning
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.Exception

class Main  {
    private lateinit var uid: String
    private lateinit var name: String
    private lateinit var token: String

    /**
     *  android内部存储文件路径
     * */
    var path = ""
    var acc = ""
    var pwd = ""

    init {
        val account = get_account()
        acc = account["username"].toString()
        pwd = account["password"].toString()
        path = get_path()
        println(acc + pwd + path)
    }

    /**
     * 读配置*/
    fun read() {
        try {
            val value = File(this.path + '/' + "a.ini").readLines()
            token = value[0]
            name = value[1]
            uid = value[2]
        } catch (e: Exception) {
            println(e)
        }

    }

    /**
     * 每个操作得登录
     */
    fun login(): Boolean {
        println("文件ini是否存在" + File("$path/a.ini").exists())
        if (File("$path/a.ini").exists()) {
            return if (test_token(path)) true
            else {
                showwarning("出错了", "登录失效，请重新登录")
                File("$path/a.ini").delete()
                println("删除" + this.path + "a.ini文件,重新登录，将新生成")
                false
            }
        } else {
            /**文件不存在就登录*/
            return if (get_token(acc, pwd, path))
                true
            else {
                showwarning("出错了", "请检查账号密码")
                false
            }
        }
    }


}

fun get_ids(token: String, uid: String): JSONObject {
    return JSONObject("{CODE:'ss'}")
}

fun get_token(acc:String, pwd:String,path:String): Boolean {
//    login = Login()
//    if login.get_token_pho(acc, pwd):
//    print("登录成功，第一次登录，或者token失效后的登录，正在写入文件")
//    with open(path+'/'+'a.ini', 'w+', encoding='utf-8') as f:
//    f.write(login.token + '\n')
//    f.write(login.name + '\n')
//    f.write(login.uid)
//    return True
//    else:
//    return False
return false
}


fun test_token(path: String): Boolean {

    try {
        val value = File("$path/a.ini").readLines()
        val token = value[0]
        val name = value[1]
        val uid = value[2]

        val res = get_ids(token, uid)
        return if (res["code"] == "100") {
            print("test_token：token还能用")
            true
        } else
            false
    } catch (e: Exception) {
        println(e)
        return false
    }
}