package com.zhu.cactus.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime(): Boolean {
     val  sdf =   SimpleDateFormat("HH")
     val  hour= sdf.format(Date())
    val  k  = Integer.parseInt(hour)
    return (k in 0..5) ||(k in 19..23)
}
