package com.wk.projects.common.net

import com.wk.projects.common.net.HttpEngineType.OK_HTTP
import com.wk.projects.common.net.HttpEngineType.RETROFIT
import com.wk.projects.common.net.HttpEngineType.RETROFIT_RX
import com.wk.projects.common.net.okhttp.HttpEngineOkHttp
import com.wk.projects.common.net.retrofit.HttpEngineRetrofit
import com.wk.projects.common.net.retrofit.rx.HttpEngineRetrofitRx
import okhttp3.RequestBody

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/19
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
@Suppress("UNUSED")
class HttpEngineBuilder(private val httpEngineType:String=HttpEngineType.RETROFIT_RX) {

    private val queryMap by lazy { HashMap<String, String>() }
    private var operation: String? = null
    private var url: String? = null
    private var mCallback: HttpResultCallBack<Any, Any?>? = null
    private var body: RequestBody? = null
    private var method: String = "GET"
    private var mAny: Any?=null

    fun withOperation(operation: String): HttpEngineBuilder {
        this.operation = operation
        return this
    }

    fun withUrl(url: String): HttpEngineBuilder {
        this.url = url
        return this
    }

    fun withQuery(key: String, value: String): HttpEngineBuilder {
        queryMap[key] = value
        return this
    }

    fun withQuerys(queryMap: HashMap<String, String>): HttpEngineBuilder {
        this.queryMap.putAll(queryMap)
        return this
    }

    fun withCallBack(mCallback: HttpResultCallBack<Any, Any?>): HttpEngineBuilder {
        this.mCallback = mCallback
        return this
    }

    fun withDownLoad(any: Any?): HttpEngineBuilder {
        mAny=any
        return this
    }

    fun withBody(body: RequestBody?): HttpEngineBuilder {
        this.body = body
        return this
    }

    fun withMethod(method: String): HttpEngineBuilder {
        this.method = method
        return this
    }

    fun builder(): HttpEngine {
        if (mCallback == null) throw Exception("mCallback can not be null")
        return when(httpEngineType){
            OK_HTTP-> HttpEngineOkHttp(operation, url, queryMap, mCallback!!, body, method,mAny)
            RETROFIT-> HttpEngineRetrofit( queryMap,operation, mCallback!!,mAny)
            RETROFIT_RX-> HttpEngineRetrofitRx( queryMap,operation, mCallback!!,mAny)
            else->throw Exception("不支持 $httpEngineType 类型")
        }

    }
}