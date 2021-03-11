package com.wk.projects.schedules.data

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.wk.projects.common.constant.NumberConstants.*
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.schedules.ui.recycler.SchedulesMainAdapter.Companion.ITEM_TYPE_SCHEDULE
import com.wk.projects.schedules.ui.recycler.SchedulesMainAdapter.Companion.ITEM_TYPE_TIME
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * author : wk
 * e-mail : 122642603@qq.com
 * time   : 2018/11/24
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 * desc   :
 * @param categoryId 类别id
 * @param itemName  项目的名称
 * @param note 备注
 * @param endTime 项目的结束时间
 * @param categoryId  类别
 * @param startTime 项目的起始时间
 * @param flags [ScheduleFlag] 标签
 */
data class ScheduleItem(@Column(nullable = false) var itemName: String = WkStringConstants.STR_EMPTY,
                        @Column(nullable = true) var startTime: Long = number_long_zero,
                        @Column(nullable = true) var endTime: Long = number_long_zero,
                        @Column(nullable = true) var note: String = WkStringConstants.STR_EMPTY,
                        var flags: ArrayList<Long> = ArrayList(),
                        @Column(nullable = true) var categoryId: Long = number_long_one_Negative)
    : LitePalSupport(), MultiItemEntity {
    companion object {
        const val COLUMN_ITEM_NAME = "itemName"
        const val COLUMN_START_TIME = "startTime"
        const val COLUMN_END_TIME = "endTime"
        const val COLUMN_ITEM_NOTE = "note"
        const val COLUMN_CATEGORY_ID = "categoryId"
    }

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }

    override fun getItemType() = if (itemName.isEmpty()) {
        ITEM_TYPE_TIME
    } else {
        ITEM_TYPE_SCHEDULE
    }
}
