package com.wk.projects.schedules.ui.recycler

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey.SCHEDULE_ITEM_ID
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.data.ScheduleItemInfoActivity
import com.wk.projects.schedules.data.ScheduleItemInfoDialog
import com.wk.projects.schedules.update.DeleteScheduleItemDialog
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
class SchedulesMainAdapter(val itemList: ArrayList<ScheduleItem>) : RecyclerView.Adapter<SchedulesMainAdapter.SAdapter>() {
    private var context: Context? = null

    class SAdapter(val v: View, val tvName: TextView) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SAdapter {
        context = parent.context
        val rootView = LayoutInflater.from(parent.context)
                .inflate(R.layout.common_only_text, parent, false)
        val tvName = rootView.findViewById<TextView>(R.id.tvCommon)
        return SAdapter(rootView, tvName)
    }

    override fun onBindViewHolder(holder: SAdapter, position: Int) {
        itemList[position].run {
            holder.tvName.text = itemName
            holder.tvName.setTextColor(
                    if (endTime ?: 0 <= startTime ?: 0)//表示现在正在进行,还未结束
                        Color.RED
                    else Color.BLACK
            )

            holder.v.setOnClickListener {
                ARouter.getInstance()
                        .build(ARoutePath.ScheduleItemInfoActivity)
                        .withLong(SCHEDULE_ITEM_ID, baseObjId)
                        .navigation()
//                ScheduleItemInfoDialog.create(bundle).show(context as BaseProjectsActivity)
            }
            holder.v.setOnLongClickListener {
                //修改项目开始时间或者删除项目
                val bundle = Bundle()
                bundle.putLong(SCHEDULE_ITEM_ID, baseObjId)
                bundle.putInt(BundleKey.LIST_POSITION, position)
                DeleteScheduleItemDialog.create(bundle).show((context as BaseProjectsActivity).supportFragmentManager)
                true
            }
        }
    }

    override fun getItemCount() = itemList.size

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