package com.wk.projects.schedules.data.all

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.schedules.R
import com.wk.projects.schedules.R2
import com.wk.projects.schedules.ui.recycler.RefreshHelper

class AllDataInfoActivity : BaseProjectsActivity() {

    @BindView(R2.id.rvCommonRs)
    lateinit var rvCommonRs:RecyclerView
    @BindView(R2.id.srCommonRs)
    lateinit var srCommonRs:SwipeRefreshLayout

    private val refreshHelper by lazy {
        RefreshHelper(this, rvCommonRs, srCommonRs)
    }

    override fun initResLayId() = R.layout.common_rv_with_sr
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        initRecycler()
        refreshHelper.onRefresh()
    }

    private fun initRecycler() {
        srCommonRs.setColorSchemeResources(
                android.R.color.holo_blue_bright
                , android.R.color.holo_green_light
                , android.R.color.holo_orange_light
        )
        /**
         * true  表示下拉时候会有大变小，回去的时候又会有由小变大
         * 120   表示refresh起始高度
         * 300   表示refresh下降高度
         * */
        srCommonRs.setProgressViewOffset(true, 120, 300)
        srCommonRs.setOnRefreshListener(refreshHelper)
        rvCommonRs.layoutManager = LinearLayoutManager(this)
    }
}
