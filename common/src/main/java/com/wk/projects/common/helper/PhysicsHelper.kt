package com.wk.projects.common.helper

import android.content.Context
import android.view.WindowManager
import com.wk.projects.common.configuration.WkProjects
import android.util.DisplayMetrics


/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/12
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 关于物理的一些方法：屏幕的长宽、
 * </pre>
 */
class PhysicsHelper private constructor() {

    companion object {
        object PhysicsHelperVH {
            val INSTANCE = PhysicsHelper()
        }

        fun getInstance() = PhysicsHelperVH.INSTANCE
    }


    private val windowManager by lazy { WkProjects.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val display by lazy { windowManager.defaultDisplay }
    private var outMetrics: DisplayMetrics? = null


    fun getScreenWidth(): Int {
        if (outMetrics == null) {
            outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
        }
        return outMetrics?.widthPixels ?: 0
    }

    fun getScreenHeight(): Int {
        if (outMetrics == null) {
            outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
        }
        return outMetrics?.heightPixels ?: 0
    }
}