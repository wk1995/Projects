package com.wk.projects.we.chat.wxapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.wk.projects.we.chat.R

class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wx_entry)
    }

    /**
     * 发送到微信请求的响应结果将回调
     * */
    override fun onResp(p0: BaseResp?) {
    }

    /**
     * 微信发送的请求将回调到该方法
     * */
    override fun onReq(p0: BaseReq?) {
    }
}
