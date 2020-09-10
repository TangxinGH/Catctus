package com.zhu.cactus.ONE

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
//服务器不支持缓存的情况下 使用自定义的拦截器
class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control") //cache for 30 days
            .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
            .build()
    }
}