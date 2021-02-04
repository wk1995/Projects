package com.wk.projects.common.log

/**
 * <pre>
 *      @author : wk <br/>
 *      e-mail : 122642603@qq.com <br/>
 *      time   : 2021/01/07 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 *      desc   : 打印日志接口
 * </pre>
 */
interface IWkLogPrintStrategy {

    fun v(msg:Any?,vararg tag: String):Int

    fun i(msg:Any?,vararg tag: String):Int

    fun d(msg:Any?,vararg tag: String):Int

    fun e(msg:Any?,vararg tag: String):Int

    fun w(msg:Any?,vararg tag: String):Int
}