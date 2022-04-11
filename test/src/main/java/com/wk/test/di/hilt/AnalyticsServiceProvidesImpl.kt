package com.wk.test.di.hilt

import com.wk.projects.common.log.WkLog
import javax.inject.Inject

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/01
 * desc         :
 */

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
class AnalyticsServiceProvidesImpl @Inject constructor(

) : IAnalyticsService {
    override fun analyticsMethods() {
        WkLog.d("AnalyticsServiceImpl  analyticsMethods")
    }
}