package com.wk.projects.schedules.ui.recycler

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.schedules.R
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.date.DateTime.getDateString
import com.wk.projects.schedules.date.DateTime.getTime
import java.text.SimpleDateFormat
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
    : BaseMultiItemQuickAdapter<ScheduleItem, BaseViewHolder>(itemList) {
    private val defaultSimpleDateFormat by lazy {
        SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault())
    }

    companion object {
        const val ITEM_TYPE_SCHEDULE = 0
        const val ITEM_TYPE_TIME = 1
    }

    init {
        addItemType(ITEM_TYPE_SCHEDULE, R.layout.schedules_item_list)
        addItemType(ITEM_TYPE_TIME, R.layout.schedules_item_list_time)
    }


    override fun convert(helper: BaseViewHolder?, item: ScheduleItem?) {
        item?.run {
            val tvCompleteStatus = helper?.getView<Button>(R.id.tvCompleteStatus)
            //表示只是时刻，不是具体的项目
            if (itemName.isEmpty()) {
                helper?.setText(R.id.tvScheduleItemName, getDateString(startTime ?: 0,defaultSimpleDateFormat))

            } else {
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
                        ?.setTextColor(R.id.tvCompleteStatus,
                                if (finish) {
                                    Color.BLACK
                                } else {
                                    Color.RED
                                }
                        )
                        ?.setVisible(R.id.tvCompleteStatus, true)
                        ?.setVisible(R.id.tvScheduleItemTime, true)
                        ?.setText(R.id.tvCompleteStatus,
                                if (finish) {
                                    R.string.common_str_has_complete
                                } else {
                                    R.string.common_str_complete
                                }
                        )
                        ?.setText(R.id.tvScheduleItemTime,
                                if (finish) {
                                    getTime((item.endTime ?: 0) - (item.startTime ?: 0))
                                } else {
                                    getTime(System.currentTimeMillis() - (item.startTime ?: 0))
                                }
                        )

                tvCompleteStatus?.background=(if (finish) {
                    null
                } else {
                    mContext.getDrawable(R.drawable.common_bg_xml_shape_r25_solid_white)
                })
            }
            helper ?.addOnClickListener(R.id.clScheduleItem)
                    ?.addOnClickListener(R.id.tvCompleteStatus)
                    ?.addOnLongClickListener(R.id.clScheduleItem)
        }


    }

    fun addItem(mScheduleItem: ScheduleItem) {
        itemList.add(mScheduleItem)
        notifyItemChanged(itemCount - 1)
    }
    
    fun replaceItem(scheduleItem: ScheduleItem,index:Int){
        if(index>=itemCount){
            replaceItemNoCheck(scheduleItem,itemCount-1)
            return
        }
        if(index<=0){
            replaceItemNoCheck(scheduleItem,0)
            return
        }
        replaceItemNoCheck(scheduleItem,index)
    }

    private fun replaceItemNoCheck(scheduleItem: ScheduleItem,index:Int){
        remove(index)
        itemList.add(index,scheduleItem)
        notifyItemChanged(index)
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