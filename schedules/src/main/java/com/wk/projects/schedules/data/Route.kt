package com.wk.projects.schedules.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.common.ui.WkCircleView

/**
 *
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/3/10
 * desc   : 路线
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 *
 * @param startCoordinateId [Coordinate] 起点
 * @param endCoordinateId [Coordinate] 终点
 * @param startTime 起点时间
 * @param endTime 终点时间
 * @param meansOfTransformations 方式
 * @param note 备注
 * @param scheduleId [ScheduleItem] 所属的scheduleItem
 *
 * */
data class Route(
        var startCoordinateId: Long = NumberConstants.number_long_one_Negative,
        var endCoordinateId: Long = NumberConstants.number_long_one_Negative,
        var startTime: Long = NumberConstants.number_long_zero,
        var endTime: Long = NumberConstants.number_long_zero,
        var meansOfTransformations: ArrayList<String> = ArrayList(),
        var note: String = WkStringConstants.STR_EMPTY,
        var scheduleId: Long = NumberConstants.number_long_one_Negative,
)
