package com.wk.projects.schedules.data.add

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.constant.IFAFlag.SCHEDULE_CATEGORY
import com.wk.projects.common.communication.constant.IFAFlag.SCHEDULE_ITEM_DIALOG
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.common.ui.WkToast
import com.wk.projects.schedules.R
import com.wk.projects.schedules.data.ScheduleCategory
import com.wk.projects.schedules.data.ScheduleItem
import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import kotlin.properties.Delegates

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
class ScheduleItemAddDialog : BaseSimpleDialog() {

    private val mItemAdapter by lazy { ScheduleItemNameListAdapter() }
    private lateinit var etAddItem: EditText
    private lateinit var rvExistItem: RecyclerView
    private var type by Delegates.notNull<Int>()

    companion object {
        const val BUNDLE_KEY_QUERY_TYPE="queryType"

        fun create(bundle: Bundle? = null): ScheduleItemAddDialog {
            val mScheduleItemDialogFragment = ScheduleItemAddDialog()
            mScheduleItemDialogFragment.arguments = bundle
            return mScheduleItemDialogFragment
        }
    }

    override fun initViewSubLayout() = R.layout.schedules_main_dialog_simple_add_item

    override fun bindView(savedInstanceState: Bundle?) {
        super.bindView(savedInstanceState)
        view?.findViewById<TextView>(R.id.tvComSimpleDialogTheme)?.setText(R.string.schedules_add_item)
        type=arguments?.getInt(BUNDLE_KEY_QUERY_TYPE)?:SCHEDULE_ITEM_DIALOG
        initData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnComSimpleDialogOk -> {
                val itemName = etAddItem.text.toString()
                when(type) {
                    SCHEDULE_CATEGORY -> {
                        val scheduleCategory=ScheduleCategory(itemName)
                        saveItemInDB(scheduleCategory)
                    }
                    else -> {
                        val scheduleItem=ScheduleItem(itemName)
                        saveItemInDB(scheduleItem)
                    }
                }

            }
        }
        super.onClick(v)
    }

    private fun initData(){
        Observable.just(getSelectString())
                .map {
                    Timber.d("54 $it")
                    LitePal.findBySQL(it)
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val items = ArrayList<String>()
                    Timber.d("60 ${it.position}")
                    while (it.moveToNext()) {
                        val itemName=it.getString(0)
                        if(itemName.trim().isNotEmpty()) {
                            items.add(itemName)
                        }
                    }
                    mItemAdapter.initData(items)
                }
    }

    private fun getSelectString():String{
        when(type){
            SCHEDULE_CATEGORY->{
               return "select distinct itemname from schedulecategory where itemname!=''"
            }
            else->{
                return "select distinct itemname from scheduleitem where itemname!=''"
            }
        }
    }


    override fun initVSView(vsView: View) {
        etAddItem = vsView.findViewById(R.id.etAddItem)
        rvExistItem = vsView.findViewById(R.id.rvExistItem)
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
                        selectItem(itemName)
                        disMiss()
                    }
                }
            }
        })
        rvExistItem.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        etAddItem.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mItemAdapter.search(s.toString())
            }
        })
    }

    private fun selectItem(itemName: String?) {
        if (itemName == null || itemName.isBlank()) {
            WkToast.showToast("项目需名字")
            return
        }
        responseSelectItem(itemName)
    }
    private fun saveItemInDB(litePalSupport : LitePalSupport){
        litePalSupport.saveAsync().listen {
            val msg: String = when (it) {
                true -> {
                    var itemName=WkStringConstants.STR_EMPTY
                    var itemId:Long?=null
                    if(litePalSupport is ScheduleCategory){
                        itemName=litePalSupport.itemName
                        itemId=litePalSupport.baseObjId
                    }

                    if(litePalSupport is ScheduleItem){
                        itemName=litePalSupport.itemName
                    }
                    responseSelectItem(itemName,itemId)
                    "保存成功"
                }
                else ->
                    "未知原因,保存失败"
            }
            WkToast.showToast(msg)
        }
    }

    /**传到主页面*/
    private fun responseSelectItem(itemName: String,itemId:Long?=null){
        val bundle = Bundle()
        bundle.putString(BundleKey.SCHEDULE_ITEM_NAME, itemName)
        if(itemId!=null) {
            bundle.putLong(BundleKey.SCHEDULE_ITEM_ID, itemId)
        }
        iFa.communication(type, bundle)
    }
}