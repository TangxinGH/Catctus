package com.zhu.daomengkj.bean

import kotlinx.serialization.*

/**
 * @see <a href="https://stackoverflow.com/questions/41928803/how-to-parse-json-in-kotlin"> kotlin 序列化</a>
 *@see<a href=" https://stackoverflow.com/questions/41928803/how-to-parse-json-in-kotlin">例子</a>
 *@see<a href=" https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/json.md#encoding-defaults "> 默认值不编码问题</a>
 * Unix时间戳（1970纪元后经过的浮点秒数)
 * 时间戳精度有两个概念：1是精确到秒，2是精确到毫秒。
 * System.currentTimeMillis()是毫秒
 * */
@Serializable
data class ktUA(
    val createTime: Long = System.currentTimeMillis(),
    val modifyTime: Long = System.currentTimeMillis(),
    val startTime: Long = System.currentTimeMillis(),
    val channelName: String = "dmkj_Android",
    val countryCode: String = "US",
    val device: String = "xiaomi Redmi Note 7 Pro",
    val hardware: String = "qcom",
    val operator: String = "%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8",
    val screenResolution: String = "1080-2131",
    val sysVersion: String = "Android 29 10",
    val system: String = "android",
    val uuid: String = "7C:03:AB:21:F1:DD",
    val version: String = "4.3.6"
)