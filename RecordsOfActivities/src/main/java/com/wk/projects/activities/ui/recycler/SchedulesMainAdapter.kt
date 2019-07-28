package com.wk.projects.activities.ui.recycler

import android.graphics.Color
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.activities.R
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.common.date.DateTime.getTime
import java.util.*


/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   :
 * </pre>
 */
class SchedulesMainAdapter(itemList: ArrayList<ScheduleItem>)
    : BaseItemDraggableAdapter<ScheduleItem, BaseViewHolder>(R.layout.schedules_item_list, itemList) {


    override fun convert(helper: BaseViewHolder?, item: ScheduleItem?) {
        item?.run {
            val finish = endTime > startTime
            helper?.setText(R.id.tvScheduleItemName, itemName)
                    ?.setTextColor(R.id.tvScheduleItemName,
                            if (finish)//表示现在正在进行,还未结束
                                Color.BLACK
                            else Color.RED
                    )
                    ?.setText(R.id.tvScheduleItemTime,
                            if (finish)
                                getTime((item.endTime) - (item.startTime))
                            else ""
                    )
                    ?.addOnLongClickListener(R.id.clScheduleItem)

        }
    }

    override fun getItemCount(): Int {
        return data.size + footerLayoutCount + headerLayoutCount
    }
}