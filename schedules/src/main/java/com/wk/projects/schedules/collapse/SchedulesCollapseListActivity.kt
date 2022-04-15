package com.wk.projects.schedules.collapse

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.schedules.R
import java.io.File

class SchedulesCollapseListActivity : BaseProjectsActivity() {

    private lateinit var rvCollapseList: RecyclerView

    private val mCollapseAdapter by lazy {
        CollapseListAdapter()
    }

    private val mSchedulesCollapseListPresent by lazy {
        SchedulesCollapseListPresent(this)
    }

    override fun initResLayId() = R.layout.schedules_collapse_list_activity

    override fun bindView(
        savedInstanceState: Bundle?,
        mBaseProjectsActivity: BaseProjectsActivity
    ) {
        rvCollapseList = findViewById(R.id.rvCollapseList)
        rvCollapseList.layoutManager = LinearLayoutManager(this)
        rvCollapseList.adapter = mCollapseAdapter
        initData()
    }

    private fun initData() {
        mSchedulesCollapseListPresent.initData()
    }

    fun updateData(data: List<File>) {
        mCollapseAdapter.updateData(data)
    }


}