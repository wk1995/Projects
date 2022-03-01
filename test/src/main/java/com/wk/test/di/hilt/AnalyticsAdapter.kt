package com.wk.test.di.hilt

import com.wk.projects.common.log.WkLog
import javax.inject.Inject

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/01
 * desc         :
 */

class AnalyticsAdapter @Inject constructor(
    private val service: IAnalyticsService
) {
    fun analyticsMethods() {
        WkLog.d(" AnalyticsAdapter  analyticsMethods")
        service.analyticsMethods()
    }
}