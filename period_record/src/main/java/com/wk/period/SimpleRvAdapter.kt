package com.wk.period

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wk.period_record.R

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2021/4/5
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class SimpleRvAdapter(private val items: MutableList<String> = ArrayList()) :
        RecyclerView.Adapter<SimpleRvAdapter.SimpleVH>() {

    class SimpleVH(val rootView: View) : RecyclerView.ViewHolder(rootView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleVH {
        val rootView=LayoutInflater.from(parent.context)
                .inflate(R.layout.common_only_text,parent,false)
        return SimpleVH(rootView)
    }

    override fun onBindViewHolder(holder: SimpleVH, position: Int) {
        holder.apply {
            if(rootView is TextView){
                rootView.text=items[position]
            }
        }
    }

    override fun getItemCount()=items.size
}