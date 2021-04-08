package com.wk.period.data

import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.WkStringConstants
import org.litepal.crud.LitePalSupport

/**
 *
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/4/5
 * desc   : 经期
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 * @param startTime 开始来月经的日子
 * @param endTime 月经结束的日子
 * @param note 备注
 * */
data class Period(var startTime:Long=NumberConstants.number_long_zero,
                  var endTime:Long=NumberConstants.number_long_zero,
                  var note:String=WkStringConstants.STR_EMPTY):LitePalSupport()