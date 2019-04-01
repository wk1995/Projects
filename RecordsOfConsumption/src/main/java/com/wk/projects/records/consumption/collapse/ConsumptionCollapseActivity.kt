package com.wk.projects.records.consumption.collapse

import com.wk.projects.common.collapse.BaseCollapseActivity
import com.wk.projects.common.collapse.BaseWatchActivity
import com.wk.projects.records.consumption.ConsumptionMainActivity

class ConsumptionCollapseActivity : BaseCollapseActivity() {

    override fun reStartActivityClass() = ConsumptionMainActivity::class.java
    override fun initWatchActivityClass() = ConsumptionWatchActivity::class.java
}
