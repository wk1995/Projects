package com.wk.projects.common.ui.recycler.listener

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/03/26
 * desc         :
 */


interface IRvItemOnClickListener {
    fun itemClick(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>, position: Int, view: View)
}