package com.wk.projects.activities

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.communication.constant.ActivityRequestCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.activities.data.add.ScheduleItemAddDialog
import com.wk.projects.activities.date.DateTime
import com.wk.projects.activities.ui.recycler.SchedulesMainAdapter
import com.wk.projects.activities.ui.time.TimePickerCreator
import com.wk.projects.activities.update.DeleteScheduleItemDialog
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.eventBus.EventMsg
import com.wk.projects.common.communication.eventBus.EventMsg.Companion.INIT_RECYCLER
import com.wk.projects.common.communication.eventBus.EventMsg.Companion.SCHEDULE_ITEM_DIALOG
import com.wk.projects.common.communication.eventBus.EventMsg.Companion.UPDATE_DATA
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import com.wk.projects.common.ui.recycler.BaseRvSimpleClickListener
import kotlinx.android.synthetic.main.activities_fragment_main.*
import org.litepal.LitePal
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import timber.log.Timber
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class ActivitiesMainFragment : BaseFragment(), View.OnClickListener, Action1<Any> {

    companion object {
        fun getInstance(bundle: Bundle? = null): ActivitiesMainFragment {
            val mActivitiesMainFragment = ActivitiesMainFragment()
            mActivitiesMainFragment.arguments = bundle
            return mActivitiesMainFragment
        }
    }


    override fun initResLay() = R.layout.activities_fragment_main
    private val scheduleMainAdapter by lazy {
        SchedulesMainAdapter(ArrayList())
    }
    private val linearLayoutManager by lazy { LinearLayoutManager(_mActivity) }
    override fun initView() {
        super.initView()
        tvDaySelected.text = DateTime.getDateString(System.currentTimeMillis())
        initClickListener()
        initRecyclerView()
        rxBus.getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    private fun initClickListener() {
        tvDaySelected.setOnClickListener(this)
        fabAddScheduleItem.setOnClickListener(this)
    }

    override fun call(t: Any?) {
        if (t is ActivitiesMsg) {
            val data = (t.any) as? Bundle
            when (t.flag) {
                EventMsg.SCHEDULE_ITEM_DIALOG -> {
                    val itemName = data?.getString(BundleKey.SCHEDULE_ITEM_NAME) ?: return
                    val id = data.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID)
                    val item = ScheduleItem(itemName)
                    item.assignBaseObjId(id.toInt())
                    scheduleMainAdapter.addItem(item)
                    linearLayoutManager.scrollToPositionWithOffset(0, 0)
                    linearLayoutManager.stackFromEnd = true
                }

                EventMsg.DELETE_ITEM_DIALOG -> {
                    val id = data?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1)
                            ?: throw Exception("id 有问题")
                    val position = data.getInt(BundleKey.LIST_POSITION, -1)
                    LitePal.deleteAsync(ScheduleItem::class.java, id).listen {
                        val itemList = scheduleMainAdapter.itemList
                        if (itemList.size <= 0) return@listen
                        val item = itemList[position]
                        if (item.baseObjId == id)
                            itemList.remove(item)
                        scheduleMainAdapter.notifyDataSetChanged()
                        ToastUtil.show(WkContextCompat.getString(R.string.common_str_delete_successful), ToastUtil.LENGTH_SHORT)
                    }
                }
                INIT_RECYCLER -> initRecyclerView()
                UPDATE_DATA -> {

                    val position = data?.getInt(BundleKey.LIST_POSITION, -1) ?: return
                    if (position < 0)
                        return
                    val endTime = data.getLong(ScheduleItem.COLUMN_END_TIME, 0)
                    val startTime = data.getLong(ScheduleItem.COLUMN_START_TIME, 0)
                    val scheduleItem = scheduleMainAdapter.getItem(position) ?: return
                    scheduleItem.endTime = endTime
                    scheduleItem.startTime = startTime
                    scheduleMainAdapter.notifyItemChanged(position)
                }
            }
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        rvSchedules.layoutManager = linearLayoutManager
        scheduleMainAdapter.bindToRecyclerView(rvSchedules)
        scheduleMainAdapter.setEmptyView(R.layout.common_list_empty)
        rvSchedules.addOnItemTouchListener(object : BaseRvSimpleClickListener() {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val baseObjId = (adapter?.getItem(position) as? ScheduleItem)?.baseObjId ?: return
                when (view?.id) {

                    R.id.clScheduleItem -> {
//                        start(ActivitiesItemInfoFragment.getInstance())
                        ARouter.getInstance()
                                .build(ARoutePath.ScheduleItemInfoActivity)
                                .withLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                                .withInt(BundleKey.LIST_POSITION, position)
                                .navigation(_mActivity, ActivityRequestCode.RequestCode_SchedulesMainActivity)
                    }
                }
            }

            //长按
            override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val item = adapter?.getItem(position) as? ScheduleItem ?: return
                val baseObjId = item.baseObjId
                val itemName = item.itemName
                //修改项目开始时间或者删除项目
                val bundle = Bundle()
                bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                bundle.putInt(BundleKey.LIST_POSITION, position)
                bundle.putString(BundleKey.LIST_ITEM_NAME, itemName)
                DeleteScheduleItemDialog.create(bundle).show(childFragmentManager)
            }
        })
        rvSchedules.addItemDecoration(
                DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL))
        initData()
    }

    private fun initData() {
        val currentTime = DateTime.getDateLong(tvDaySelected.text.toString().trim())
        val toDayStart = DateTime.getDayStart(currentTime).toString()
        val toDayEnd = DateTime.getDayEnd(currentTime).toString()
        Timber.d("69 toDayStart ${DateTime.getDateString(toDayStart.toLong())} toDayEnd ${DateTime.getDateString(toDayEnd.toLong())}")

        //开始的时间是当天,对结束的时间没有限制
        LitePal.where("startTime>? and startTime<?", toDayStart, toDayEnd)
                .order("startTime")
                .findAsync(ScheduleItem::class.java)
                .listen {
                    scheduleMainAdapter.clear()
                    scheduleMainAdapter.addItems(it)
                }
    }

    override fun onClick(v: View?) {
        when (v) {
            tvDaySelected ->
                TimePickerCreator.create(_mActivity, object : OnTimeSelectListener {
                    override fun onTimeSelect(date: Date?, view: View?) {
                        tvDaySelected.text = DateTime.getDateString(date?.time)
                        initData()
                    }
                })
        //增加数据库中没有的项目
            fabAddScheduleItem -> ScheduleItemAddDialog.create().show(childFragmentManager)
        }
    }
}