package com.wk.projects.common.helper

import android.util.Log
import timber.log.Timber

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/7/28<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
@Suppress("logNotTimber")
object LogHelper {

    @JvmStatic
    fun i(tag:String,message:String?){
        Log.i(tag,message?:"null")
    }

    @JvmStatic
    fun TimberI(message:String?){
        Timber.i(message?:"null")
    }

    @JvmStatic
    fun d(tag:String,message:String?){
        Log.d(tag,message?:"null")
    }
}