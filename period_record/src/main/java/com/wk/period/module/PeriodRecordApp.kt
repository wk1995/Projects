package com.wk.period.module

import com.wk.period_record.R
import com.wk.projects.common.BaseApplication
import com.wk.projects.common.configuration.WkProjects

/**
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/4/5
 * desc   :
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 */
class PeriodRecordApp : BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        WkProjects.init(this)
                .withModuleName(getString(R.string.module_name))
                .configure()
    }
}