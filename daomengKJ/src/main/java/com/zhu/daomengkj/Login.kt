package com.zhu.daomengkj

import DES_ECB_Encrypt
import android.telecom.Call
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


class Login {
    val standardUA = {
        ( UA.Builder.instance
            .createTime((100 * System.currentTimeMillis()).toInt())
            .modifyTime((100 * System.currentTimeMillis()).toInt())
            .startTime((100 * System.currentTimeMillis()).toInt())
            .build()
//        "'channelName': 'dmkj_Android', 'countryCode': 'US', 'createTime': "+(100 * System.currentTimeMillis()).toInt()+
//                ",'device': 'xiaomi Redmi Note 7 Pro', 'hardware': 'qcom', 'modifyTime': "+(100 * System.currentTimeMillis().toInt())+
//                ",'operator': '%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8', 'screenResolution': '1080-2131','startTime': "+(100 * System.currentTimeMillis().toInt()))+19606523+
//                ", 'sysVersion': 'Android 29 10', 'system': 'android','uuid': '7C:03:AB:21:F1:DD', 'version': '4.3.6'"
    }



    fun get_token_pho( acc:String, pwd:String) {

       val sss =  "account=" +acc+ "&pwd=" +DES_ECB_Encrypt(pwd)+ "&version=4.3.6"
       val url = "https://appdmkj.5idream.net/v2/login/phone"

        //进行缓存
        val httpClientBuilder = OkHttpClient.Builder() //1.创建OkHttpClient对象

        val okHttpClient = httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())//增加拦截器
            .build() //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
//            .addheader()则会有多个数据
        val call=  okHttpClient.newCall(Request.Builder().url(url).header("standardUA",standardUA).method("POST", sss).build()) // //3.创建一个call对象,参数就是Request请求对象
//4.请求加入调度，重写回调方法

        //4.请求加入调度，重写回调方法
        call.enqueue( object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //请求失败执行的方法
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                //请求成功执行的方法
             val body=   response.body.toString()
                val res=JSONObject(body)
                if (res["code"] == "100")

                    println(res)

            }
        })
//        res = requests.post(url, headers = headers, data = urlencode(sss)).json()
//        if res['code'] == '100':
//        self.name = res['data']['name']
//        self.uid = str(res['data']['uid'])
//        self.token = res['data']['token']
//        return True
//        else:
//        return False
    }
    //des加密



}