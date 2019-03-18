package com.wk.projects.activities.collapse

import com.wk.projects.common.collapse.BaseCollapseActivity
import com.wk.projects.activities.SchedulesMainActivity

/**
 * Created by wk on 2017/12/20.
 * desc: App闪退后显示的Activity
 */
class SchedulesCollapseActivity : BaseCollapseActivity() {

    override fun reStartActivityClass() = SchedulesMainActivity::class.java
    override fun initWatchActivityClass() = SchedulesWatchActivity::class.java
}