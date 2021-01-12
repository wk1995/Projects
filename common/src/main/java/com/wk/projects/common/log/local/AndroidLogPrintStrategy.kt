package com.wk.projects.common.log.local

import android.annotation.SuppressLint
import android.util.Log
import com.wk.projects.common.constant.WkStringConstants.STR_NULL_LOW
import com.wk.projects.common.constant.WkSuppressConstants

/**
 *
 *      @author : wk <br/>
 *      e-mail : 122642603@qq.com <br/>
 *      time   : 2021/01/07 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 *      desc   : 使用android.util.log,不做任何处理
 *
 */
@Suppress(WkSuppressConstants.UNUSED)
@SuppressLint("LogNotTimber")
class AndroidLogPrintStrategy : ILocalPrintStrategy {
    override fun v(msg: Any?, vararg tag: String): Int {
        var result = 0
        tag.forEach {
            result += Log.v(it, msg?.toString() ?: STR_NULL_LOW)
        }
        return result
    }

    override fun i(msg: Any?, vararg tag: String): Int {
        var result = 0
        tag.forEach {
            result += Log.v(it, msg?.toString() ?: STR_NULL_LOW)
        }
        return result
    }

    override fun d(msg: Any?, vararg tag: String): Int {
        var result = 0
        tag.forEach {
            result += Log.v(it, msg?.toString() ?: STR_NULL_LOW)
        }
        return result
    }

    override fun e(msg: Any?, vararg tag: String): Int {
        var result = 0
        tag.forEach {
            result += Log.v(it, msg?.toString() ?: STR_NULL_LOW)
        }
        return result
    }

    override fun w(msg: Any?, vararg tag: String): Int {
        var result = 0
        tag.forEach {
            result += Log.v(it, msg?.toString() ?: STR_NULL_LOW)
        }
        return result
    }
}