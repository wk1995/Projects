package com.wk.test.debug

import com.wk.projects.common.BaseApplication
import com.wk.projects.common.configuration.WkProjects
import com.wk.test.R
//import com.wk.test_lib.IWk
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/23<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */

@HiltAndroidApp
class TestApp: BaseApplication() {

    @Inject
//    lateinit var service: IWk
    override fun onCreate() {
        super.onCreate()
        WkProjects.init(this)
                .withModuleName(getString(R.string.test_modules_name))
                .configure()
//        service.analyticsMethods()
//        KOOM.init(this)
    }
}