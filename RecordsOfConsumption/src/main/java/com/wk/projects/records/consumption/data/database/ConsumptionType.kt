package com.wk.projects.records.consumption.data.database

import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 支出类型
 * </pre>
 */
class ConsumptionType constructor(val name: String,val createTime:Long)
    : LitePalSupport() {

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}