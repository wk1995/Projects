package com.wk.projects.activities

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.communication.constant.RequestCode
import com.wk.projects.activities.communication.constant.ResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.communication.constant.SchedulesBundleKey.SCHEDULE_OPERATION
import com.wk.projects.activities.data.ActivitiesInfoFragment.Companion.OPERATION_MODIFY
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.activities.ui.recycler.SchedulesMainAdapter
import com.wk.projects.activities.update.DeleteScheduleItemDialog
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.eventBus.EventMsg
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.date.DateTime
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import com.wk.projects.common.ui.recycler.BaseRvSimpleClickListener
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import kotlinx.android.synthetic.main.activities_fragment_main.*
import me.yokeyword.fragmentation.ISupportFragment
import org.litepal.LitePal
import timber.log.Timber
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 主界面
 * </pre>
 */
@Route(path = ARoutePath.ActivitiesMainFragment)
class ActivitiesMainFragment : BaseFragment(), View.OnClickListener, OnTimeSelectListener {

    private val scheduleMainAdapter by lazy {
        SchedulesMainAdapter(ArrayList())
    }
    private val linearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    override fun initResLay() = R.layout.activities_fragment_main

    override fun initView() {
        super.initView()
        tvDaySelected.text = DateTime.getDateString(System.currentTimeMillis())
        initClickListener()
        initRecyclerView()

    }

    private fun initClickListener() {
        tvDaySelected.setOnClickListener(this)
        fabAddScheduleItem.setOnClickListener(this)
    }

    override fun call(t: Any?) {
        if (t is ActivitiesMsg) {
            val data = (t.any) as? Bundle
            when (t.flag) {
            //增加新项目
                EventMsg.ADD_ITEM -> {
                    scheduleMainAdapter.data.add((t.any) as ScheduleItem)
                    rvSchedules.scrollToPosition(scheduleMainAdapter.itemCount - 1 - scheduleMainAdapter.footerLayoutCount)
                }
            //增加新项目
                EventMsg.SCHEDULE_ITEM_DIALOG -> {
                    val itemName = data?.getString(SchedulesBundleKey.SCHEDULE_ITEM_NAME) ?: return
                    val id = data.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID)
                    val item = ScheduleItem(itemName)
                    item.assignBaseObjId(id.toInt())
                    scheduleMainAdapter.data.add(item)
                    rvSchedules.scrollToPosition(scheduleMainAdapter.itemCount - 1 - scheduleMainAdapter.footerLayoutCount)
                }

            //删除项目
                EventMsg.DELETE_ITEM_DIALOG -> {
                    val id = data?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1)
                            ?: throw Exception("id 有问题")
                    val position = data.getInt(BundleKey.LIST_POSITION, -1)
                    LitePal.deleteAsync(ScheduleItem::class.java, id).listen {
                        val itemList = scheduleMainAdapter.data
                        if (itemList.size <= 0) return@listen
                        val item = itemList[position]
                        if (item.baseObjId == id)
                            itemList.remove(item)
                        scheduleMainAdapter.notifyDataSetChanged()
                        ToastUtil.show(WkContextCompat.getString(R.string.common_str_delete_successful), ToastUtil.LENGTH_SHORT)
                    }
                }

                EventMsg.QUERY_ALL_DATA ->
                    start(ARouter.getInstance()
                            .build(ARoutePath.AllDataInfoFragment).navigation() as ISupportFragment)
            }
        }
    }

    private fun initRecyclerView() {
        //两行是为了倒序显示
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
                        startForResult(ARouter.getInstance()
                                .build(ARoutePath.ActivitiesInfoFragment)
                                .withLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                                .withInt(BundleKey.LIST_POSITION, position)
                                .navigation() as ISupportFragment,
                                RequestCode.SchedulesMainActivity)
                    }
                }
            }

            //长按
            override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val popupMenu = PopupMenu(_mActivity, view ?: return)
                //加载菜单文件
                popupMenu.menuInflater.inflate(R.menu.activities_main_delete_and_update, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    val id = it.itemId
                    when (id) {
                        R.id.menuItemRename -> {
                            ToastUtil.show("重命名")
                        }
                        R.id.menuItemDelete -> {
                            val item = adapter?.getItem(position) as? ScheduleItem?
                            if (item != null) {
                                val baseObjId = item.baseObjId
                                val itemName = item.itemName
                                //修改项目开始时间或者删除项目
                                val bundle = Bundle()
                                bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                                bundle.putInt(BundleKey.LIST_POSITION, position)
                                bundle.putString(BundleKey.LIST_ITEM_NAME, itemName)
                                DeleteScheduleItemDialog.create(bundle).show(childFragmentManager)
                            }
                        }
                    }
                    true
                }
                popupMenu.show()
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
                    scheduleMainAdapter.setNewData(it)
                }
    }

    override fun onTimeSelect(date: Date?, v: View?) {
        tvDaySelected.text = DateTime.getDateString(date?.time)
        initData()
    }

    override fun onClick(v: View?) {
        when (v) {
            tvDaySelected ->
                TimePickerCreator.create(_mActivity, this)
        //增加数据库中没有的项目
            fabAddScheduleItem -> start(
                    ARouter.getInstance()
                            .build(ARoutePath.ActivitiesInfoFragment)
                            .navigation() as ISupportFragment)
        }
    }

    //修改数据后回来
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        Timber.i("requestCode:  $requestCode   resultCode : $resultCode")
        if (requestCode == RequestCode.SchedulesMainActivity &&
                resultCode == ResultCode.ResultCode_ScheduleItemInfoActivity) {
            val operationType = data?.getString(SCHEDULE_OPERATION)
            when (operationType) {
            // 修改
                OPERATION_MODIFY -> {
                    val position = data.getInt(BundleKey.LIST_POSITION, -1)
                    if (position < 0)
                        return
                    val endTime = data.getLong(SchedulesBundleKey.SCHEDULE_END_TIME, 0)
                    val startTime = data.getLong(SchedulesBundleKey.SCHEDULE_START_TIME, 0)
                    val mScheduleItemName = data.getString(SchedulesBundleKey.SCHEDULE_ITEM_NAME)
                            ?: throw Exception("没有活动名")
                    val scheduleItem = scheduleMainAdapter.getItem(position) ?: return
                    scheduleItem.endTime = endTime
                    scheduleItem.startTime = startTime
                    scheduleItem.itemName = mScheduleItemName
                    scheduleMainAdapter.notifyItemChanged(position)
                }
            }

        }
    }
}