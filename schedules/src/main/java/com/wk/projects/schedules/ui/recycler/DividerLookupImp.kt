package com.wk.projects.schedules.ui.recycler

import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration

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
class DividerLookupImp(val size:Int,val color:Int): DividerItemDecoration.DividerLookup {
    override fun getHorizontalDivider(position: Int):Divider
            = Divider.Builder()
            .size(size)
            .color(color)
            .margin(10,10)
            .build()

    override fun getVerticalDivider(position: Int):Divider= Divider.Builder().build()
}