package com.wk.projects.common.unit

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/11/16<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 *      @param unit 单位
 * */
enum class WkStorageUnit(unit:String,val level:Int):IUnit<WkStorageUnit,Long> {

    WkStorageB("B",0),
    WkStorageK("K",1),
    WkStorageM("M",2),
    WkStorageG("G",3),
    WkStorageT("T",4);

    val conversionUnit=1024
    override fun conversion(t: Long?, start: WkStorageUnit, end: WkStorageUnit): Long {
        TODO("Not yet implemented")
    }

    override fun getConversionUnit(start: WkStorageUnit?, end: WkStorageUnit?): Long {
        TODO("Not yet implemented")
    }
}