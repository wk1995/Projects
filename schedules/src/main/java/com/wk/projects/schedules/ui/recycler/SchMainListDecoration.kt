package com.wk.projects.schedules.ui.recycler

import com.wk.projects.common.ui.recycler.decoration.BaseDecoration

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
class SchMainListDecoration(size:Int,color:Int):BaseDecoration(size,color) {
    init {
        setDividerLookup(DividerLookupImp(size,color))
    }
}