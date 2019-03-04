package com.wk.projects.common.communication

import android.os.Bundle

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/25
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
interface IRvClickListener {
    fun onItemClick(bundle: Bundle?,vararg any: Any?)

    fun onItemLongClick(bundle: Bundle?,vararg any: Any?)
}