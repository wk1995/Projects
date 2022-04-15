package com.wk.test

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wk.projects.common.ui.recycler.BaseRecyclerViewAdapter

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/23<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
class TestListAdapter(private val item:List<String> ): BaseRecyclerViewAdapter<String,TestListAdapter.TestListVH>() {

    class TestListVH(rootView: View,val showContent:TextView ) : RecyclerView.ViewHolder(rootView)

    override fun getItemCount()=item.size

    override fun onBindViewHolder(p0: TestListVH, p1: Int) {
        p0.apply {
            showContent.text=item[p1]
            showContent.setOnClickListener {
                mIRvClickListener?.onItemClick(this@TestListAdapter,showContent,p1)
            }
        }
    }

    override fun getItemLayoutResId(parent: ViewGroup, viewType: Int)=android.R.layout.simple_list_item_1

    override fun createVH(rootView: View, viewType: Int): TestListVH {
        val tv=rootView.findViewById<TextView>(android.R.id.text1)
        return TestListVH(rootView,tv)
    }
}