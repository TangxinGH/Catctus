package com.zhu.daomengkj.Interceptor

import com.zhu.daomengkj.bean.ktUA
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import okio.*


class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )
        val response: Response = chain.proceed(request)
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

class  AddHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        logger.info("增加头部")
        val addAfterRequest = originalRequest.newBuilder()
            .header("standardUA", Json{encodeDefaults=true}.encodeToString(  ktUA()))
//            .header("Content-Type", "application/x-www-form-urlencoded")
//            .header("Host","appdmkj.5idream.net")
//            .header("Connection", "Keep-Alive")
//            .header("Accept-Encoding","gzip")
            .header("User-Agent", "okhttp/3.11.0")
            .method(originalRequest.method,  originalRequest.body)
            .build()
        return chain.proceed(addAfterRequest)
    }

}

/** This interceptor compresses the HTTP request body. Many webservers can't handle this!  */
  class GzipRequestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.body == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest)
        }
        val compressedRequest = originalRequest.newBuilder()
            .header("Content-Encoding", "gzip")
            .method(originalRequest.method, gzip(originalRequest.body))
            .build()
        return chain.proceed(compressedRequest)
    }

    private fun gzip(body: RequestBody?): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType? {
                return body!!.contentType()
            }

            override fun contentLength(): Long {
                return -1 // We don't know the compressed length in advance!
            }

            @Throws(IOException::class)
            override fun writeTo(sink: BufferedSink) {
                val gzipSink: BufferedSink = GzipSink(sink).buffer()
                body!!.writeTo(gzipSink)
                gzipSink.close()
            }
        }
    }
}