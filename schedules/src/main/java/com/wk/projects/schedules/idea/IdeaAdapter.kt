package com.wk.projects.schedules.idea

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/17<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
class IdeaAdapter(private var beans: MutableList<ScheduleIdeaBean>? = null) : androidx.recyclerview.widget.RecyclerView.Adapter<IdeaAdapter.IdeaViewHolder>() {
    class IdeaViewHolder(val v: View, val textView: TextView) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val inflate = LayoutInflater.from(p0.context).inflate(android.R.layout.simple_list_item_1, p0, false)
        val text = inflate.findViewById<TextView>(android.R.id.text1)
        return IdeaViewHolder(inflate, text)
    }

    override fun getItemCount() = beans?.size ?: 0

    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        val scheduleIdeaBeans=beans?:return
        val bean = scheduleIdeaBeans[p1]
        p0.apply {
            textView.text = (bean.ideaContent)
        }
    }

    fun setScheduleIdeaBeans(beans: List<ScheduleIdeaBean>){
        if(this.beans==null){
            this.beans=ArrayList()
        }
        this.beans?.clear()
        this.beans?.addAll(beans)
        notifyDataSetChanged()
    }

    fun addScheduleIdeaBean(bean:ScheduleIdeaBean,index:Int=itemCount){
        if(beans==null){
            beans=ArrayList()
        }
        beans?.add(index,bean)
        notifyItemRangeChanged(index,itemCount-index)
    }

}