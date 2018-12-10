package com.wk.projects.schedules.data.add

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.wk.projects.common.BaseSimpleDialogFragment
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.IFAFlag
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.data.ScheduleItem

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/25
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 新增数据库没有的项目数据
 * </pre>
 */
class ScheduleNewItemDialogFragment : BaseSimpleDialogFragment() {

    lateinit var etAddItem: EditText

    companion object {
        fun create(bundle: Bundle?): ScheduleNewItemDialogFragment {
            val mScheduleItemDialogFragment = ScheduleNewItemDialogFragment()
            mScheduleItemDialogFragment.arguments = bundle
            return mScheduleItemDialogFragment
        }
    }

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        tvComSimpleDialogTheme.setText(R.string.schedules_add_item)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk -> {
                val itemName = etAddItem.text.toString()
                if (itemName.isBlank()) {
                    Toast.makeText(mActivity, "项目需名字", Toast.LENGTH_LONG).show()
                    return
                }
                //插入数据库中
                val scheduleItem = ScheduleItem(itemName, System.currentTimeMillis())
                scheduleItem.saveAsync().listen {
                    val msg: String =
                            if (it) {
                                //传到主页面
                                val bundle = Bundle()
                                bundle.putString(BundleKey.SCHEDULE_ITEM_NAME, itemName)
                                bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, scheduleItem.baseObjId)
                                iFa.communication(IFAFlag.SCHEDULE_NEW_ITEM_DIALOG, bundle)
                                "保存成功"
                            } else "未知原因,保存失败"
                    Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show()
                }

            }
        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
        etAddItem = vsView.findViewById(R.id.etAddItem)
    }

    override fun initViewSubLayout() = R.layout.schedules_main_dialog_simple_add_item
}