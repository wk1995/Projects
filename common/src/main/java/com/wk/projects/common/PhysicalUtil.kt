package com.wk.projects.common

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.wk.projects.common.constant.WkSuppressConstants.UNUSED



/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/25<br/>
 *      desc   : 物理工具库  <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
@Suppress(UNUSED)
object PhysicalUtil {

    /**
     * 获取屏幕高度
     * @return width and height  unit px
     * */
    @JvmStatic
    fun getScreenWidthAndHeight(context: Context):List<Int>{
        val windowManager=context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        val metric=DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metric)
        return listOf(metric.widthPixels,metric.heightPixels)
    }

    /**
     * 获取状态栏高度
     * @return width and height  unit px
     * */
    @JvmStatic
    fun getStatusBarHeight(context: Context):Int{
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}