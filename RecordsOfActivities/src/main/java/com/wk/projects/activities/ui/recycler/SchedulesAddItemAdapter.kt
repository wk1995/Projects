package com.wk.projects.activities.ui.recycler

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.common.communication.IRvClickListener

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
class SchedulesAddItemAdapter(private val itemList: ArrayList<String>,
                              private val rvClickListener:IRvClickListener?=null) : RecyclerView.Adapter<SchedulesAddItemAdapter.SAdapter>() {
    class SAdapter(val v: View, val tvName: TextView) : RecyclerView.ViewHolder(v)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SAdapter {
        val rootView = LayoutInflater.from(parent.context)
                .inflate(R.layout.common_only_text, parent, false)
        val tvName = rootView.findViewById<TextView>(R.id.tvCommon)
        return SAdapter(rootView, tvName)
    }

    override fun onBindViewHolder(holder: SAdapter, position: Int) {
        val itemName=itemList[position]
        holder.run {
            tvName.text=itemName
            v.setOnClickListener{
                val bundle=Bundle()
                bundle.putString(SchedulesBundleKey.SCHEDULE_ITEM_NAME,itemName)
                rvClickListener?.onItemClick(bundle)
            }
        }
    }

    override fun getItemCount() = itemList.size
}