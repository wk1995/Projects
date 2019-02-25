package com.wk.projects.schedules.data

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.date.DateTime
import com.wk.projects.schedules.ui.time.TimePickerCreator
import kotlinx.android.synthetic.main.schedules_activity_schedule_item_info.*
import org.litepal.LitePal
import timber.log.Timber
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 具体项目的详细信息
 * </pre>
 */
@Route(path = ARoutePath.ScheduleItemInfoActivity)
class ScheduleItemInfoActivity : BaseProjectsActivity(), View.OnClickListener, OnTimeSelectListener {
    private val itemId: Long by lazy {
        val itemId = intent?.extras?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1L)
                ?: throw Exception("itemId 有问题")
        if (itemId < 0) throw Exception("itemId 有问题")
        itemId
    }

    override fun initResLayId() = R.layout.schedules_activity_schedule_item_info

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        LitePal.findAsync(ScheduleItem::class.java, itemId).listen {
            Timber.d("42 $it")
            tvScheduleName.text = it.itemName
            tvScheduleStartTime.text = DateTime.getTimeString(it.startTime)
            tvScheduleEndTime.text = DateTime.getTimeString(it.endTime)
            etScheduleNote.setText(it.note)
        }
        tvScheduleStartTime.setOnClickListener(this)
        tvScheduleEndTime.setOnClickListener(this)
        btOk.setOnClickListener(this)
        btCancel.setOnClickListener(this)
        btEndTime.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btOk -> {
                val mContentValues = ContentValues()
                mContentValues.put(ScheduleItem.COLUMN_START_TIME,
                        DateTime.getTime(tvScheduleStartTime.text.toString()))
                mContentValues.put(ScheduleItem.COLUMN_END_TIME,
                        DateTime.getTime(tvScheduleEndTime.text.toString()))
                mContentValues.put(ScheduleItem.COLUMN_ITEM_NOTE,
                        etScheduleNote.text.toString())
                LitePal.updateAsync(ScheduleItem::class.java,
                        mContentValues, itemId).listen {
                    Toast.makeText(WkProjects.getContext(), "更新成功", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
            btCancel -> finish()
            tvScheduleStartTime,
            tvScheduleEndTime -> {
                TimePickerCreator.create(this, object : OnTimeSelectListener {
                    override fun onTimeSelect(date: Date?, view: View?) {
                        (v as? TextView)?.text = DateTime.getTimeString(date?.time)
                    }
                })
            }
            btEndTime->{
                tvScheduleEndTime.text=DateTime.getTimeString(System.currentTimeMillis())
            }
        }

    }

    override fun onTimeSelect(date: Date?, v: View?) {
        Timber.d("76 $v")
        (v as? TextView)?.text = DateTime.getTimeString(date?.time)
    }
}