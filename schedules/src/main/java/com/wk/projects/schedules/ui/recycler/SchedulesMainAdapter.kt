package com.wk.projects.schedules.ui.recycler

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.schedules.R
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.date.DateTime.getTime
import java.util.*


/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class SchedulesMainAdapter(val itemList: ArrayList<ScheduleItem>)
    : BaseQuickAdapter<ScheduleItem, BaseViewHolder>(R.layout.schedules_item_list, itemList) {


    override fun convert(helper: BaseViewHolder?, item: ScheduleItem?) {
        item?.run {
            val finish = endTime ?: 0 > startTime ?: 0
            helper?.setText(R.id.tvScheduleItemName, itemName)
                    ?.setTextColor(R.id.tvScheduleItemName,
                            if (finish)//表示现在正在进行,还未结束
                                Color.BLACK
                            else Color.RED
                    )
                    ?.setText(R.id.tvScheduleItemTime,
                            if (finish)
                                getTime((item.endTime ?: 0)-(item.startTime ?: 0))
                            else ""
                    )
                    ?.addOnClickListener(R.id.clScheduleItem)
                    ?.addOnLongClickListener(R.id.clScheduleItem)

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