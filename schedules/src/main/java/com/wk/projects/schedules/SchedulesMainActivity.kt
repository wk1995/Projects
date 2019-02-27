package com.wk.projects.schedules

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.BundleKey.LIST_POSITION
import com.wk.projects.common.communication.constant.IFAFlag
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.ui.recycler.BaseRvSimpleClickListener
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.constant.ActivityRequestCode
import com.wk.projects.schedules.constant.ActivityResultCode
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.data.add.ScheduleItemAddDialog
import com.wk.projects.schedules.date.DateTime
import com.wk.projects.schedules.date.DateTime.getDayEnd
import com.wk.projects.schedules.date.DateTime.getDayStart
import com.wk.projects.schedules.permission.PermissionDialog
import com.wk.projects.schedules.permission.RefuseDialog
import com.wk.projects.schedules.ui.recycler.SchedulesMainAdapter
import com.wk.projects.schedules.ui.time.TimePickerCreator
import com.wk.projects.schedules.update.DeleteScheduleItemDialog
import kotlinx.android.synthetic.main.schedules_activity_main.*
import org.litepal.LitePal
import permissions.dispatcher.*
import timber.log.Timber
import java.util.*

@Route(path = ARoutePath.SchedulesMainActivity)
@RuntimePermissions
class SchedulesMainActivity : BaseProjectsActivity(), View.OnClickListener {
    private val scheduleMainAdapter by lazy {
        SchedulesMainAdapter(ArrayList())
    }

    @BindView(R2.id.addNewScheduleItem)
    lateinit var addNewScheduleItem: TextView
    @BindView(R2.id.tvQueryAllData)
    lateinit var tvQueryAllData: TextView
    @BindView(R2.id.tvDaySelected)
    lateinit var tvDaySelected: TextView

    override fun initResLayId() = R.layout.schedules_activity_main

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        tvDaySelected.text = DateTime.getTimeString(System.currentTimeMillis())
        SchedulesMainActivityPermissionsDispatcher.getStorageWithCheck(this)
        initClickListener()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        rvSchedules.layoutManager = linearLayoutManager
        rvSchedules.adapter = scheduleMainAdapter
        rvSchedules.addOnItemTouchListener(object : BaseRvSimpleClickListener() {

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val baseObjId = (adapter?.getItem(position) as? ScheduleItem)?.baseObjId ?: return
                when (view?.id) {
                    R.id.tvCommon -> ARouter.getInstance()
                            .build(ARoutePath.ScheduleItemInfoActivity)
                            .withLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                            .withInt(LIST_POSITION, position)
                            .navigation(this@SchedulesMainActivity, ActivityRequestCode.RequestCode_SchedulesMainActivity)
                }
            }

            override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val item = adapter?.getItem(position) as? ScheduleItem ?: return
                val baseObjId = item.baseObjId
                val itemName = item.itemName
                //修改项目开始时间或者删除项目
                val bundle = Bundle()
                bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                bundle.putInt(BundleKey.LIST_POSITION, position)
                bundle.putString(BundleKey.LIST_ITEM_NAME, itemName)
                DeleteScheduleItemDialog.create(bundle).show(supportFragmentManager)
            }
        })
        rvSchedules.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        initData()
    }

    private fun initData() {
        val currentTime = DateTime.getTime(tvDaySelected.text.toString().trim())
        val toDayStart = getDayStart(currentTime).toString()
        val toDayEnd = getDayEnd(currentTime).toString()
        Timber.d("69 toDayStart ${DateTime.getTimeString(toDayStart.toLong())} toDayEnd ${DateTime.getTimeString(toDayEnd.toLong())}")

        //开始的时间是当天,对结束的时间没有限制
        LitePal.where("startTime>? and startTime<?", toDayStart, toDayEnd)
                .order("startTime")
                .findAsync(ScheduleItem::class.java)
                .listen {
                    scheduleMainAdapter.clear()
                    scheduleMainAdapter.addItems(it)
                }
    }


    private fun initClickListener() {
        addNewScheduleItem.setOnClickListener(this)
        tvQueryAllData.setOnClickListener(this)
        tvDaySelected.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
        //增加数据库中没有的项目
            addNewScheduleItem ->
                ScheduleItemAddDialog.create().show(supportFragmentManager)
//                Timber.d("83 ${DateTime.getTimeString(DateTime.getDayStart(System.currentTimeMillis()))}")

            tvQueryAllData -> ARouter.getInstance().build(ARoutePath.AllDataInfoActivity).navigation()
            tvDaySelected ->
                TimePickerCreator.create(this, object : OnTimeSelectListener {
                    override fun onTimeSelect(date: Date?, view: View?) {
                        tvDaySelected.text = DateTime.getTimeString(date?.time)
                        initData()
                    }
                })
        }
    }

    override fun communication(flag: Int, bundle: Bundle?, any: Any?) {
        when (flag) {
            IFAFlag.SCHEDULE_NEW_ITEM_DIALOG,
            IFAFlag.SCHEDULE_ITEM_DIALOG -> {
                val itemName = bundle?.getString(BundleKey.SCHEDULE_ITEM_NAME) ?: return
                val id = bundle.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID)
                val item = ScheduleItem(itemName)
                item.assignBaseObjId(id.toInt())
                scheduleMainAdapter.addItem(item)
            }
            IFAFlag.DELETE_ITEM_DIALOG -> {
                val id = bundle?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1)
                        ?: throw Exception("id 有问题")
                val position = bundle.getInt(BundleKey.LIST_POSITION, -1)
                LitePal.deleteAsync(ScheduleItem::class.java, id).listen {
                    val itemList = scheduleMainAdapter.itemList
                    if (itemList.size <= 0) return@listen
                    val item = itemList[position]
                    if (item.baseObjId == id)
                        itemList.remove(item)
                    scheduleMainAdapter.notifyDataSetChanged()
                    Toast.makeText(WkProjects.getContext(), "删除成功", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode:  $requestCode   resultCode : $resultCode")
        if (requestCode == ActivityRequestCode.RequestCode_SchedulesMainActivity &&
                resultCode == ActivityResultCode.ResultCode_ScheduleItemInfoActivity) {
            val position = data?.getIntExtra(LIST_POSITION, -1) ?: return
            if (position < 0)
                return
            val endTime=data.getLongExtra(ScheduleItem.COLUMN_END_TIME,0)
            val startTime=data.getLongExtra(ScheduleItem.COLUMN_START_TIME,0)
            val scheduleItem=scheduleMainAdapter.getItem(position)?:return
            scheduleItem.endTime=endTime
            scheduleItem.startTime=startTime
            scheduleMainAdapter.notifyItemChanged(position)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        SchedulesMainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getStorage() {
        initRecyclerView()
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun refusePermission() {
        RefuseDialog().show(this)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationale(request: PermissionRequest) {
        PermissionDialog().withRequest(request).show(this)
    }

}

