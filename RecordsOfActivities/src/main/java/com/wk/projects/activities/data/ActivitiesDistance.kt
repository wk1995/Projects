package com.wk.projects.activities.data

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/2
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 活动中的路程项
 * </pre>
 */

/**
 * @param startLon  起点坐标经度
 * @param startLat  起点坐标纬度
 * @param endLon    终点坐标经度
 * @param endLat    终点点坐标纬度
 *
 * */
class ActivitiesDistance (@Column(nullable = false) var startLon: Double,
                          @Column(nullable = false) var startLat: Double,
                          @Column(nullable = false) var endLon: Double,
                          @Column(nullable = false) var endLat: Double)
    : LitePalSupport(){

    public override  fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}