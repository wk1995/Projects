package com.wk.projects.activities.data.add.adapter

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.wk.projects.activities.data.WkActivity
import com.wk.projects.activities.data.WkActivity.Companion.NO_PARENT

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class ActivitiesBean(val wkActivity: WkActivity,val wkLevel:Int) : AbstractExpandableItem<ActivitiesBean>() {

     override fun getLevel() =wkLevel

}