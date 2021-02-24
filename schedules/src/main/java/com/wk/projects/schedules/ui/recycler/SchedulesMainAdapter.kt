package com.wk.projects.schedules.ui.recycler

import android.graphics.Color
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.schedules.R
import com.wk.projects.schedules.data.ScheduleItem
import java.util.*


/**
 *
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 */
class SchedulesMainAdapter(val itemList: ArrayList<ScheduleItem>)
    : BaseQuickAdapter<ScheduleItem, BaseViewHolder>(R.layout.schedules_item_list, itemList) {

    override fun convert(helper: BaseViewHolder?, item: ScheduleItem?) {
        item?.run {
            //表示现在正在进行,还未结束
            val finish = endTime ?: 0 > startTime ?: 0
            helper?.setText(R.id.tvScheduleItemName, itemName)
                    ?.setTextColor(R.id.tvScheduleItemName,
                            if (finish) {
                                Color.BLACK
                            } else {
                                Color.RED
                            }
                    )
                    ?.setText(R.id.tvCompleteStatus,
                            if (finish) {
//                                getTime((item.endTime ?: 0) - (item.startTime ?: 0))
                                R.string.common_str_has_complete
                            } else {
                                R.string.common_str_complete
                            }
                    )?.addOnClickListener(R.id.clScheduleItem)
                    ?.addOnClickListener(R.id.tvCompleteStatus)
                    ?.addOnLongClickListener(R.id.clScheduleItem)
            val tvCompleteStatus = helper?.getView<Button>(R.id.tvCompleteStatus)
            tvCompleteStatus?.setBackgroundResource(if (finish) {
                -1
            } else {
                R.drawable.common_bg_xml_shape_r25_solid_white
            })

        }
    }

    fun addItem(mScheduleItem: ScheduleItem) {
        itemList.add(mScheduleItem)
        notifyItemChanged(itemCount - 1)
    }

    fun addItems(list: MutableCollection<ScheduleItem>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }
}