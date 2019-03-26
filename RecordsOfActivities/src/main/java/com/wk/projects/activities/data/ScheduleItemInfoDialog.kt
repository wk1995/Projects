package com.wk.projects.activities.data

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.date.DateTime
import com.wk.projects.activities.ui.time.TimePickerCreator
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
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
                    ToastUtil.show(WkContextCompat.getString(R.string.common_str_update_successful), ToastUtil.LENGTH_SHORT)
                }
                super.onClick(v)
            }
            btnComSimpleDialogCancel -> super.onClick(v)
            tvScheduleStartTime,
            tvScheduleEndTime -> {
                TimePickerCreator.create(this)
            }
        }

    }

    override fun initVSView(vsView: View) {
        tvScheduleStartTime = vsView?.findViewById(R.id.tvScheduleStartTime) ?: return
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