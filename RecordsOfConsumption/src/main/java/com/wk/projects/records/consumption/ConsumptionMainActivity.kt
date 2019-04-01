package com.wk.projects.records.consumption

import android.Manifest
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.constant.ARoutePath
import kotlinx.android.synthetic.main.consumption_activity_main.*
import me.yokeyword.fragmentation.ISupportFragment
import permissions.dispatcher.*

@Route(path = ARoutePath.ConsumptionMainActivity)
@RuntimePermissions
class ConsumptionMainActivity : BaseProjectsActivity() {
    override fun initResLay() = R.layout.consumption_activity_main
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        setSupportActionBar(tbConsumption)
        ConsumptionMainActivityPermissionsDispatcher.getStorageWithCheck(this)



    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ConsumptionMainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getStorage() {
        if (findFragment(ConsumptionMainFragment::class.java) == null)
            loadRootFragment(R.id.flConsumptionContent,
                    ARouter.getInstance()
                            .build(ARoutePath.ConsumptionMainFragment)
                            .navigation()
                            as ISupportFragment)
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun refusePermission() {
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationale(request: PermissionRequest) {

    }

}
