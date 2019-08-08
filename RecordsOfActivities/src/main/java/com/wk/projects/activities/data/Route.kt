package com.wk.projects.activities.data

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : ${date}<br/>
 *      coordinateDesc   : 路线，用来记录路程的单独表 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/

class Route() : LitePalSupport(){

    /**
     * 起始坐标
     * */
    @Column(nullable = true)
     var startCoordinateId: Long=-1


    /**
     * 开始时间
     * */
    @Column(nullable = false)
    var startTime: Long = 0

    /**
     * 终点
     * */
    @Column(nullable = true)
    var endCoordinateId: Long = -1

    /**
     * 结束时间
     * */
    @Column(nullable = true)
    var endTime: Long = 0

    /**
     * 备注，可以是天气，方式：行走，乘车，骑车。。
     * */
    @Column(nullable = true)
    var note: String? = null

    var belongScheduleItem: ScheduleItem? = null

    constructor(startCoordinateId: Long, startTime: Long, endCoordinateId: Long, endTime: Long, note: String?, belongScheduleItem: ScheduleItem?)
            : this() {
        this.startCoordinateId = startCoordinateId
        this.startTime = startTime
        this.endCoordinateId = endCoordinateId
        this.endTime = endTime
        this.note = note
        this.belongScheduleItem = belongScheduleItem
    }

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }

    override fun toString(): String {
        return "Route(startCoordinateId=$startCoordinateId, startTime=$startTime, endCoordinateId=$endCoordinateId, " +
                "endTime=$endTime, note=$note)"
    }


}