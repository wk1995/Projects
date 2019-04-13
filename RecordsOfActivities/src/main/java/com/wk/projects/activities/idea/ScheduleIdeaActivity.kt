package com.wk.projects.activities.idea

import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.configuration.ConfigureKey
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.helper.file.IFileStatusListener
import com.wk.projects.common.idea.IdeaActivity


@Route(path = ARoutePath.ScheduleIdeaActivity)
class ScheduleIdeaActivity : IdeaActivity(), IFileStatusListener.IReadStatusListener {

    override fun getModuleName() = WkProjects.getConfiguration<String>(ConfigureKey.MODULE_NAME)
            ?: throw Exception("未配置moduleName")

}
