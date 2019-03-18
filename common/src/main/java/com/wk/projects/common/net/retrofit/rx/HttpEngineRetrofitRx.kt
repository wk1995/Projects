package com.wk.projects.common.net.retrofit.rx

import com.wk.projects.common.net.HttpResultCallBack
import com.wk.projects.common.net.retrofit.HttpEngineRetrofit
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/23
 *      desc   : 使用Retrofit+okHttp+RxJava进行网络请求
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
@Suppress("UNUSED")
class HttpEngineRetrofitRx internal constructor(queryMap: HashMap<String, String>,
                                                private val operation: String?,
                                                private val mCallBack: HttpResultCallBack<Any, Any?>,
                                                mAny: Any?)
    : HttpEngineRetrofit(queryMap, operation, mCallBack, mAny) {
    private var observable: Observable<out Any>? = null

    //异步
    override fun operateAsyHttp() {
        observable =
                when (operation) {
                    QUERY_OPERATION -> appService.postQueryAppsRxJava(operation, queryMap)
                    DOWNLOAD_OPERATION -> appService.postDownLoadAppRxJava(operation, queryMap)
                    else -> null
                }
        observable?.subscribeOn(Schedulers.io())
                ?.subscribe(object : Subscriber<Any>() {
                    override fun onNext(t: Any?) {
                        result(t, mCallBack, this)
                    }

                    override fun onCompleted() {}

                    override fun onError(e: Throwable?) {
                        switchHandler.post { mCallBack.onError(null, e) }

                    }
                })
    }


}