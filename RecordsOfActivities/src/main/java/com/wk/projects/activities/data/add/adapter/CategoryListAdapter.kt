package com.wk.projects.activities.data.add.adapter

import android.widget.LinearLayout
import android.widget.TextView
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
class CategoryListAdapter
    : BaseQuickAdapter<ActivitiesBean, BaseViewHolder>(R.layout.activities_category_list_item) {

    override fun convert(helper: BaseViewHolder?, item: ActivitiesBean?) {
        item?.run {
            val tvExpend = helper?.getView<TextView>(R.id.tvExpend) ?: return
            val layoutParams = tvExpend.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(wkLevel * 60, 0, 0, 0)
            tvExpend.layoutParams=layoutParams
            if (wkActivity == null) {
                //说明是增加的
                helper.setText(R.id.tvItemName, "增加")
                        ?.setVisible(R.id.tvExpend, false)

            } else
                helper.setText(R.id.tvItemName, wkActivity.itemName)
                        ?.setVisible(R.id.tvExpend, true)
                        ?.addOnClickListener(R.id.tvExpend)

            tvExpend.text = if (isExpanded) "-" else "+"

        }
    }
}