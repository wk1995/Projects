package com.wk.projects.activities.communication.constant

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/2/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   : >0
 * </pre>
 */
object RequestCode {

    const val ActivitiesMainFragment_QUERY_INFO=1

    /**修改项目名称*/
    const val RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName=2
    /**修改类别名称*/
    const val RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName=3

    /**新增坐标desc*/
    const val RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination=6
    const val RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE=7


    //改变WkActivity的parentId
    const val ActivitiesInfoFragment_CHANGE_PARENTID=5
    /**新增活动*/
    const val ActivitiesMainFragment_ADD_ACTIVITIES=4
}