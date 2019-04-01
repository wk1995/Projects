package com.wk.projects.records.consumption.data.database

import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/31
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 交易类别：收入支出，还是内部转账
 * </pre>
 */

/**
 *
 * @param tradeTypeName 名称
 * @param belongTo 收入；支出；内部转账
 * @param parentId 所属的父类型 belong跟父类型是一样的
 * */
data class TradeType(var tradeTypeName: String, var belongTo: Int, var parentId: Int)
    : LitePalSupport() {

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }

    companion object {
        const val INCOME = 1
        const val PAY = 2
        const val INSIDER_DEALING = 3
    }
}