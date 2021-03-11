package com.wk.projects.schedules.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants

/**
 *
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/3/10
 * desc   : 坐标
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 *
 * @param lon 经度
 * @param lat 纬度
 * @param coordinateDesc 坐标描述
 * @param note 备注
 * */
data class Coordinate(val lon: Double = NumberConstants.number_double_one,
  val lat: Double = NumberConstants.number_double_one,
  var coordinateDesc: String = WkStringConstants.STR_EMPTY,
  var note: String = WkStringConstants.STR_EMPTY
)
