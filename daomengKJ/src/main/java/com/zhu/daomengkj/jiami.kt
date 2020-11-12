import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**DES
 * DES/ECB/PKCS5Padding 模式
 * https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation#Counter_.28CTR.29
 * ECB mode cannot use IV 不能有偏向量
 * */
fun DES_ECB_Encrypt(str: String): String? {
    val secretKey = "51434574"
    try {
        val keyBytes = secretKey.toByteArray()
        val content = str.toByteArray()
        val keySpec = DESKeySpec(keyBytes)
        val keyFactory =
            SecretKeyFactory.getInstance("DES")
        val key = keyFactory.generateSecret(keySpec)
        val cipher =
            Cipher.getInstance("DES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)//ECB mode cannot use IV 不能有偏向量
        val result = cipher.doFinal(content)
        return byteToHexString(result)
    } catch (e: java.lang.Exception) {
        println("exception:$e")
    }
    return null
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