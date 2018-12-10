package com.wk.projects.common

import android.app.Application
import org.litepal.LitePal
import timber.log.Timber

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
abstract class BaseApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        //日志Log
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        LitePal.initialize(this)
    }
}