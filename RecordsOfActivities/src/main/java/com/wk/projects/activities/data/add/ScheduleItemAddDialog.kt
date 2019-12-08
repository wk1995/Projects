package com.wk.projects.activities.data.add

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.communication.constant.RequestCode
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName
import com.wk.projects.activities.communication.constant.ResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.common.communication.eventBus.EventMsg
import com.wk.projects.common.helper.EditTextHelper
import com.wk.projects.common.helper.LogHelper
import com.wk.projects.common.helper.PhysicsHelper
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.dialog.BaseSimpleDialog
import com.wk.projects.common.ui.notification.ToastUtil
import kotlinx.android.synthetic.main.schedules_main_dialog_simple_add_item.*
import org.litepal.LitePal
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/25
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   : 新增项目
 * </pre>
 */
open class ScheduleItemAddDialog : BaseSimpleDialog() {
    private val mItemAdapter by lazy { ScheduleItemNameListAdapter() }
    private val editTextHelper by lazy { EditTextHelper.getInstance() }

    companion object {
        fun create(bundle: Bundle? = null): ScheduleItemAddDialog {
            val mScheduleItemDialogFragment = ScheduleItemAddDialog()
            mScheduleItemDialogFragment.arguments = bundle
            return mScheduleItemDialogFragment
        }
    }

    override fun initViewSubLayout() = R.layout.schedules_main_dialog_simple_add_item


    protected open fun getTitleResId():Int{
        return  when (targetRequestCode) {
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE,
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination -> R.string.schedules_add_coordinate
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName-> R.string.schedules_add_category
            RequestCode.ActivitiesMainFragment_ADD_ACTIVITIES->R.string.schedules_add_item
            else -> R.string.schedules_update_name
        }

    }

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                val rect= Rect()
                window.decorView.getWindowVisibleDisplayFrame(rect)
                Log.i("wk","rect.top: ${rect.top} rect.boot : ${rect.bottom} ")
                Log.i("wk","屏幕宽： ${PhysicsHelper.getInstance().getScreenWidth()} g高： ${PhysicsHelper.getInstance().getScreenHeight()}")
            }
        })
        tvComSimpleDialogTheme.setText(getTitleResId())
    }

    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk -> {
                transferName(etAddItem.text.toString())
            }
        }
        super.onClick(v)
    }

    private fun initRecycle(){
        rvExistItem.layoutManager = LinearLayoutManager(mActivity)
        rvExistItem.adapter = mItemAdapter
        rvExistItem.addOnItemTouchListener(object : BaseSimpleClickListener() {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                Timber.i("onItemChildClick")
                when (view?.id) {
                    R.id.tvCommon -> {
                        //获取当前的值
                        val data = adapter?.data ?: return
                        val itemName = data[position] as? String
                        Timber.d("95  $itemName")
                        transferName(itemName)
                        disMiss()
                    }
                }
            }
        })
        rvExistItem.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
    }

    protected open fun getTableName(): String {
        return when (targetRequestCode) {
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName -> {
                "wkactivity"
            }
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE,
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination -> {
                "coordinate"
            }
            else -> {
                "scheduleitem"
            }
        }
    }

    protected open fun getColumn(): String {
        return when (targetRequestCode) {
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE,
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination -> {
                "coordinatedesc"
            }
            else -> {
                "itemname"
            }
        }
    }

    private fun initRecycleData(){
        Observable.just("select distinct ${getColumn()} from ${getTableName()}")
                .map {
                    LitePal.findBySQL(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val items = ArrayList<String>()
                    LogHelper.TimberI("60 ${it.position} and ${it.count}")
                    while (it.moveToNext()) {
                        items.add(it.getString(0))
                    }
                    mItemAdapter.initData(items)
                    it.close()
                }
    }

    override fun initVSView(vsView: View) {
//        editTextHelper.showFocus(etAddItem, window)
        initRecycle()
        initRecycleData()
        etAddItem.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mItemAdapter.search(s.toString())
            }
        })
    }

    protected open fun putExtra(name:String?):Intent?{
        if (name?.isBlank()!=false) {
            ToastUtil.show("不能为空")
            return null
        }
        val transIntent = Intent()
        when (targetRequestCode) {
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName -> {
                transIntent.putExtra(SchedulesBundleKey.SCHEDULE_ITEM_NAME, name)
            }
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName -> {
                transIntent.putExtra(SchedulesBundleKey.CATEGORY_NAME, name)
            }
            RequestCode.ActivitiesMainFragment_ADD_ACTIVITIES -> {
                //TODO 会造成内存泄漏
                saveItem(name)
                return null
            }
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE,
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination->{
                transIntent.putExtra(SchedulesBundleKey.COORDINATE_DESC, name)
            }
        }
        return transIntent
    }

    private fun transferName(name: String?) {
        targetFragment?.onActivityResult(targetRequestCode,
                ResultCode.ScheduleItemAddDialog , putExtra(name) ?: return)
    }

    //保存数据库中
    private fun saveItem(itemName: String?) {
        if (itemName == null || itemName.isBlank()) {
            ToastUtil.show(WkContextCompat.getString(R.string.schedules_item_need_name), ToastUtil.LENGTH_LONG)
            return
        }
        val scheduleItem = ScheduleItem(itemName, System.currentTimeMillis())
        scheduleItem.saveAsync().listen {
            val msg: String?
            when (it) {
                true -> {
                    //传到主页面
                    msg = WkContextCompat.getString(R.string.common_str_save_successful)
                    Timber.d("80 id is ${scheduleItem.baseObjId}")
                    rxBus.post(ActivitiesMsg(EventMsg.ADD_ITEM, scheduleItem))
                }
                else ->
                    msg = "未知原因,保存失败"
            }
            ToastUtil.show(msg, ToastUtil.LENGTH_LONG)
        }
    }
}