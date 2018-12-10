package com.wk.projects.schedules.data.add

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.wk.projects.common.BaseSimpleDialogFragment
import com.wk.projects.common.communication.IRvClickListener
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.IFAFlag
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey.SCHEDULE_ITEM_ID
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.ui.recycler.SchedulesAddItemAdapter
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
 *      desc   :
 * </pre>
 */
class ScheduleItemDialogFragment : BaseSimpleDialogFragment(), IRvClickListener {
    private val itemList by lazy {
        ArrayList<String>()
    }
    private val itemAdapter by lazy { SchedulesAddItemAdapter(itemList, this) }

    private lateinit var rvCommon: RecyclerView

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        tvComSimpleDialogTheme.setText(R.string.schedules_add_item)
    }


    override fun initVSView(vsView: View) {
        rvCommon = vsView.findViewById(R.id.rvCommon)
        rvCommon.layoutManager = LinearLayoutManager(mActivity)
        rvCommon.adapter = itemAdapter
        rvCommon.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        Observable.just("select distinct itemname from scheduleitem")
                .map {
                    Timber.d("54 $it")
                    LitePal.findBySQL(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val items=ArrayList<String>()
                    Timber.d("60 ${it.position}")
                    while (it.moveToNext()) {
                        items.add(it.getString(0))
                    }
                    itemAdapter.addItems(items)
                }

    }

    override fun onItemClick(bundle: Bundle?, vararg any: Any?) {
        val itemName = bundle?.getString(BundleKey.SCHEDULE_ITEM_NAME) ?: return
        //插入数据库中
        val scheduleItem = ScheduleItem(itemName, System.currentTimeMillis())
        scheduleItem.saveAsync().listen {
            val msg: String
            when (it) {
                true -> {
                    //传到主页面
                    msg = "保存成功"
                    Timber.d("80 id is ${scheduleItem.baseObjId}")
                    bundle.putLong(SCHEDULE_ITEM_ID,scheduleItem.baseObjId)
                    iFa.communication(IFAFlag.SCHEDULE_ITEM_DIALOG, bundle)
                }
                else ->
                    msg = "未知原因,保存失败"
            }
            Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show()

        }
        disMiss()
    }

    override fun onItemLongClick(bundle: Bundle?, vararg any: Any?) {
    }

    override fun initViewSubLayout() = R.layout.common_only_recycler
}