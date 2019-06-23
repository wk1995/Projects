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

class ScheduleItem() : LitePalSupport() {

    /**
     * 项目名称
     * */
    @Column(nullable = false)
    var itemName: String? = null

    /**
     * 项目开始的时间
     * */
    @Column(nullable = false)
    var startTime: Long = 0

    /**
     * 项目结束的时间
     * */
    @Column(nullable = false)
    var endTime: Long = 0

    /**
     * 项目的备注
     * */
    @Column(nullable = true)
    var note: String? = null

    @Column(nullable = false)
    var belongActivity: WkActivity? = null

    /**
     * 额外的数据，比如：一段路程，额外的数据有起点与终点
     * */
    @Column(nullable = true)
    var extraData: ArrayList<ActivityExtraData> = ArrayList()

    constructor(itemName: String, startTime: Long ) : this() {
        this.itemName = itemName
        this.startTime = startTime
    }

    constructor(itemName: String, startTime: Long = 0, endTime: Long = 0,
                note: String?, belongActivity: WkActivity? = null) : this() {
        this.itemName = itemName
        this.startTime = startTime
        this.endTime = endTime
        this.note = note
        this.belongActivity = belongActivity
    }

    constructor(itemName: String, startTime: Long = 0, endTime: Long = 0,
                note: String?, belongActivity: WkActivity? = null, extraData: ArrayList<ActivityExtraData>)
            : this() {
        this.itemName = itemName
        this.startTime = startTime
        this.endTime = endTime
        this.note = note
        this.belongActivity = belongActivity
        this.extraData = extraData
    }

    companion object {
        const val SCHEDULE_ITEM_NAME = "itemName"
        const val SCHEDULE_START_TIME = "startTime"
        const val SCHEDULE_END_TIME = "endTime"
        const val SCHEDULE_ITEM_NOTE = "note"
        const val SCHEDULE_PARENT_ID = "parentId"
        const val SCHEDULE_EXTRA_MSG = "extraMsg"

        const val SCHEDULE_NO_PARENT_ID = -1L
    }

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }


}
