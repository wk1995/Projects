package com.wk.projects.common.helper.file

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 文件状态的回调接口
 * </pre>
 */
interface IFileStatusListener {

    /**
     * 主要用于异步读取文件内容的回调接口
     * */
    interface IReadStatusListener{
        fun readResult(result:String?)
        fun readFinish()
        fun readError(e:Throwable?)
    }
}