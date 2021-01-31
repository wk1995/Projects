package com.wk.projects.common

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference


/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/9/13<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
abstract class BaseHandler<T>(t:T):Handler() {
    private  val instance :WeakReference<T> by lazy {
        WeakReference(t)
    }
    final override fun handleMessage(msg: Message) {
        val target=instance.get()?:return
        handleMessage(target,msg)
    }

    abstract fun handleMessage(t:T,msg: Message?)
}