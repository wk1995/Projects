package com.wk.cashbook.trade.data

import androidx.annotation.WorkerThread
import com.wk.projects.common.constant.DataBaseConstants
import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants
import org.litepal.LitePal
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


data class TradeCategory(val categoryName: String, val createTime: Long = NumberConstants.number_long_zero,
                         var parentId: Long = NumberConstants.number_long_one_Negative,
                         var note: String = WkStringConstants.STR_EMPTY) : LitePalSupport() {

    companion object {
        const val CATEGORY_NAME = "categoryname"
        const val CREATE_TIME = "createtime"
        const val PARENT_ID = "parentid"
        const val NOTE = "note"


        /**
         * 获取最顶的类别
         * 支出，收入，内部转账
         * */
        @WorkerThread
        fun getRootCategory(): List<TradeCategory> {
            return getSubCategory()
        }

        /**
         * 获取parent 子类
         * */
        @WorkerThread
        fun getSubCategory(parent: TradeCategory? = null): List<TradeCategory> {
            val parentId = parent?.baseObjId ?: NumberConstants.number_long_one_Negative
            return LitePal.where(PARENT_ID + DataBaseConstants.STR_EQUAL_AND_QUESTION_MARK_EN,
                    parentId.toString())
                    .find(TradeCategory::class.java)

        }
    }


    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }


}

