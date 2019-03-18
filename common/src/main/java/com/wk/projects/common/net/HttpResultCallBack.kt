package com.wk.projects.common.net

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/17
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
interface  HttpResultCallBack<in  T,in R>{
     fun onError(request:  T?,t: Throwable?)
     fun onResponse(request: T?, result:R?)
}