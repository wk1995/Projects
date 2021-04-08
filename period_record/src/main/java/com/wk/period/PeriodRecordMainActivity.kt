package com.wk.period

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.wk.period.data.Period
import com.wk.period_record.R
import com.wk.period_record.databinding.PeriodRecordMainActivityBinding
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.SimpleOnlyEtDialog
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.time.date.DayUtil
import com.wk.projects.common.ui.WkToast
import org.litepal.LitePal
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PeriodRecordMainActivity : BaseProjectsActivity(), CalendarView.OnCalendarSelectListener,
        CalendarView.OnCalendarLongClickListener, SimpleOnlyEtDialog.SimpleOnlyEtDialogListener,
        CalendarView.OnCalendarRangeSelectListener {

    private val mBind by lazy {
        PeriodRecordMainActivityBinding.inflate(layoutInflater)
    }

    private val clPeriodRecordCalendar by lazy {
        mBind.clPeriodRecordCalendar
    }

    private val cvPeriodRecordCalendar by lazy {
        mBind.cvPeriodRecordCalendar
    }

    private val rvPeriodRecord by lazy {
        mBind.rvPeriodRecord
    }

    private val adapter by lazy {
        SimpleRvAdapter()
    }

    private val fabAddPeriodRecord by lazy {
        mBind.fabAddPeriodRecord
    }

    private var dialog: SimpleOnlyEtDialog? = null

    /**当前年份数*/
    private var mCurrentYear: Int = 0

    override fun initResLayId() = mBind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        mCurrentYear = cvPeriodRecordCalendar.curYear
        cvPeriodRecordCalendar.setOnCalendarSelectListener(this)
        cvPeriodRecordCalendar.setOnCalendarLongClickListener(this)
        cvPeriodRecordCalendar.setOnCalendarRangeSelectListener(this)
        fabAddPeriodRecord.setOnClickListener(this)
        initRv()
    }

    private fun initRv() {
        rvPeriodRecord.layoutManager = LinearLayoutManager(this)
        rvPeriodRecord.adapter = adapter
    }

    override fun beforeSetContentView() {
        supportActionBar?.hide()
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        if (calendar == null) {
            WkToast.showToast("calendar is null")
            return
        }
        val month = calendar.month
        val day = calendar.day
        val year = calendar.year
        WkToast.showToast("$month $day 号 : isClick: $isClick")
        val selectDayStart = DayUtil.getDayStart(calendar.timeInMillis)
        val selectDayEnd = DayUtil.getDayEnd(calendar.timeInMillis)
//        cvPeriodRecordCalendar.setRange()
    }

    override fun onCalendarLongClickOutOfRange(calendar: Calendar?) {
    }


   private var period:Period?=null

    override fun onCalendarLongClick(calendar: Calendar?) {
        Observable.create(Observable.OnSubscribe<List<Period>> { t ->
            //判断是否有开始时间的经期
            val periods = LitePal.where("").find(Period::class.java)
            t?.onNext(periods)
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (dialog != null) {
                        dialog = null
                    }
                    val bundle = Bundle()
                    bundle.putInt(BaseSimpleDialog.THEME_STR_ID, R.string.period_recode_add_hint)
                    //有的花 换时间或者设置结束时间
                    if (!it.isNullOrEmpty()) {
                        period=it[0]
                        bundle.putInt(BaseSimpleDialog.OK_STR_ID, R.string.period_recode_set_start)
                        bundle.putInt(BaseSimpleDialog.CANCEL_STR_ID, R.string.period_recode_set_end)
                    } else {  //没有的话则询问是否设置为开始时间

                    }
                    dialog = SimpleOnlyEtDialog.create(bundle, this)
                    dialog?.show(this)
                }


    }

    override fun ok(textString: String?): Boolean {
        WkToast.showToast("ok")
        val currentTime=System.currentTimeMillis()
        if(period!=null){
            period?.startTime=currentTime
        }else{
            period=Period(currentTime)
        }
        Observable.create(Observable.OnSubscribe<Boolean?> { t ->
            t?.onNext(period?.save())
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it!=true){
                        WkToast.showToast("保存失败")
                    }else{
                        WkToast.showToast("保存成功")
                    }
                }
        return false
    }

    override fun cancel(): Boolean {
        WkToast.showToast("cancel")
        if(period!=null){
            period?.endTime=System.currentTimeMillis()
            Observable.create(Observable.OnSubscribe<Boolean?> { t ->
                t?.onNext(period?.save())
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if(it!=true){
                            WkToast.showToast("保存失败")
                        }else{
                            WkToast.showToast("保存成功")
                        }
                    }
        }

        return false
    }

    override fun onClick(v: View?) {


    }

    override fun onCalendarSelectOutOfRange(calendar: Calendar?) {
        WkLog.i("onCalendarSelectOutOfRange")

    }

    override fun onSelectOutOfRange(calendar: Calendar?, isOutOfMinRange: Boolean) {
        WkLog.i("onSelectOutOfRange")
    }

    override fun onCalendarRangeSelect(calendar: Calendar?, isEnd: Boolean) {
        WkLog.i("onCalendarRangeSelect")

    }
}