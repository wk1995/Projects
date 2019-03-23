package com.wk.projects.activities.data.add

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.IFAFlag
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
 *      desc   : 新增项目
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
        tvComSimpleDialogTheme.setText(R.string.schedules_add_item)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk -> {
                val itemName = etAddItem.text.toString()
                saveItem(itemName)
            }
        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
        editTextHelper.showFocus(etAddItem, window)
        rvExistItem.layoutManager = LinearLayoutManager(mActivity)
        rvExistItem.adapter = mItemAdapter
        rvExistItem.addOnItemTouchListener(object : BaseSimpleClickListener() {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (view?.id) {
                    R.id.tvCommon -> {
                        //获取当前的值
                        val data = adapter?.data ?: return
                        val itemName = data[position] as? String
                        Timber.d("95  $itemName")
                        saveItem(itemName)
                        disMiss()
                    }
                }
            }
        })
        rvExistItem.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        Observable.just("select distinct itemname from scheduleitem")
                .map {
                    Timber.d("54 $it")
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
                }
        etAddItem.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mItemAdapter.search(s.toString())
            }
        })
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
                    val bundle = Bundle()
                    msg = WkContextCompat.getString(R.string.common_str_save_successful)
                    Timber.d("80 id is ${scheduleItem.baseObjId}")
                    bundle.putString(BundleKey.SCHEDULE_ITEM_NAME, itemName)
                    bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, scheduleItem.baseObjId)
                    iFa.communication(IFAFlag.SCHEDULE_ITEM_DIALOG, bundle)
                }
                else ->
                    msg = "未知原因,保存失败"
            }
            ToastUtil.show(msg,ToastUtil.LENGTH_LONG)
        }
    }
}