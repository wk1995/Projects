package com.wk.projects.common.net.retrofit

import com.wk.projects.common.net.HttpResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import rx.Observable

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/23
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
interface AppService {

    @POST("{operate}")
    fun postQueryApps(@Path("operate") operate:String, @QueryMap queryMap:Map<String,String>): Call<HttpResult>

    @POST("{operate}")
    fun postDownLoadApp(@Path("operate") operate:String, @QueryMap queryMap:Map<String,String>): Call<ResponseBody>

    @POST("{operate}")
    fun postDownLoadAppRxJava(@Path("operate") operate:String, @QueryMap queryMap:Map<String,String>): Observable<ResponseBody>

    @POST("{operate}")
    fun postQueryAppsRxJava(@Path("operate") operate:String, @QueryMap queryMap:Map<String,String>): Observable<HttpResult>



}