package com.wk.projects.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.permission.PermissionDialog
import com.wk.projects.activities.permission.RefuseDialog
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.communication.eventBus.EventMsg.Companion.QUREY_ALL_DATA
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.CommonFilePath
import com.wk.projects.common.ui.notification.ToastUtil
import kotlinx.android.synthetic.main.schedules_activity_main.*
import me.yokeyword.fragmentation.ISupportFragment
import permissions.dispatcher.*
import timber.log.Timber
import java.io.File

@Route(path = ARoutePath.SchedulesMainActivity)
@RuntimePermissions
class SchedulesMainActivity : BaseProjectsActivity(),
        Toolbar.OnMenuItemClickListener {
    override fun initResLay() = R.layout.schedules_activity_main

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        setSupportActionBar(tbSchedules)
        tbSchedules.setOnMenuItemClickListener(this)
        SchedulesMainActivityPermissionsDispatcher.getStorageWithCheck(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.schedules_tb_menu, menu)
        return true
    }


    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        Timber.i("p0?.itemId:: ${p0?.itemId}")
        when (p0?.itemId) {
            R.id.menuItemAllData ->rxBus.post(ActivitiesMsg(QUREY_ALL_DATA))

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


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        SchedulesMainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getStorage() {
        startService(Intent(this, AppInitIntentService::class.java))
        if (findFragment(ActivitiesMainFragment::class.java) == null)
            loadRootFragment(R.id.activities_main_content,
                    ARouter.getInstance().build(ARoutePath.ActivitiesMainFragment).navigation() as ISupportFragment)
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

