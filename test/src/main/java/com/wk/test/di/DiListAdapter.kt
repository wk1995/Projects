package com.wk.test.di

import android.view.View
import android.view.ViewGroup
import com.wk.projects.common.R
import com.wk.projects.common.ui.recycler.BaseRecyclerViewAdapter
import com.wk.projects.common.ui.recycler.SimpleStringListVH
import com.wk.test.BaseTestActivity

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/01
 * desc         :
 */


class DiListAdapter(data: MutableList<Pair<String, Class<out BaseTestActivity>>> = ArrayList()) :
    BaseRecyclerViewAdapter<Pair<String, Class<out BaseTestActivity>>, SimpleStringListVH>(data) {

    override fun getItemLayoutResId(parent: ViewGroup, viewType: Int) = R.layout.common_only_text

    override fun createVH(rootView: View, viewType: Int): SimpleStringListVH {
        return SimpleStringListVH(rootView, rootView.findViewById(R.id.tvCommon))
    }

    override fun onBindViewHolder(holder: SimpleStringListVH, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.tvCommon.apply {
            text = mData[position].first
            setOnClickListener{
                mIRvClickListener?.onItemClick(this@DiListAdapter,this,position)
            }
        }
    }
}