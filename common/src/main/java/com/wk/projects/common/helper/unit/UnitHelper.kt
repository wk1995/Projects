package com.wk.projects.common.helper.unit

import android.content.Context
import com.wk.projects.common.configuration.WkProjects.UNUSED

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/7/13<br/>
 *      desc   :  单位转换 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
@Suppress(UNUSED)
object UnitHelper {

    /**dp转成px*/
    fun dp2px(context: Context, dpValue: Float) = (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()
}