package com.wk.projects.common.net

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/26
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
data class HttpResult(val content:String?,val type:String,val isSuccess:Boolean=true) {
    companion object {
        const val appName="appName"
        const val process="process"
    }
}