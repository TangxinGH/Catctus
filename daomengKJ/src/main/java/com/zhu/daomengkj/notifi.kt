package com.zhu.daomengkj

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlin.properties.Delegates

/**
 * https://kotlinlang.org/docs/reference/delegated-properties.html
 * https://jefflin1982.medium.com/kotlin-%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8observer-2972f7a45e2
 * */
class info(all: MutableSet<String>, callback: (String, int: Int) -> Unit) {

    var name: String by Delegates.observable("") {//属性改变时会被调用
            prop, old, new ->
        println("$old -> $new")
        if (all.size<1) return@observable //没有元素

        val builder = StringBuilder()
        all.forEach {
            builder.append(it).append("|")
        }
        val str=builder.dropLast(1) .toString()
        Regex( str ).findAll(new).iterator().forEachRemaining {
            if (it.groupValues[0] in all) {
                callback.invoke("有活动", 1)
            }else{callback.invoke("test 使用",1) }
        }
    }


}
//fun callback(callback:(String,int:Int) ->Unit){
//    callback.invoke("",1)
//}

fun notifiTask(context: Context, callback: (String, int: Int) -> Unit) {
    val result = MutableLiveData<String>()

    val sharedPreference =
        context.getSharedPreferences(
            "daomengKJNotifications",
            Context.MODE_PRIVATE
        )


   val main= Main(result)
     main.   login()
      main.  read()
    main.    notifi(result, info(sharedPreference.all.keys, callback))



}