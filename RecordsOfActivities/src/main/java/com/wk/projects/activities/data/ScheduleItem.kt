package com.wk.projects.activities.data

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

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

/**
 * @param itemName      项目名称
 * @param startTime     项目开始的时间
 * @param endTime       项目结束的时间
 * @param note          项目的备注
 * @param parentId      项目所属的类别id
 * @param extraMsg      额外的信息
 *
 * */
data class ScheduleItem(@Column(nullable = false) var itemName: String,
                        @Column(nullable = false) var startTime: Long = 0,
                        @Column(nullable = false) var endTime: Long = 0,
                        @Column(nullable = true) var note: String? = null,
                        @Column(nullable = true) var parentId:Long?=null,
                        @Column(nullable = true) var extraMsg:LitePalSupport?=null)
    : LitePalSupport() {
    companion object {
        const val  SCHEDULE_ITEM_NAME="itemName"
        const val  SCHEDULE_START_TIME="startTime"
        const val  SCHEDULE_END_TIME="endTime"
        const val  SCHEDULE_ITEM_NOTE="note"
        const val  SCHEDULE_PARENT_ID="parentId"
        const val  SCHEDULE_EXTRA_MSG="extraMsg"
    }
    public override  fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }


}
