package com.wk.projects.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.communication.constant.ActivityRequestCode
import com.wk.projects.activities.communication.constant.ActivityResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.activities.data.add.CategoryDialog
import com.wk.projects.activities.data.add.ScheduleItemAddDialog
import com.wk.projects.common.date.DateTime
import com.wk.projects.common.date.DateTime.getDayEnd
import com.wk.projects.common.date.DateTime.getDayStart
import com.wk.projects.activities.permission.PermissionDialog
import com.wk.projects.activities.permission.RefuseDialog
import com.wk.projects.activities.ui.recycler.SchedulesMainAdapter
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import com.wk.projects.activities.update.DeleteScheduleItemDialog
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.BundleKey.LIST_POSITION
import com.wk.projects.common.communication.eventBus.EventMsg
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.CommonFilePath
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import com.wk.projects.common.ui.recycler.BaseRvSimpleClickListener
import kotlinx.android.synthetic.main.schedules_activity_main.*
import org.litepal.LitePal
import permissions.dispatcher.*
import rx.functions.Action1
import timber.log.Timber
import java.io.File
import java.util.*

@Route(path = ARoutePath.SchedulesMainActivity)
@RuntimePermissions
class SchedulesMainActivity : BaseProjectsActivity(), Action1<Any>,
        View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private val scheduleMainAdapter by lazy {
        SchedulesMainAdapter(ArrayList())
    }
    private val linearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun initResLay() = R.layout.schedules_activity_main

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        setSupportActionBar(tbSchedules)
        startService(Intent(this, AppInitIntentService::class.java))
        tvDaySelected.text = DateTime.getDateString(System.currentTimeMillis())
        SchedulesMainActivityPermissionsDispatcher.getStorageWithCheck(this)
        initClickListener()
        mSubscription = rxBus.getObservable().subscribe(this)
    }

    private fun initRecyclerView() {
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        rvSchedules.layoutManager = linearLayoutManager
        scheduleMainAdapter.bindToRecyclerView(rvSchedules)
        scheduleMainAdapter.setEmptyView(R.layout.common_list_empty)
//        scheduleMainAdapter.addFooterView(TextView(this))
        rvSchedules.addOnItemTouchListener(object : BaseRvSimpleClickListener() {

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val baseObjId = (adapter?.getItem(position) as? ScheduleItem)?.baseObjId ?: return
                when (view?.id) {
                    R.id.clScheduleItem -> ARouter.getInstance()
                            .build(ARoutePath.ScheduleItemInfoActivity)
                            .withLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                            .withInt(LIST_POSITION, position)
                            .navigation(this@SchedulesMainActivity, ActivityRequestCode.RequestCode_SchedulesMainActivity)
                }
            }

            //长按
            override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val popupMenu = PopupMenu(this@SchedulesMainActivity, view ?: return)
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
                                DeleteScheduleItemDialog.create(bundle).show(supportFragmentManager)
                            }
                        }
                    }
                    true
                }
                popupMenu.show()

            }

        })
        rvSchedules.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        initData()

    }


    private fun initData() {
        val currentTime = DateTime.getDateLong(tvDaySelected.text.toString().trim())
        val toDayStart = getDayStart(currentTime).toString()
        val toDayEnd = getDayEnd(currentTime).toString()
        Timber.d("69 toDayStart ${DateTime.getDateString(toDayStart.toLong())} toDayEnd ${DateTime.getDateString(toDayEnd.toLong())}")

        //开始的时间是当天,且显示以前未完成的活动
        LitePal.where("(startTime>? and startTime<?) or (startTime>endTime)", toDayStart, toDayEnd)
                .order("startTime")
                .findAsync(ScheduleItem::class.java)
                .listen {
                    scheduleMainAdapter.setNewData(it)
                }
    }

    private fun initClickListener() {
        tvDaySelected.setOnClickListener(this)
        fabAddScheduleItem.setOnClickListener(this)
        tbSchedules.setOnMenuItemClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.schedules_tb_menu, menu)
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            tvDaySelected ->
                TimePickerCreator.create(this, object : OnTimeSelectListener {
                    override fun onTimeSelect(date: Date?, view: View?) {
                        tvDaySelected.text = DateTime.getDateString(date?.time)
                        initData()
                    }
                })
        //增加数据库中没有的项目
            fabAddScheduleItem ->
//                CategoryDialog.create().show(supportFragmentManager)
                ScheduleItemAddDialog.create().show(supportFragmentManager)
        }
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when (p0?.itemId) {
            R.id.menuItemAllData -> ARouter.getInstance().build(ARoutePath.AllDataInfoActivity).navigation()
            R.id.menuItemSearch -> {
            }
            R.id.menuItemIdea -> {
                ARouter.getInstance().build(ARoutePath.ScheduleIdeaActivity).navigation()
            }
            R.id.menuDeleteDB -> {
                val path = "wk/projects/database/schedules"
                val path1 = CommonFilePath.ES_PATH + path
                val file = File(path1)
                Timber.i("$file 存在 ？ ${file.exists()}")
                val result = file.parentFile
                        .delete()
                ToastUtil.show(if (result) "删除成功" else "删除失败")
            }
        }
        return true
    }

    //RxBus的回调
    override fun call(t: Any?) {
        if (t is ActivitiesMsg) {
            val bundle = t.any as? Bundle
            when (t.flag) {
                EventMsg.SCHEDULE_ITEM_DIALOG -> {
                    val itemName = bundle?.getString(BundleKey.SCHEDULE_ITEM_NAME) ?: return
                    val id = bundle.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID)
                    val item = ScheduleItem(itemName)
                    item.assignBaseObjId(id.toInt())
                    scheduleMainAdapter.addItem(item)
                    rvSchedules.scrollToPosition(scheduleMainAdapter.itemCount - 1 - scheduleMainAdapter.footerLayoutCount)
                }

                EventMsg.DELETE_ITEM_DIALOG -> {
                    val id = bundle?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1)
                            ?: throw Exception("id 有问题")
                    val position = bundle.getInt(BundleKey.LIST_POSITION, -1)
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
            val endTime = data.getLongExtra(ScheduleItem.COLUMN_END_TIME, 0)
            val startTime = data.getLongExtra(ScheduleItem.COLUMN_START_TIME, 0)
            val scheduleItem = scheduleMainAdapter.getItem(position) ?: return
            scheduleItem.endTime = endTime
            scheduleItem.startTime = startTime
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

