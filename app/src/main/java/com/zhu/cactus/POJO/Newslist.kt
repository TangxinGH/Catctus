package com.zhu.cactus.POJO

import androidx.lifecycle.MutableLiveData
import java.util.*

/**
 * Copyright 2020 bejson.com
 */


/**
 * Auto-generated: 2020-09-10 9:26:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
class Newslist : MutableLiveData<String>() {
    Override fun setValue( value:String)
    {
        super.setValue(value);

        //listen to property changes
        value.addOnPropertyChangedCallback(callback)
    }
    var oneid = 0
    var word: String? = null
    var wordfrom: String? = null
    var imgurl: String? = null
    var imgauthor: String? = null
    var date: String? = null

}