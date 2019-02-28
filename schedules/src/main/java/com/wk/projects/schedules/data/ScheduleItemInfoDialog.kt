package com.wk.projects.schedules.data

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.date.DateTime
import com.wk.projects.schedules.ui.time.TimePickerCreator
import org.litepal.LitePal
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class ScheduleItemInfoDialog : BaseSimpleDialog(), OnTimeSelectListener {

    companion object {
        fun create(bundle: Bundle? = null): ScheduleItemInfoDialog {
            val mScheduleItemInfoDialog = ScheduleItemInfoDialog()
            mScheduleItemInfoDialog.arguments = bundle
            return mScheduleItemInfoDialog
        }
    }

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        isCancelable = false// 设置点击屏幕Dialog不消失
    }

    private val itemId: Long by lazy {
        val itemId = arguments?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1L)
                ?: throw Exception("itemId 有问题")
        if (itemId < 0) throw Exception("itemId 有问题")
        itemId
    }
    private lateinit var tvScheduleStartTime: TextView
    private lateinit var tvScheduleEndTime: TextView


    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk -> {
                val mContentValues = ContentValues()
                mContentValues.put(ScheduleItem.COLUMN_START_TIME,
                        DateTime.getDateLong(tvScheduleStartTime.text.toString()))
                mContentValues.put(ScheduleItem.COLUMN_END_TIME,
                        DateTime.getDateLong(tvScheduleEndTime.text.toString()))
                LitePal.updateAsync(ScheduleItem::class.java,
                        mContentValues, itemId).listen {
                    Toast.makeText(WkProjects.getContext(), "更新成功", Toast.LENGTH_SHORT).show()
                }
                super.onClick(v)
            }
            btnComSimpleDialogCancel->super.onClick(v)
            tvScheduleStartTime,
            tvScheduleEndTime -> {
                TimePickerCreator.create(mActivity, this)
            }
        }

    }

    override fun initVSView(vsView: View) {
        tvScheduleStartTime = vsView.findViewById(R.id.tvScheduleStartTime)
        tvScheduleStartTime.setOnClickListener(this)
        tvScheduleEndTime = vsView.findViewById(R.id.tvScheduleEndTime)
        tvScheduleEndTime.setOnClickListener(this)
        LitePal.findAsync(ScheduleItem::class.java, itemId).listen {
            tvComSimpleDialogTheme.text = it.itemName
            tvScheduleStartTime.text = DateTime.getDateString(it.startTime)
            tvScheduleEndTime.text = DateTime.getDateString(it.endTime)
        }

    }

    override fun initViewSubLayout() = R.layout.schedules_dialog_item_info_
    override fun onTimeSelect(date: Date?, v: View?) {
        (v as TextView).text = DateTime.getDateString(date?.time)
    }
}