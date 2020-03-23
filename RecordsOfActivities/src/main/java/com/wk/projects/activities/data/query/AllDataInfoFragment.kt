package com.wk.projects.activities.data.query

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.listener.BaseTextWatcher
import kotlinx.android.synthetic.main.schedules_activity_query_data.*
import me.yokeyword.fragmentation.ISupportFragment
import org.litepal.LitePal
import timber.log.Timber

@Route(path = ARoutePath.AllDataInfoFragment)
class AllDataInfoFragment : BaseFragment() {

    private lateinit var mOriginalData :List<ScheduleItem>
    private val mAllDataAdapter by lazy { AllDataAdapter() }
    override fun initResLay() = R.layout.schedules_activity_query_data
    override fun initView() {
        super.initView()
        initRecycler()
        initClickListener()
    }

    private fun initRecycler() {
        rvCommonRs.layoutManager = LinearLayoutManager(_mActivity)
        rvCommonRs.addOnItemTouchListener(object : BaseSimpleClickListener() {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                Timber.i("position:  $position")
                val mScheduleItem = adapter?.getItem(position) as? ScheduleItem
                mScheduleItem?.run {
                    start(ARouter.getInstance()
                            .build(ARoutePath.ActivitiesInfoFragment)
                            .withLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                            .navigation() as ISupportFragment)
                }
            }
        })
        LitePal.findAllAsync(ScheduleItem::class.java).listen {
            mOriginalData=it
            rvCommonRs.adapter = mAllDataAdapter
            mAllDataAdapter.setNewData(it)
        }
    }

    private fun initClickListener() {
        etQueryName.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword=s.toString()
                val newData=mOriginalData.filter {
                    it.itemName?.contains(keyword)==true
                }
                mAllDataAdapter.setNewData(newData)
                tvTotalTime.setText(R.string.activities_all_data_time)
                val totalTime =
                        newData.fold(0L) { i, j ->
                            j.endTime - j.startTime+i
                        }.toString()
                tvTotalTime.append(totalTime)
            }
        })
    }

}
