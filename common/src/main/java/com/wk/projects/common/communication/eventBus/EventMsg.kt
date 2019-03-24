package com.wk.projects.common.communication.eventBus

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
abstract class EventMsg( flag:Int, any:Any?){
    companion object {
        const val SCHEDULE_ITEM_DIALOG=1
        const val DELETE_ITEM_DIALOG=3
    }
}