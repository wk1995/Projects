package com.wk.iot.ty.module

import androidx.multidex.MultiDex
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.wk.projects.common.BaseApplication
import com.wk.projects.common.configuration.WkProjects


/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/01/27
 * desc         : 与涂鸦智能账号不互通，需要注册
 */


class TuyaApp: BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        WkProjects.init(this)
        TuyaHomeSdk.init(this)
        TuyaHomeSdk.setDebugMode(true)
        MultiDex.install(this)
    }


}