package com.wk.projects.common.ui.notification

import android.widget.Toast
import com.wk.projects.common.configuration.WkProjects

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/23
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 封装自定义Toast
 * </pre>
 */
object ToastUtil {
    const val LENGTH_LONG = -1
    const val LENGTH_SHORT = -2
    fun show(msg: String?, duration: Int=LENGTH_SHORT) {
        Toast.makeText(WkProjects.getContext(), msg,
                if (duration == LENGTH_LONG)
                    Toast.LENGTH_LONG
                else
                    Toast.LENGTH_SHORT
        ).show()
    }
}