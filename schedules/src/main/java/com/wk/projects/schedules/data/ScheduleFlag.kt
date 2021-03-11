package com.wk.projects.schedules.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants

/**
 *
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/3/10
 * desc   : 标签
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 *
 * @param name 标签的名称
 * @param createTime 标签的创建时间
 * @param note 备注
 * */
data class ScheduleFlag(val name:String,
    val createTime:Long=NumberConstants.number_long_zero,
    val note:String=WkStringConstants.STR_EMPTY)
