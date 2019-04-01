package com.wk.projects.records.consumption.data.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.records.consumption.R
import kotlinx.android.synthetic.main.consumption_fragment_consumption_detail.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/28
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 消费明细
 * </pre>
 */
@Route(path = ARoutePath.ConsumptionDetailFragment)
class ConsumptionDetailFragment : BaseFragment(), View.OnClickListener {

    private val mConsumptionDetailListAdapter by lazy {
        ConsumptionDetailListAdapter()
    }

    override fun initResLay() = R.layout.consumption_fragment_consumption_detail
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvRecordAPay.setOnClickListener(this)
        rvConsumptionDetail.layoutManager = LinearLayoutManager(_mActivity)
//        mConsumptionDetailListAdapter.setNewData()
        mConsumptionDetailListAdapter.bindToRecyclerView(rvConsumptionDetail)
        mConsumptionDetailListAdapter.setEmptyView(R.layout.common_list_empty)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvRecordAPay -> {
                (parentFragment as BaseFragment)
                        .start(ARouter.getInstance().build(ARoutePath.AddDetailFragment).navigation() as BaseFragment)
            }
        }
    }
}