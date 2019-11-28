package com.wk.projects.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View


/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/11/25<br/>
 *      desc   :   Bitmap create 方法<br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
object BitmapFactory {

    @JvmStatic
    fun viewConversionBitmap(v: View):Bitmap{
        val w = v.width
        val h = v.height
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }
}