package com.wk.cashbook.trade.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants
import org.litepal.crud.LitePalSupport

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/03/10
 * desc         : 交易记录
 * @param tradeName 交易记录名
 * @param tradeTime 交易时间
 * @param accountId 账户 [TradeAccount]
 * @param categoryId 类别  [TradeCategory]
 * @param flagIds 标签 [TradeFlag]
 * @param amount 金额
 * @param tradeNote 备注
 * @param receiveAccountId 交易对象 [TradeAccount.accountName]
 * @param relationTradeId 关联交易 比如还款
 */


data class TradeRecode(var tradeName: String = WkStringConstants.STR_EMPTY,
                       var tradeTime: Long = NumberConstants.number_long_zero,
                       var accountId: Long = NumberConstants.number_long_one_Negative,
                       var categoryId: Long = NumberConstants.number_long_one_Negative,
                       val flagIds: ArrayList<Long> = ArrayList(),
                       var amount: Double = NumberConstants.number_double_zero,
                       var tradeNote: String = WkStringConstants.STR_EMPTY,
                       var receiveAccountId: Long = NumberConstants.number_long_one_Negative,
                       var relationTradeId: Long = NumberConstants.number_long_one_Negative
) : LitePalSupport() {

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }

}