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
class Coordinate(): LitePalSupport() {

    /**
     * 经度， null 表示暂未获取到坐标
     * */
    @Column(nullable = true) var lon: Double? = null

    /**
     * 纬度， null 表示暂未获取到坐标
     * */
    @Column(nullable = true) var lat: Double? = null

    /**
     * 该坐标的描述
     * */
    @Column(nullable = true) var descList: List<String> = ArrayList()

    constructor(lon: Double?,lat: Double?):this(){
        this.lat=lat
        this.lon=lon
    }
    constructor(lon: Double?,lat: Double?,descList: List<String>):this(){
        this.lat=lat
        this.lon=lon
        this.descList=descList
    }
    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}