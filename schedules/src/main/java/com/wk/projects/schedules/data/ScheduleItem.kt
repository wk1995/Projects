package com.wk.projects.schedules.data

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
data class ScheduleItem(@Column(nullable = false) var itemName: String,
                        @Column(nullable = true) var startTime: Long? = null,
                        @Column(nullable = true) var endTime: Long? = null,
                        @Column(nullable = true) var note: String? = null,
                        @Column(nullable = true) var parentId: Long? = null)
    : LitePalSupport() {
    companion object {
        const val  COLUMN_ITEM_NAME="itemName"
        const val  COLUMN_START_TIME="startTime"
        const val  COLUMN_END_TIME="endTime"
        const val  COLUMN_ITEM_NOTE="note"
        const val  COLUMN_PARENT_ID="parentId"
    }
    public override  fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }


}
