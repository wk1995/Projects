package com.wk.projects.records.consumption.data.database

import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 账户类别表，支付宝，银行卡[银行卡1，银行卡2]。。。。
 * </pre>
 */


/**
 * @param name ->账户Name
 * @param amount ->当前账户里面金额
 * @param accountTypeId ->所属账户的ID
 * */

data class AccountType(var name: String, var amount: Long, var accountTypeId: Int = -1)
    : LitePalSupport() {

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}