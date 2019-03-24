package com.wk.projects.activities.communication

import com.wk.projects.common.communication.eventBus.EventMsg

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
data class ActivitiesMsg(val flag: Int, val any: Any?) : EventMsg(flag, any)