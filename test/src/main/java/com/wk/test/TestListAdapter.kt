package com.wk.test

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/23<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
class TestListAdapter(private val item:List<String>): RecyclerView.Adapter<TestListAdapter.TestListVH>() {

    interface ITestItemClickListener{
        fun onTestItemClick(position:Int)
    }
    var testItemClickListener:ITestItemClickListener?=null
    class TestListVH(rootView: View,val showContent:TextView ) :RecyclerView.ViewHolder(rootView)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TestListVH {
        val rootView=LayoutInflater.from(p0.context).inflate(android.R.layout.simple_list_item_1,p0,false)
        val tv=rootView.findViewById<TextView>(android.R.id.text1)
        return TestListVH(rootView,tv)
    }

    override fun getItemCount()=item.size

    override fun onBindViewHolder(p0: TestListVH, p1: Int) {
        p0.apply {
            showContent.text=item[p1]
            showContent.setOnClickListener {
                testItemClickListener?.onTestItemClick(p1)
            }
        }
    }
}