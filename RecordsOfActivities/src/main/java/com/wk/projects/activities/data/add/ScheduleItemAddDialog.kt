package com.wk.projects.activities.data.add

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.communication.constant.RequestCode
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination
import com.wk.projects.activities.communication.constant.RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName
import com.wk.projects.activities.communication.constant.ResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.common.ui.dialog.BaseSimpleDialog
import com.wk.projects.common.communication.eventBus.EventMsg
import com.wk.projects.common.helper.EditTextHelper
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.common.resource.WkContextCompat
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
class ScheduleItemAddDialog : BaseSimpleDialog() {
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

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        tvComSimpleDialogTheme.setText(
                when (targetRequestCode) {
                    RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination -> R.string.schedules_add_coordinate
                    RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName-> R.string.schedules_add_coordinate
                    RequestCode.ActivitiesMainFragment_ADD_ACTIVITIES->R.string.schedules_add_item
                    else -> R.string.schedules_update_name
                }
        )
    }

    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk -> {
                transferName(etAddItem.text.toString())
            }
        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
//        editTextHelper.showFocus(etAddItem, window)
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
        val table :String
        val attr:String
        when (targetRequestCode) {
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName ->{
                table="wkactivity"
                attr="itemname"
            }
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination->{
                table= "coordinate"
                attr="coordinatedesc"
            }
            else->{
                table= "scheduleitem"
                attr="itemname"
            }
        }
        Observable.just("select distinct $attr from $table")
                .map {
                    LitePal.findBySQL(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val items = ArrayList<String>()
                    Timber.d("60 ${it.position}")
                    while (it.moveToNext()) {
                        items.add(it.getString(0))
                    }
                    mItemAdapter.initData(items)
                    it.close()
                }
        etAddItem.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mItemAdapter.search(s.toString())
            }
        })
    }

    private fun transferName(name: String?) {
        if (name == null || name.isBlank())
            ToastUtil.show("不能为空")
        val transIntent = Intent()
        when (targetRequestCode) {
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName -> {

                transIntent.putExtra(SchedulesBundleKey.SCHEDULE_ITEM_NAME, name)

            }
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName -> {
                transIntent.putExtra(SchedulesBundleKey.CATEGORY_NAME, name)
            }
            RequestCode.ActivitiesMainFragment_ADD_ACTIVITIES -> {
                //会造成内存泄漏
                saveItem(name)
                return
            }
            RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination->{
                transIntent.putExtra(SchedulesBundleKey.COORDINATE_DESC, name)
            }
        }
        targetFragment?.onActivityResult(
                targetRequestCode,
                ResultCode.ScheduleItemAddDialog, transIntent)
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