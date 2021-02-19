package com.wk.projects.common.log.local

import com.wk.projects.common.log.local.ILocalPrintStrategy

/**
 * <pre>
 *      @author : wk <br/>
 *      e-mail : 122642603@qq.com <br/>
 *      time   : 2021/01/07 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 *      desc   : 使用System.out.println 打印日志
 * </pre>
 */
class SystemPrintStrategy: ILocalPrintStrategy {
    override fun v(msg: Any?, vararg tag: String): Int {
        println("$tag   $msg")
        return 0
    }

    override fun i(msg: Any?, vararg tag: String): Int {
        println("$tag   $msg")
        return 0
    }

    override fun d(msg: Any?, vararg tag: String): Int {
        println("$tag   $msg")
        return 0
    }

    override fun e(msg: Any?, vararg tag: String): Int {
        println("$tag   $msg")
        return 0
    }

    override fun w(msg: Any?, vararg tag: String): Int {
        println("$tag   $msg")
        return 0
    }
}