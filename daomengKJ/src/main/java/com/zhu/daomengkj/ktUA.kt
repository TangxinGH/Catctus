package com.zhu.daomengkj
import kotlinx.serialization.*
/**
 * https://stackoverflow.com/questions/41928803/how-to-parse-json-in-kotlin
 * */
@Serializable
data class ktUA(val channelName: Int,
                val countryCode:String,
                val device:String,
                val hardware:String,
                val modifyTime:String,
                val operator:String,
                val screenResolution:String,
                val sysVersion:String,
                val system:String,
                val uuid:String,
                val version:String,
                @Optional val b: String = "42"


)