package com.wk.projects.common.resource

import android.content.Context
import androidx.core.content.ContextCompat

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

    fun getColor(context: Context, color: Int) {
        ContextCompat.getColor(context, color)
    }
}