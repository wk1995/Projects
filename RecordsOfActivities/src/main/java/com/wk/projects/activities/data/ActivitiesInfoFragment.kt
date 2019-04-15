package com.wk.projects.activities.data

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.activities.communication.constant.RequestCode
import com.wk.projects.activities.communication.constant.ResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.add.ScheduleItemAddDialog
import com.wk.projects.activities.data.add.adapter.ActivitiesBean
import com.wk.projects.activities.data.add.adapter.CategoryListAdapter
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.communication.eventBus.EventMsg.Companion.ADD_ITEM
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.date.DateTime
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import kotlinx.android.synthetic.main.schedules_activity_schedule_item_info.*
import org.litepal.LitePal
import timber.log.Timber
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/26
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 具体项目的详细信息
 * </pre>
 */
@Route(path = ARoutePath.ActivitiesInfoFragment)
class ActivitiesInfoFragment : BaseFragment(),
        View.OnClickListener, OnTimeSelectListener {

    companion object {
        const val OPERATION_ADD = "OPERATION_ADD"
        const val OPERATION_MODIFY = "OPERATION_MODIFY"
    }

    private val itemId: Long by lazy {
        arguments?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, -1L) ?: -1
    }
    //改变后的parentId
    private var newCategoryId: Long? = -1L
    private var currentId: Long? = -1L
    private val mCategoryListAdapter by lazy { CategoryListAdapter() }
    private val cateGoryList by lazy { ArrayList<ActivitiesBean>() }
    private val mScheduleItemAddDialog by lazy {
        ScheduleItemAddDialog.create()
    }

    override fun initResLay() = R.layout.schedules_activity_schedule_item_info
    var i = 1
    override fun initView() {
        super.initView()
        if (itemId >= 0)
            LitePal.findAsync(ScheduleItem::class.java, itemId).listen {
                if (it == null) return@listen
                Timber.d("42 $it")
                currentId = it.parentId
                newCategoryId = currentId
                Timber.d("currentId:  $currentId")
                tvScheduleName.text = it.itemName
                tvScheduleStartTime.text = DateTime.getDateString(it.startTime)
                tvScheduleEndTime.text = DateTime.getDateString(it.endTime)
                etScheduleNote.setText(it.note)
                if (currentId != null)
                    LitePal.findAsync(WkActivity::class.java, currentId!!).listen {
                        tvItemClassName.text = it?.itemName ?: ""
                    }

            }
        else
            tvScheduleStartTime.text = DateTime.getDateString(System.currentTimeMillis())
        initClick()
        rvItemClass.layoutManager = LinearLayoutManager(_mActivity)
        rvItemClass.adapter = mCategoryListAdapter
        findRootCategory()
        rvItemClass.addOnItemTouchListener(object : BaseSimpleClickListener() {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                //展开
                Timber.i("onItemChildClick position:  $position")
                val wkActivityBean = adapter?.getItem(position) as ActivitiesBean
                val wkActivity = wkActivityBean.wkActivity ?: return
                //如果没有下一层级的
                if (!wkActivityBean.hasSubItem()) {
                    val wkActivityId = wkActivity.baseObjId
                    LitePal.where("parentId=?", wkActivityId.toString())
                            .findAsync(WkActivity::class.java).listen {
                                Timber.i("size: ${it.size}")
                                it.forEach {
                                    wkActivityBean.addSubItem(ActivitiesBean(it, wkActivityBean.wkLevel + 1, wkActivityBean))
                                }
                                wkActivityBean.addSubItem(ActivitiesBean(null, wkActivityBean.wkLevel + 1, wkActivityBean))
                                adapter.expand(position)
                            }

                } else {//如果有
                    if (wkActivityBean.isExpanded)
                        adapter.collapse(position)
                    else
                        adapter.expand(position)
                }
            }

            //选择选中的类别
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                if (adapter !is CategoryListAdapter) return
                Timber.i("onItemClick position:  $position")
                currentBean = adapter.getItem(position) as ActivitiesBean
                val wkActivity = currentBean?.wkActivity
                //说明是个额外的item
                if (wkActivity == null) {
                    ToastUtil.show("增加")
                    mScheduleItemAddDialog.setTargetFragment(this@ActivitiesInfoFragment, RequestCode.ActivitiesInfoFragment_CategoryName)
                    mScheduleItemAddDialog.show(fragmentManager)

                } else
                    ToastUtil.show("选中 ${wkActivity.itemName}")

            }

            override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                //删除
                super.onItemLongClick(adapter, view, position)

            }
        })
    }

    private var currentBean: ActivitiesBean? = null

    override fun onClick(v: View?) {
        when (v) {
        //修改项目名称
            tvModifyScheduleName -> {
                mScheduleItemAddDialog.setTargetFragment(this, RequestCode.ActivitiesInfoFragment_itemName)
                mScheduleItemAddDialog.show(fragmentManager)
            }
        //返回
            btOk -> {
                //修改信息
                if (tvItemClassName.text.isBlank()) {
                    ToastUtil.show("请选择类别")
                    return
                }
                val startTime = DateTime.getDateLong(tvScheduleStartTime.text.toString())
                val endTime = DateTime.getDateLong(tvScheduleEndTime.text.toString())
                val note = etScheduleNote.text.toString()

                if (itemId >= 0) {
                    val mContentValues = ContentValues()
                    mContentValues.put(ScheduleItem.SCHEDULE_START_TIME,
                            startTime)
                    mContentValues.put(ScheduleItem.SCHEDULE_END_TIME,
                            endTime)
                    mContentValues.put(ScheduleItem.SCHEDULE_ITEM_NOTE,
                            note)
                    Timber.d("newCategoryId : $newCategoryId    currentId:  $currentId")
                    if (newCategoryId != currentId)
                        mContentValues.put(ScheduleItem.SCHEDULE_PARENT_ID,
                                newCategoryId)
                    LitePal.updateAsync(ScheduleItem::class.java,
                            mContentValues, itemId).listen {
                        Timber.i("保存的个数 $it")
                        ToastUtil.show(WkContextCompat.getString(R.string.common_str_update_successful), ToastUtil.LENGTH_SHORT)
                        if (arguments == null)
                            arguments = Bundle()
                        arguments?.putLong(SchedulesBundleKey.SCHEDULE_START_TIME, startTime)
                        arguments?.putLong(SchedulesBundleKey.SCHEDULE_END_TIME, endTime)
                        arguments?.putString(SchedulesBundleKey.SCHEDULE_OPERATION, OPERATION_MODIFY)
                        setFragmentResult(ResultCode.ResultCode_ScheduleItemInfoActivity, arguments)
                        pop()
                    }
                } else {
                    //增加项目
                    val mScheduleItemName = tvScheduleName.text.toString()
                    if (mScheduleItemName.isBlank()) {
                        ToastUtil.show("活动名称为空")
                        return
                    }
                    val mScheduleItem = ScheduleItem(mScheduleItemName,
                            startTime, endTime,
                            etScheduleNote.text.toString(), newCategoryId)
                    mScheduleItem.saveAsync().listen {
                        if (it) {
                            rxBus.post(ActivitiesMsg(ADD_ITEM, mScheduleItem))
                            pop()
                        } else {
                            ToastUtil.show(WkContextCompat.getString(R.string.common_str_save_failed))
                        }

                    }
                }
            }
        //选择时间
            tvScheduleStartTime,
            tvScheduleEndTime -> {
                TimePickerCreator.create(_mActivity, this)
            }

        //快捷方式直接设置当前时间
            btEndTime -> {
                tvScheduleEndTime.text = DateTime.getDateString(System.currentTimeMillis())
            }
            btStartTime -> {
                tvScheduleStartTime.text = DateTime.getDateString(System.currentTimeMillis())
            }
        }
    }

    override fun onTimeSelect(date: Date?, v: View?) {
        Timber.d("76 $v")
        (v as? TextView)?.text = DateTime.getDateString(date?.time)
    }

    //取出所有类型
    private fun findRootCategory() {
        //先取出最大的类别
        LitePal.where("parentId=?", WkActivity.NO_PARENT.toString())
                .findAsync(WkActivity::class.java).listen {
                    it.forEach {
                        cateGoryList.add(ActivitiesBean(it, 0))
                    }
                    /* mCategoryListAdapter.addFooterView(
                             getFooterView(R.layout.activities_category_list_item, this, true)
                     )*/
                    mCategoryListAdapter.setNewData(cateGoryList)
                }
    }

    //注册各种监听
    private fun initClick() {
        tvScheduleStartTime.setOnClickListener(this)
        tvScheduleEndTime.setOnClickListener(this)
        btOk.setOnClickListener(this)
        btEndTime.setOnClickListener(this)
        btStartTime.setOnClickListener(this)
        tvModifyScheduleName.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode :  $requestCode  resultCode:  $resultCode ")
        if (resultCode == ResultCode.ScheduleItemAddDialog)
            when (requestCode) {
                RequestCode.ActivitiesInfoFragment_itemName ->
                    tvScheduleName.text = data?.getStringExtra(SchedulesBundleKey.SCHEDULE_ITEM_NAME)
                RequestCode.ActivitiesInfoFragment_CategoryName -> {
                    val categoryName = data?.getStringExtra(SchedulesBundleKey.CATEGORY_NAME)
                            ?: throw Exception("ActivitiesInfoFragment category name is null ")
                    if (categoryName.isBlank()) {
                        return
                    }
                    Timber.i("增加的类别名称： $categoryName")
                    //上一层
                    val parentBean = currentBean?.parentBean
                    //说明不是根类别
                    if (parentBean != null) {
                        val newWkActivity = WkActivity(categoryName, System.currentTimeMillis(), parentBean.wkActivity?.baseObjId
                                ?: WkActivity.NO_PARENT)
                        newWkActivity.saveAsync().listen {
                            if (!it) {
                                ToastUtil.show("新建类别失败")
                                return@listen
                            }
                            val parentPosition = mCategoryListAdapter.getParentPosition(currentBean!!)
                   /*         Timber.i("parent position:  parentPosition")
                            val parentPosition = mCategoryListAdapter.data.indexOf(parentBean)*/
                            Timber.i("parent name: ${parentBean.wkActivity?.itemName} ")
                            val subSize = parentBean.subItems.size
                            parentBean.addSubItem(subSize - 1,
                                    ActivitiesBean(
                                            newWkActivity,
                                            parentBean.wkLevel + 1,
                                            parentBean))
                            i++
                            if (parentBean.isExpanded) {
                                mCategoryListAdapter.collapse(parentPosition)
                                mCategoryListAdapter.expand(parentPosition)
                            }
                        }

                    } else {
                        //这是根类别
                    }


                }
            }

    }

}