package com.wk.projects.common.ui

import android.widget.Toast
import com.wk.projects.common.configuration.WkProjects

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/7/30<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
@Suppress("unused")
object WkToast {


    @JvmStatic
    fun showToast(content: String) {
        Toast.makeText(WkProjects.getApplication(), content, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showToast(contentResId: Int) {
        Toast.makeText(WkProjects.getApplication(),
                WkProjects.getApplication().getString(contentResId),
                Toast.LENGTH_SHORT).show()
    }
}