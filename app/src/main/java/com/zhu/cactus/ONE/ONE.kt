package com.zhu.cactus.ONE

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.zhu.cactus.App
import com.zhu.cactus.method.MainListAdapter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun getONEFor(Count: Int) {
    //进行缓存
    val httpClientBuilder = OkHttpClient.Builder()
    val cacheFile = File(App.context.externalCacheDir, "OneCache")
    val cache = Cache(cacheFile, 1024 * 1024 * 50)
    httpClientBuilder
        .addNetworkInterceptor(CacheInterceptor())
        .cache(cache)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
    val okHttpClient = httpClientBuilder.build()
    //日期
    val c1 = Calendar.getInstance()
    val pattern = "yyyy-MM-dd"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.CHINA)
    for (x in 0..Count) {
        getONE(
            0,
            "http://api.tianapi.com/txapi/one/index?key=121939b0c5048ac82af5fbaa4b1c792e&date=" + simpleDateFormat.format(
                c1.time
            ),
            x,
            okHttpClient
        )
//       getONE("https://api.berryapi.net/?service=App.Dairy.Words&app_key=LGGVnRstIT2yb4dZ&source=one",x,okHttpClient) one 作者
        getONE(1, "https://v1.alapi.cn/api/shici?type=shuqing", x, okHttpClient)//诗词
        c1.add(Calendar.DATE, -1)
    }
}

fun getONE(type: Int, url: String, position: Int, okHttpClient: OkHttpClient) {
//   val url= "http://api.tianapi.com/txapi/one/index?key=121939b0c5048ac82af5fbaa4b1c792e&date=$date"

//        if (isApkInDebug(App.context)) return//调试用阻断网络
    val request = Request.Builder()
        .url(url)
        .build()
//            val response = client.newCall(request).execute() 同步会
    val call: Call = okHttpClient.newCall(request)//异步 线程请求
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败")
        }

        override fun onResponse(call: Call, response: Response) {
//                中.string()方法使用次数超过了一次。
            val result = response.body?.string()
            if (result != null) {
                if (result.contains("\"code\":200") || result.contains("\"code\": 200")) {//请求成功

                    when (type) {
                        0 -> {
                            val mapper = ObjectMapper().registerModule(KotlinModule())
                            if (MainListAdapter.data.size > position) MainListAdapter.data[position].postValue(
                                mapper.readValue(result)
                            )
                        }
                        1 -> {
                            val json = JSONObject(result)
                            MainListAdapter.data[position].value?.newslist?.get(0)?.date =(json["data"] as JSONObject).get("origin") as String?
//                            val re= "origin\": \".*\"".toRegex().find(result)?.value?.substringAfter(" \"")?.substringBeforeLast("\"")
//                            if (MainListAdapter.data.size > position) MainListAdapter.data[position].(MainListAdapter.data[position].value)
                        }
                    }
                }
            }

        }
    })
}
//                                    {
//	"code": 200,
//	"msg": "success",
//	"data": {
//		"content": "低头独长叹，此叹无人喻。",
//		"origin": "买花 / 牡丹",
//		"author": "白居易",
//		"category": "古诗文-抒情-孤独"
//	},
//	"author": {
//		"name": "Alone88",
//		"desc": "由Alone88提供的免费API 服务，官方文档：www.alapi.cn"
//	}
//}
