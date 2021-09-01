package com.wk.projects.schedules

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.BundleKey.LIST_POSITION
import com.wk.projects.common.communication.constant.IFAFlag
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.common.ui.WkToast
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.constant.ActivityRequestCode
import com.wk.projects.schedules.constant.ActivityResultCode
import com.wk.projects.schedules.data.ScheduleItem
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
class SchedulesMainActivity : BaseProjectsActivity(), View.OnClickListener,
        Toolbar.OnMenuItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener {

    private var selectPosition = -1

    private val scheduleMainAdapter by lazy {
        SchedulesMainAdapter(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.ScheduleAppTheme)
    }

    override fun initResLayId() = R.layout.schedules_activity_main

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        setSupportActionBar(tbSchedules)
        tvDaySelected.text = DateTime.getDateString(System.currentTimeMillis())
        SchedulesMainActivityPermissionsDispatcher.getStorageWithCheck(this)
        requestPermission()
        initClickListener()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        rvSchedules.layoutManager = linearLayoutManager
        rvSchedules.adapter = scheduleMainAdapter
        scheduleMainAdapter.onItemChildClickListener = this
        scheduleMainAdapter.onItemChildLongClickListener = this
        rvSchedules.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        initData()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (view?.id) {
            R.id.tvCompleteStatus -> {
                val scheduleItem = scheduleMainAdapter.getItem(position) ?: return
                val tempTime = scheduleItem.endTime
                scheduleItem.endTime = System.currentTimeMillis()
                scheduleItem.saveAsync().listen {
                    if (it) {
                        scheduleMainAdapter.notifyItemChanged(position)
                        WkToast.showToast("完成")
                    } else {
                        scheduleItem.endTime = tempTime
                    }
                }

            }
            R.id.clScheduleItem -> {
                selectPosition = position
                val baseObjId = (adapter?.getItem(position) as? ScheduleItem)?.baseObjId ?: return
                ARouter.getInstance()
                        .build(ARoutePath.ScheduleItemInfoActivity)
                        .withLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
                        .withInt(LIST_POSITION, position)
                        .navigation(this@SchedulesMainActivity,
                                ActivityRequestCode.RequestCode_SchedulesMainActivity)
            }
        }
    }

    override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
        val item = adapter?.getItem(position) as? ScheduleItem ?: return false
        val baseObjId = item.baseObjId
        val itemName = item.itemName
        //修改项目开始时间或者删除项目
        val bundle = Bundle()
        bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, baseObjId)
        bundle.putInt(BundleKey.LIST_POSITION, position)
        bundle.putString(BundleKey.LIST_ITEM_NAME, itemName)
        DeleteScheduleItemDialog.create(bundle).show(supportFragmentManager)
        return true
    }

    private fun initData() {
        val currentTime = DateTime.getDateLong(tvDaySelected.text.toString().trim())
        val toDayStart = getDayStart(currentTime).toString()
        val toDayEnd = getDayEnd(currentTime).toString()
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

    private fun initClickListener() {
        tvDaySelected.setOnClickListener(this)
        fabAddScheduleItem.setOnClickListener(this)
        fabAddScheduleItem.setOnLongClickListener(this)
        tbSchedules.setOnMenuItemClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.schedules_tb_menu, menu)
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            tvDaySelected ->
                TimePickerCreator.create(this) { date, view ->
                    tvDaySelected.text = DateTime.getDateString(date?.time)
                    initData()
                }
            //增加数据库中没有的项目
            fabAddScheduleItem -> {
                val currentTime = System.currentTimeMillis()
                val scheduleItem = ScheduleItem(WkStringConstants.STR_EMPTY, currentTime)
                scheduleItem.saveAsync().listen {
                    if (it) {
                        scheduleMainAdapter.addItem(scheduleItem)
                    }
                }

            }
        }
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when (p0?.itemId) {
//            R.id.menuItemAllData->ARouter.getInstance().build(ARoutePath.AllDataInfoActivity).navigation()
            R.id.menuItemSearch -> {
            }
            R.id.menuItemIdea -> {
                ARouter.getInstance().build(ARoutePath.IdeaActivity).navigation()
            }
        }
        return true
    }

    override fun communication(flag: Int, bundle: Bundle?, any: Any?) {
        when (flag) {
            IFAFlag.SCHEDULE_NEW_ITEM_DIALOG,
            IFAFlag.SCHEDULE_ITEM_DIALOG -> {
                val itemName = bundle?.getString(BundleKey.SCHEDULE_ITEM_NAME) ?: return
                val id = bundle.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID)
                val item = ScheduleItem(itemName)
                item.assignBaseObjId(id)
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
                    Toast.makeText(WkProjects.getApplication(), "删除成功", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode:  $requestCode   resultCode : $resultCode")
        if (requestCode == ActivityRequestCode.RequestCode_SchedulesMainActivity &&
                resultCode == ActivityResultCode.ResultCode_ScheduleItemInfoActivity) {
            val scheduleItem = scheduleMainAdapter.getItem(selectPosition) ?: return
            LitePal.findAsync(ScheduleItem::class.java, scheduleItem.baseObjId).listen {
                scheduleMainAdapter.replaceItem(it ?: return@listen, selectPosition)
            }
        }

        if (requestCode == 1024 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
            } else {
                finish()
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1024) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                finish()
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            }
            return
        }
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

    override fun onLongClick(v: View?): Boolean {
        when (v?.id) {
            R.id.fabAddScheduleItem -> {
                ARouter.getInstance()
                        .build(ARoutePath.ScheduleItemInfoActivity)
                        .navigation(this@SchedulesMainActivity,
                                ActivityRequestCode.RequestCode_SchedulesMainActivity)
            }
        }
        return true
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {

            } else {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, 1024)
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1024)
            }
        } else {

        }
    }
}

