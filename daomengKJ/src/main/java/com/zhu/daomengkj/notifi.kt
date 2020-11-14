package com.zhu.daomengkj

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlin.properties.Delegates

/**
 * https://kotlinlang.org/docs/reference/delegated-properties.html
 * https://jefflin1982.medium.com/kotlin-%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8observer-2972f7a45e2
 * */
class info(all: MutableSet<String>) {

    var name:String by Delegates.observable(""){
            prop, old, new ->
          println("$old -> $new")
    Regex("思想成长|实践实习").findAll(new).iterator().forEachRemaining {
        if ( it.groupValues[0] in all){

        }
    }
    }
}

fun notifiTask(context: Context){
    val result = MutableLiveData<String>()

    val sharedPreference =
        context.getSharedPreferences(
            "daomengKJNotifications",
            Context.MODE_PRIVATE
        )


     Main( result).apply {
        login()
        read()
        notifi(result, info(  sharedPreference.all.keys))
    }



}