package com.wk.test.aidl

import android.util.Log
import com.wk.test.BaseTestActivity

import android.webkit.WebView


class AIDLActivity : BaseTestActivity() {

    override fun initLayout()= com.wk.test.R.layout.test_activity_aidl

    override fun initView() {
        testWebViewInitUsedTime();
        testWebViewInitUsedTime();
    }


    private fun testWebViewInitUsedTime() {
        val p = System.currentTimeMillis()
        val mWebView = WebView(this)
        val n = System.currentTimeMillis()

        Log.i("wk", "testWebViewFirstInit use time:" + (n - p))
    }


}
