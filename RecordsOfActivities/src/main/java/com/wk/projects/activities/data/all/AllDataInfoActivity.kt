package com.wk.projects.activities.data.all

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.activities.R
import com.wk.projects.activities.data.ScheduleItem
import kotlinx.android.synthetic.main.schedules_activity_query_data.*
import org.litepal.LitePal

@Route(path = ARoutePath.AllDataInfoActivity)
class AllDataInfoActivity : BaseProjectsActivity() {

    override fun initResLayId() = R.layout.schedules_activity_query_data
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        initRecycler()
        initClickListener()
    }

    private fun initRecycler() {
        rvCommonRs.layoutManager = LinearLayoutManager(this)
        LitePal.findAllAsync(ScheduleItem::class.java).listen {
            val mAllDataAdapter = AllDataAdapter(this)
            rvCommonRs.adapter = mAllDataAdapter
            mAllDataAdapter.setNewData(it)
        }
    }

    private fun initClickListener() {
        etQueryName.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                LitePal.where()
            }
        })
    }

}
