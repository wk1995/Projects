package com.wk.projects.common.net.okhttp.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/18
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain?.request() ?: throw Exception("request is null")
        val t1 = System.nanoTime()
        val requestLog = StringBuilder()
        requestLog.append("request: ")
        requestLog.append("\r\n")
        requestLog.append("request url: ")
        requestLog.append(request.url())
        requestLog.append("\r\n")
        requestLog.append(request.headers().toString())
        requestLog.append("\r\n")
        requestLog.append("chain.connection:  ")
        requestLog.append("\r\n")
        requestLog.append(chain.connection().toString())
        requestLog.append("\r\n")
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        requestLog.append("response: ")
        requestLog.append("\r\n")
        requestLog.append(response.request().url())
        requestLog.append("\r\n")
        requestLog.append(response.headers().toString())
        requestLog.append("\r\n")
        requestLog.append("time: ")
        requestLog.append(t2 - t1)
        Timber.i(requestLog.toString())
        return response

    }
}