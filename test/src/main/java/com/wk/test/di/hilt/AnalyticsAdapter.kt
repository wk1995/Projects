package com.wk.test.di.hilt

import javax.inject.Inject

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/01
 * desc         :
 */

class AnalyticsAdapter @Inject constructor(
    private val service: IAnalyticsService
){
    fun analyticsMethods(){
        service.analyticsMethods()
    }
}