package com.wk.projects.activities.data.add.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.activities.R

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
class CategoryListAdapter(val data: ArrayList<ActivitiesBean>)
    : BaseQuickAdapter<ActivitiesBean, BaseViewHolder>(R.layout.activities_category_list_item, data) {

    override fun convert(helper: BaseViewHolder?, item: ActivitiesBean?) {
        item?.run {
            helper?.setText(R.id.tvItemName, wkActivity.itemName)
                    ?.addOnClickListener(R.id.tvExpend)
        }
    }
}