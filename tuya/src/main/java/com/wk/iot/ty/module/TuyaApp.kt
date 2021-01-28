package com.wk.iot.ty.module

import android.app.Application
import android.support.multidex.MultiDex
//import android.support.multidex.MultiDex
import com.tuya.smart.home.sdk.TuyaHomeSdk
//import com.wk.projects.common.BaseApplication

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/01/27
 * desc         :
 */


class TuyaApp:Application (){
    override fun onCreate() {
        super.onCreate()
        TuyaHomeSdk.init(this)
        TuyaHomeSdk.setDebugMode(true)
        MultiDex.install(this);
    }


}