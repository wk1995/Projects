package com.wk.projects.schedules.data.all

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.schedules.R
import kotlinx.android.synthetic.main.schedules_activity_query_data.*

@Route(path = ARoutePath.AllDataInfoActivity)
class AllDataInfoActivity : BaseProjectsActivity() {

    override fun initResLayId() = R.layout.schedules_activity_query_data
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        initRecycler()
        initClickListener()
    }

    private fun initRecycler() {
        rvCommonRs.layoutManager = LinearLayoutManager(this)
    }

    private fun initClickListener() {
        etQueryName.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

}
