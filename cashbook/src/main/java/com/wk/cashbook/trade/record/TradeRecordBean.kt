package com.wk.cashbook.trade.record

import com.wk.cashbook.trade.record.ITradeRecord
import com.wk.projects.common.constant.WkStringConstants

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/27
 * desc         :
 */


data class TradeRecordBean(val date:Long, val note:String, val amount:Double, val type:String=WkStringConstants.COMMON_PUNCTUATION_SEMICOLON): ITradeRecord