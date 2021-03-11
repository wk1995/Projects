package com.wk.cashbook.trade.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants
import org.litepal.crud.LitePalSupport

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/03/10
 * desc         :账单类别
 * @param categoryName  类别名称
 * @param createTime 创建时间
 * @param parentId 父类
 * @param note 备注
 */


data class TradeCategory(val categoryName: String, val createTime: Long,
                         var parentId: Long = NumberConstants.number_long_one_Negative,
                         var note: String = WkStringConstants.STR_EMPTY) : LitePalSupport() {

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}

