package com.wk.projects.activities.data.add

import android.content.Intent
import android.os.Bundle
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.common.ui.notification.ToastUtil

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/8/18<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
class CoordinateItemAddDialog:ScheduleItemAddDialog() {
    companion object {
        fun create(bundle: Bundle? = null): CoordinateItemAddDialog {
            val mCoordinateItemAddDialog = CoordinateItemAddDialog()
            mCoordinateItemAddDialog.arguments = bundle
            return mCoordinateItemAddDialog
        }

    }
    override fun getTitleResId()=R.string.schedules_add_coordinate

    override fun getTableName()="coordinate"
    override fun getColumn()="coordinatedesc"
    override fun putExtra(name: String?): Intent? {
        if (name?.isBlank()!=false) {
            ToastUtil.show("不能为空")
            return null
        }
        val transIntent=Intent()
        transIntent.putExtra(SchedulesBundleKey.COORDINATE_DESC, name)
        return transIntent
    }
}