package com.zhu.cactus.ONE

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.zhu.cactus.App
import com.zhu.cactus.POJO.JsonHitokoto
import com.zhu.cactus.method.MainListAdapter
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.random.Random


fun gethitokoto(Count: Int) {
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

    for (x in 0..Count) {
        Hitokoto(x,
            okHttpClient,
            "http://v1.hitokoto.cn/"+"?random="+ Random.nextInt(Count), //为了不缓存，？
            true )
    }
}

fun Hitokoto(
    position: Int,
    okHttpClient: OkHttpClient,
    url: String,
    cache: Boolean
) {
    //    url 相同的不能缓存
    val okhttp =
        if (cache) OkHttpClient()
        else okHttpClient

    val request = Request.Builder()
        .url(url)
        .build()
    okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            if (body != null&& body.contains("\"id\"")) {
                val mapper = ObjectMapper().registerModule(KotlinModule())
                val HitokotoBean: JsonHitokoto = mapper.readValue(body)
                if (MainListAdapter.data.size > position) MainListAdapter.data[position].postValue(HitokotoBean)

            }

        }
    })
}
