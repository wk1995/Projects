package com.wk.projects.records.consumption


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.date.DateTime
import com.wk.projects.common.date.DateTime.getDayEnd
import com.wk.projects.common.date.DateTime.getDayStart
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import kotlinx.android.synthetic.main.consumption_fragment_main.*
import timber.log.Timber
import java.util.*

@Route(path = ARoutePath.ConsumptionMainFragment)

class ConsumptionMainFragment : BaseFragment(), View.OnClickListener {

    private var currentPosition = 0
    //放入各个子Fragment的LinkHashMap 保证顺序
    private val itemFragmentMap by lazy { LinkedHashMap<MoneyItemTab, BaseFragment>() }
    //上面的tab
    private val itemabList = ArrayList<MoneyItemTab>()
    //各个子Fragment
    private val itemFragment = ArrayList<BaseFragment>()

    override fun initResLay() = R.layout.consumption_fragment_main

    override fun beforeCreateView() {
        super.beforeCreateView()
        addItem()
        for ((k, v) in itemFragmentMap) {
            itemabList.add(k)
            itemFragment.add(v)
        }
    }

    private fun addItem() {
        itemFragmentMap[MoneyItemTab(R.string.common_str_detail)] =
                aRouter.build(ARoutePath.ConsumptionDetailFragment).navigation() as BaseFragment
        itemFragmentMap[MoneyItemTab(R.string.common_str_account)] =
                aRouter.build(ARoutePath.ConsumptionAccountFragment).navigation() as BaseFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDaySelected.text = DateTime.getDateString(System.currentTimeMillis())
        initClickListener()
        for (i in 0 until itemFragmentMap.size) {
            LayoutInflater.from(context).inflate(R.layout.common_only_text, llConsumptionTab)
            val tab = llConsumptionTab.getChildAt(i) as TextView
            val params = tab.layoutParams as LinearLayout.LayoutParams
            params.width = 0
            params.weight = 1f
            tab.text = WkContextCompat.getString(itemabList[i].nameStringResId)
            tab.tag = i
            tab.setOnClickListener(this)
            if (currentPosition == i) {
                tab.setTextColor(Color.BLACK)
                tab.setBackgroundColor(Color.WHITE)
            } else {
                tab.setTextColor(Color.WHITE)
                tab.setBackgroundColor(Color.BLACK)
            }
        }
        loadMultipleRootFragment(R.id.flAboutMoney, currentPosition, *itemFragment.toTypedArray())
    }


    private fun initClickListener() {
        tvDaySelected.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvDaySelected -> TimePickerCreator.create(_mActivity,object : OnTimeSelectListener {
                override fun onTimeSelect(date: Date?, view: View?) {
                    tvDaySelected.text = DateTime.getDateString(date?.time)
                    initData()
                }
            })

        }
        val tag = v?.tag as? Int ?: return
        resetItemTab()
        val itemTab = llConsumptionTab.getChildAt(tag) as TextView
        itemTab.setTextColor(Color.BLACK)
        itemTab.setBackgroundColor(Color.WHITE)
        showHideFragment(itemFragment[tag], itemFragment[currentPosition])
        currentPosition = tag
//        v.tag
    }

    private fun resetItemTab() {
        val tabSize = llConsumptionTab.childCount
        for (i in 0 until tabSize) {
            val item = llConsumptionTab.getChildAt(i) as TextView
            item.setTextColor(Color.WHITE)
            item.setBackgroundColor(Color.BLACK)
        }
    }


    private fun initData() {
        val currentTime = DateTime.getDateLong(tvDaySelected.text.toString().trim())
        val toDayStart = getDayStart(currentTime).toString()
        val toDayEnd = getDayEnd(currentTime).toString()
        Timber.d("69 toDayStart ${DateTime.getDateString(toDayStart.toLong())} toDayEnd ${DateTime.getDateString(toDayEnd.toLong())}")

        /*  //开始的时间是当天,且显示以前未完成的活动
          LitePal.where("(startTime>? and startTime<?) or (startTime>endTime)", toDayStart, toDayEnd)
                  .order("startTime")
                  .findAsync(ScheduleItem::class.java)
                  .listen {
                      scheduleMainAdapter.setNewData(it)
                  }*/
    }
}
