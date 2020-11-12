package com.zhu.daomengkj

import com.zhu.daomengkj.jiami.DES_ECB_Encrypt
import com.zhu.daomengkj.Interceptor.AddHeadersInterceptor
import com.zhu.daomengkj.Interceptor.LoggingInterceptor
import com.zhu.daomengkj.Py_invoke_Java.showinfo
import com.zhu.daomengkj.Py_invoke_Java.showwarning
import com.zhu.daomengkj.bean.LoginJSON
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


    fun get_token_pho(acc: String, pwd: String, mainUpdate: Main1) {

        /**
         * val sss =  "account=" +acc+ "&pwd=" +com.zhu.daomengkj.jiami.DES_ECB_Encrypt(pwd)+ "&version=4.3.6"
         * */
        val url = "https://appdmkj.5idream.net/v2/login/phone"

        val httpClientBuilder = OkHttpClient.Builder() //1.创建OkHttpClient对象


        val okHttpClient = httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(AddHeadersInterceptor())//            .addheader()则会有多个数据
            .addInterceptor(LoggingInterceptor())//增加拦截器
            .build() //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        //des加密
        DES_ECB_Encrypt(pwd)?.let {
            FormBody.Builder().add("account", acc).add("pwd", it).add("version", "4.3.6").build()
        }
            ?.let {
                Request.Builder().url(url)
                    //            .method("POST", sss.toRequestBody("application/x-www-form-urlencoded".toMediaType()))
                    .post(it)
                    .build()
            }?.let { okHttpClient.newCall(it) }?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //请求失败执行的方法
                    showwarning("登录失败","网络错误")
                }

                override fun onResponse(call: Call, response: Response) {
                    //请求成功执行的方法
                    val body = response.body?.string()
                    body?.let {
                        val res = Json.decodeFromString(LoginJSON.serializer(), body)
                        if (res.code == "100") {
                            print("登录成功，第一次登录，或者token失效后的登录，正在写入文件")
                            File("$path/a.ini").writeText(
                                res.data.name + "\n" +
                                        res.data.uid + "\n" +
                                        res.data.token + "\n"
                            )
                            mainUpdate.read()//更新 token uid name
                            showinfo("login成功",res.data.name)
                        }
                        else   showwarning("出错了", "请检查账号密码")
                    }
                }
            })

    }


