package com.zhu.daomengkj

import android.annotation.SuppressLint
import android.widget.Toast
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun result_messagebox(title: String, message: String) =
        /*从python中返回一些消息提示！！*/
        println(title + message)


    @Test
    fun showwarning() {

        result_messagebox(title = "xxxx", message = "xxffgfxx")
    }
}