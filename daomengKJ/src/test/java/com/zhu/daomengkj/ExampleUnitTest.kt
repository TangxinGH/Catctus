package com.zhu.daomengkj

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.UnsupportedEncodingException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.*
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

import java.util.Base64;
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
    private fun byteToHexString(bytes: ByteArray): String? {
        val sb = StringBuffer(bytes.size)
        var sTemp: String
        for (i in bytes.indices) {
            sTemp = Integer.toHexString(0xFF and bytes[i].toInt())
            if (sTemp.length < 2) sb.append(0)
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }
    @Test
    fun encrypt() {
        val input="qq147369"
        val password="51434574"
        val desCbcEncrypt = DES_CBC_Encrypt(input.toByteArray(), password.toByteArray())
        if (desCbcEncrypt != null) {
           println( byteToHexString(desCbcEncrypt))
        }
    }
    /**
     * https://blog.csdn.net/WHF_0000/article/details/79016944
     * */
    fun DES_CBC_Encrypt(
        content: ByteArray?,
        keyBytes: ByteArray?
    ): ByteArray? {
        try {
            val keySpec = DESKeySpec(keyBytes)
            val keyFactory =
                SecretKeyFactory.getInstance("DES")
            val key: SecretKey = keyFactory.generateSecret(keySpec)
            val cipher =
                Cipher.getInstance("DES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(keySpec.key)) //cbc 下可以有偏移量
            return cipher.doFinal(content)
        } catch (e: Exception) {
            println("exception:$e")
        }
        return null
    }


    @Test
    fun de(){
      println(  DES_CBC_Encrypt("51434574","qq147369"))
    }
    fun DES_CBC_Encrypt(secretKey: String, str: String): String? {
        try {
            val keyBytes = secretKey.toByteArray()
            val content = str.toByteArray()
            val keySpec = DESKeySpec(keyBytes)
            val keyFactory =
                SecretKeyFactory.getInstance("DES")
            val key = keyFactory.generateSecret(keySpec)
            val cipher =
                Cipher.getInstance("DES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE,                key                  )//ECB mode cannot use IV 不能有偏向量
            val result = cipher.doFinal(content)
            return byteToHexString(result)
        } catch (e: java.lang.Exception) {
            println("exception:$e")
        }
        return null
    }

}