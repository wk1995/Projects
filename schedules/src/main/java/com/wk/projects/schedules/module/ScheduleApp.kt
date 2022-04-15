package com.wk.projects.schedules.module

import com.wk.projects.common.BaseApplication
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.schedules.R
import org.litepal.LitePal
import org.litepal.tablemanager.callback.DatabaseListener

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/22
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class ScheduleApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        WkProjects.init(this)
                .withModuleName(getString(R.string.schedule_name))
                .configure()
        LitePal.registerDatabaseListener(object:DatabaseListener{
            override fun onCreate() {

            }

            override fun onUpgrade(oldVersion: Int, newVersion: Int) {
                if(oldVersion<=4){
                    
                }
            }
        })
    }
}