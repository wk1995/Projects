package com.wk.projects.common.log

import android.util.Log
import com.wk.projects.common.constant.WkSuppressConstants

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/24<br/>
 *      desc   : 对log封装  <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
@Suppress(WkSuppressConstants.UNUSED)
object WkLogUtil {
    fun d(tag: String, msg: String) {
       WkLog.i(msg,tag)
    }
}