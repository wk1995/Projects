package com.wk.projects.records.consumption.debug

import com.wk.projects.common.BaseApplication
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.records.consumption.R

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/18
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 消费记录
 * </pre>
 */
@Suppress(WkProjects.UNUSED)
class ConsumptionApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        WkProjects.init(this)
                .withModuleName(getString(R.string.modules_name))
                .configure()
    }
}