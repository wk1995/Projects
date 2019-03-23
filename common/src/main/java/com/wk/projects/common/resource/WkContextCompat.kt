package com.wk.projects.common.resource

import android.support.v4.content.ContextCompat
import com.wk.projects.common.configuration.WkProjects

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
object WkContextCompat {

    fun getColor(color: Int) =
            ContextCompat.getColor(WkProjects.getContext(), color)


    fun getString(stringId: Int): String? =
            WkProjects.getContext().getString(stringId)

}