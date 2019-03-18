package com.wk.projects.common.net.okhttp

import com.google.gson.Gson
import com.wk.projects.common.communication.constant.MediaTypeConstant.apk
import com.wk.projects.common.communication.constant.MediaTypeConstant.applicationJson
import com.wk.projects.common.net.HttpEngine
import com.wk.projects.common.net.HttpResult
import com.wk.projects.common.net.HttpResultCallBack
import okhttp3.*
import timber.log.Timber
import java.io.IOException

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/17
 *      desc   : 单纯的使用okHttp进行网络请求
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
@Suppress("UNUSED")
class HttpEngineOkHttp internal constructor(private val operation: String?,
                                            private val url: String?,
                                            queryMap: HashMap<String, String>,
                                            private val mCallback: HttpResultCallBack<Any, Any?>,
                                            private var body: RequestBody?,
                                            private val method: String = "GET",
                                            mAny:Any?) : HttpEngine(queryMap,mAny) {

    private var call: Call? = null
    override fun operateAsyHttp() {
        if (this.queryMap.isNotEmpty() && body == null) {
            var formBodyBuilder = FormBody.Builder()
            for ((k, v) in this.queryMap)
                formBodyBuilder = formBodyBuilder.add(k, v)
            body = formBodyBuilder.build()
        }
        val request = Request.Builder()
                .url(url ?: rootUrl+(operation
                        ?: throw Exception("OkHttp url & operation 至少一个不能为null")))
                .method(method, body).build()
        call = okHttpClient.newCall(request)
        dealAsyResult(call ?: return, mCallback)
    }

    private fun dealAsyResult(call: Call, callBack: HttpResultCallBack<Any, Any>) {
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Timber.i(e?.message ?: "null")
                switchHandler.post {
                    callBack.onError(call?.request(), e)
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                val responseBody = response?.body()
                val responseBodyContentType = responseBody?.contentType()
                val mediaType = responseBodyContentType?.toString()
                val type = responseBodyContentType?.type()
                Timber.i(String.format("类型type:%s\r\n 子类型subtype %s \r\n mediaType: %s"
                        , type, responseBodyContentType?.subtype(), responseBodyContentType.toString()))
                when (mediaType?.toLowerCase()) {
                    applicationJson -> {
                        val resultString = responseBody.string()
                        Timber.d(" 121 $resultString")
                        val httpResult = Gson().fromJson<HttpResult>(resultString, HttpResult::class.java)
                        switchHandler.post {
                            if (httpResult.isSuccess)
                                callBack.onResponse(call?.request(), httpResult)
                            else
                                callBack.onError(call?.request(), Exception(httpResult.content))
                        }
                    }
                    apk -> {
                        streamToFile(responseBody)
                    }
                    else ->
                        switchHandler.post {
                            callBack.onError(call?.request(), Exception("类型为${mediaType ?: "不确定"}"))
                        }
                }

            }
        })
    }

    override fun cancel() {
        if (call?.isCanceled == false)
            call?.cancel()

    }
}