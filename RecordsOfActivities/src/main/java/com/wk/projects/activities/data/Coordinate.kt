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
 * @param lon  经度， null 表示暂未获取到坐标
 * @param lat  纬度， null 表示暂未获取到坐标
 * @param descList    该点的描述
 *
 * */
class Coordinate (@Column(nullable = true) var lon: Double?=null,
                  @Column(nullable = true) var lat: Double?=null,
                  @Column(nullable = true) var descList: List<String>?)
    : LitePalSupport(){

    public override  fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}