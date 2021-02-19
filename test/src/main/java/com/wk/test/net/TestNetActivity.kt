package com.wk.test.net

import android.view.View
import com.wk.projects.common.log.WkLogUtil
import com.wk.test.BaseTestActivity
import com.wk.test.R
import kotlinx.android.synthetic.main.test_net_activity.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

import java.util.concurrent.TimeUnit

class TestNetActivity : BaseTestActivity(),View.OnClickListener {


    override fun initLayout()= R.layout.test_net_activity

    override fun initView() {
        btnRequest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnRequest -> {
                //https://github.com/square/okhttp
                val client = OkHttpClient.Builder()
                        .readTimeout(5,TimeUnit.SECONDS).build() //创建OkHttpClient对象
                val request: Request = Request.Builder()
                        .url("https://www.jianshu.com/p/6941ea12c8f3") //请求链接
                        .get() // 可省略，默认GET方法
                        .build() //创建Request对象

                val response: Response = client.newCall(request).execute() //获取Response对象
                WkLogUtil.d("wk",response.body.toString())
            }
        }
    }
}