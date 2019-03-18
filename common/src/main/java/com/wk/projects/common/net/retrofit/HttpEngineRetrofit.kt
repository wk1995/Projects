package com.wk.projects.common.net.retrofit

import com.google.gson.Gson
import com.wk.projects.common.constant.MediaTypeConstant.apk
import com.wk.projects.common.constant.MediaTypeConstant.applicationJson
import com.wk.projects.common.net.HttpEngine
import com.wk.projects.common.net.HttpResult
import com.wk.projects.common.net.HttpResultCallBack
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.Observable
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/23
 *      desc   : 使用Retrofit+okHttp进行网络请求
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
@Suppress("UNUSED")
open class HttpEngineRetrofit internal constructor(queryMap: HashMap<String, String>,
                                              private val operation: String?,
                                              private val mCallBack: HttpResultCallBack<Any, Any?>,
                                                   mAny:Any?)
    : HttpEngine(queryMap,mAny) {
    private var call: Call<out Any>? = null
    private var observable: Observable<out Any>? = null

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(rootUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()

    }
    protected val appService :AppService by lazy {
        retrofit.create(AppService::class.java)
    }


    //异步
    override fun operateAsyHttp() {
        call =
                when (operation) {
                    QUERY_OPERATION -> appService.postQueryApps(operation, queryMap)
                    DOWNLOAD_OPERATION -> appService.postDownLoadApp(operation, queryMap)
                    else -> null
                }

        operateAsy(call, mCallBack)

    }

    //异步操作
    private fun <T> operateAsy(call: Call<T>?, callBack: HttpResultCallBack<Call<T>, Any?>) {
        call?.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                Timber.e("onFailure: $t")
                switchHandler.post {
                    callBack.onError(call, t)
                }
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                val httpResult = response?.body()
                Timber.i("result:  $httpResult")
                result(httpResult, callBack, call)
            }
        })
    }

    protected fun <T, R> result(it: T, callBack: HttpResultCallBack<R, Any>, callBckResult: R?) {
        when (it) {
            is HttpResult -> {
                if (it.isSuccess)
                    callBack.onResponse(callBckResult, it)
                else
                    callBack.onError(callBckResult, Exception(it.content))
            }
            is ResponseBody -> {
                val contentType = it.contentType().toString()
                Timber.d("94 $contentType ${Thread.currentThread()}")
                when (contentType) {
                    applicationJson -> {
                        val httpResult = Gson().fromJson<HttpResult>(it.string(), HttpResult::class.java)
                        switchHandler.post {
                            if (httpResult.isSuccess)
                                callBack.onResponse(callBckResult, httpResult)
                            else
                                callBack.onError(callBckResult, Exception(httpResult.content))
                        }
                    }
                    apk -> streamToFile(it)
                    else ->
                        switchHandler.post {
                            callBack.onError(callBckResult, Exception("类型为$contentType "))
                        }
                }
            }
        }
    }


    override fun cancel() {
        if (call?.isCanceled == false)
            call?.cancel()
    }
}