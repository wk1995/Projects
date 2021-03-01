package com.wk.cashbook.trade

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/27
 * desc         :
 */


data class TradeRecordTotal(val date:Long,var allPayAmount:Double,var allIncomeAmount:Double):ITradeRecord