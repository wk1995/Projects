package com.wk.projects.records.consumption.data.database

import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/31
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 支入支出记录
 * </pre>
 */
data class ConsumptionDetail(var tradeAmount: Long,
                             var tradeTypeId: Int,
                             var fromId: Int?=null,
                             var toId: Int?=null,
                             var tradeTime: Long,
                             var note: String? = null)
    : LitePalSupport() {
    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}