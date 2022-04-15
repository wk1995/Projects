package com.wk.test

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.ui.recycler.BaseRecyclerViewAdapter
import com.wk.projects.common.ui.recycler.IRvClickListener

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/12
 * desc         :
 */


abstract class BaseListActivity<T> : BaseProjectsActivity(), IRvClickListener {
    private lateinit var rvDataList: RecyclerView
    override fun initResLayId() = R.layout.test_base_list_layout_activity

    private val map: Map<String, T> by lazy {
        getListMap()
    }

    private val list by lazy {
        val list = ArrayList<String>()
        map.forEach {
            list.add(it.key)
        }
        list
    }

    override fun bindView(
        savedInstanceState: Bundle?,
        mBaseProjectsActivity: BaseProjectsActivity
    ) {
        rvDataList = findViewById(R.id.rvDataList)
        rvDataList.layoutManager = LinearLayoutManager(this)
        rvDataList.adapter = TestListAdapter(list)
    }

    abstract fun getListMap(): Map<String, T>

    override fun onItemClick(adapter: RecyclerView.Adapter<*>?, view: View?, position: Int) {
        if (adapter is BaseRecyclerViewAdapter<*, *>) {
            val item = adapter.getItem(position)
            map[item]
        }

    }

}