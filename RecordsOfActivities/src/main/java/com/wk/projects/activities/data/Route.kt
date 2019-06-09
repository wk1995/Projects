package com.wk.projects.activities.data

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : ${date}<br/>
 *      desc   :  用来记录路程的单独表 <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/

/**
 * @param startCoordinate 起始坐标
 * @param startTime 终点
 * @param endCoordinate 开始时间
 * @param endTime 结束时间
 * @param belongTo 所属的活动，一个活动可能会有多段路程
 * @param note 备注，可以是天气，方式：行走，乘车，骑车。。
 * */
class Route(@Column(nullable = false)var startCoordinate:Coordinate,
            @Column(nullable = false)var startTime:Long,
            @Column(nullable = false)var endCoordinate:Coordinate,
            @Column(nullable = false)var endTime:Long,
            @Column(nullable = false)var belongTo:Long,
            @Column(nullable = false)var note:String?) : LitePalSupport(){
    public override  fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}