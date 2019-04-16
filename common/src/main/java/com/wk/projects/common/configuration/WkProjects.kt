package com.wk.projects.common.configuration

import android.content.Context
import com.wk.projects.common.BaseApplication
import com.wk.projects.common.configuration.ConfigureKey.CONTEXT
import com.wk.projects.common.configuration.WkProjects.UNUSED

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/23
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
@Suppress(UNUSED)
object WkProjects {
    const val UNUSED = "unused"
    @JvmStatic
    fun init(application: BaseApplication): WkConfiguration {
        getInfo().put(CONTEXT, application)
        return WkConfiguration.getInstance()
    }

    @JvmStatic
    private fun getInfo() = WkConfiguration.getInstance().info

    fun getContext(): Context = getInfo()[CONTEXT] as Context

    @JvmStatic
    fun <T> getConfiguration(key: Int) =
            getInfo()[key] as? T

    @JvmStatic
    fun setConfiguration(key: Int, any: Any) = getInfo().put(key, any)

}