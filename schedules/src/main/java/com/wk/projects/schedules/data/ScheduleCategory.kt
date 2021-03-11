package com.wk.projects.schedules.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 *
 * author : wk
 * e-mail : 122642603@qq.com
 * time   : 2019/2/25
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 * desc   : schedule 类别
 * @param itemName 类别名
 * @param createTime 创建时间
 * @param parentId 父类
 * @param note 备注
 *
 */
data class ScheduleCategory(@Column(nullable = false) var itemName: String,
                            @Column(nullable = false) var createTime: Long = NumberConstants.number_long_zero,
                            @Column(nullable = false) var parentId: Long = NumberConstants.number_long_one_Negative,
                            @Column(nullable = false) var note: String = WkStringConstants.STR_EMPTY
) : LitePalSupport() {

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}