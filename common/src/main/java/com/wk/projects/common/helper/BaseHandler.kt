package com.wk.projects.common.helper

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/8/6<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
 abstract class BaseHandler<T> constructor(val t:T): Handler(){
    private val wr by lazy { WeakReference<T>(t) }

    fun getTarget()=wr.get()

    fun isValid()=getTarget()!=null

    override fun dispatchMessage(msg: Message?) {
        if(!isValid()){
            return
        }
        super.dispatchMessage(msg)
    }
}