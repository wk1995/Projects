package com.wk.projects.common.helper

import android.os.Looper

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/7/28<br/>
 *      desc   :  整合一些关于线程的方法 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
object TheadHelper {

    /**是否是主线程*/
    fun isMainThread():Boolean{
        val isMainThread=Thread.currentThread()==Looper.getMainLooper().thread
        LogHelper.TimberI(isMainThread.toString())
        return isMainThread
    }
}