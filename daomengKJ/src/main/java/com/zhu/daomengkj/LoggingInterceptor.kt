package com.zhu.daomengkj

import android.annotation.SuppressLint
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import okio.IOException
import kotlin.jvm.Throws

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val newRequest = request.newBuilder()
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Host","appdmkj.5idream.net")
            .header("Connection", "Keep-Alive")
            .header("Accept-Encoding","gzip")
            .header("User-Agent", "okhttp/3.11.0")
            .build()

        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )
        val response: Response = chain.proceed(newRequest)
        val t2 = System.nanoTime()
        logger.info(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers
            )
        )
        return response
    }
}