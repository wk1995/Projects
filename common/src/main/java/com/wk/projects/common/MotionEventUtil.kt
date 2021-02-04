package com.wk.projects.common

import android.view.MotionEvent
import android.view.MotionEvent.*

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/23<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
object MotionEventUtil {

    @JvmStatic
    fun toString(motionEvent: MotionEvent?): String {
        when (motionEvent?.action ?: return "null") {
            ACTION_DOWN -> {
                return "ACTION_DOWN"
            }
            ACTION_UP -> {
                return "ACTION_UP"
            }
            ACTION_CANCEL -> {
                return  "ACTION_CANCEL"
            }
            ACTION_MOVE -> {
                return "ACTION_MOVE"
            }
        }
        return "unknow"
    }
}