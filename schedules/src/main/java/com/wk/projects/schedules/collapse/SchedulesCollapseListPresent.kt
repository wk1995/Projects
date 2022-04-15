package com.wk.projects.schedules.collapse

import com.wk.projects.common.BaseProjectsPresent
import com.wk.projects.common.collapse.CrashHandler
import java.io.File

class SchedulesCollapseListPresent(
    private val mSchedulesCollapseListActivity: SchedulesCollapseListActivity
) : BaseProjectsPresent() {

    fun initData() {
        val crashHandler = CrashHandler.Companion.CrashHandlerFactory.getCrashHandler()
        val crashLogPath = crashHandler.getLogPath()
        val rootFile = File(crashLogPath)
        val crashFiles = rootFile.listFiles()?.asList() ?: ArrayList()
        mSchedulesCollapseListActivity.updateData(crashFiles)
    }
}