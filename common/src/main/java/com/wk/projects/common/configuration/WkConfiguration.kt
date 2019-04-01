package com.wk.projects.common.configuration

import android.content.Context
import android.util.SparseArray
import com.wk.projects.common.collapse.CrashHandler
import com.wk.projects.common.configuration.ConfigureKey.CONTEXT
import com.wk.projects.common.configuration.ConfigureKey.MODULE_NAME
import timber.log.Timber

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
class WkConfiguration private constructor() {

    internal val info by lazy { SparseArray<Any?>() }

    companion object {
        object WkConfigurationViewHolder {
            @JvmField
            val INSTANCE = WkConfiguration()
        }

        @JvmStatic
        fun getInstance(): WkConfiguration = WkConfigurationViewHolder.INSTANCE
    }

    fun withModuleName(moduleName: String): WkConfiguration {
        info.put(MODULE_NAME, moduleName)
        return this
    }

    fun configure() {
        val context = info[CONTEXT] as? Context ?: throw Exception("context must not be null")
        val packageName = context.packageName
        //全限定名
        Timber.d("packageName:  $packageName")
        if (packageName != null)
            CrashHandler.init(context, packageName)
    }


}