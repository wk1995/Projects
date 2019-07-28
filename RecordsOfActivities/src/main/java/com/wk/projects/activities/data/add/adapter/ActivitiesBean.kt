package com.wk.projects.activities.data.add.adapter

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.wk.projects.activities.data.WkActivity

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   : 类别Recycler的适配器中的Bean
 * </pre>
 */
class ActivitiesBean(val wkActivity: WkActivity?, val wkLevel: Int, val parentBean: ActivitiesBean? = null)
    : AbstractExpandableItem<ActivitiesBean>() {

    override fun getLevel() = wkLevel

}