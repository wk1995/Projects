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
import com.wk.projects.activities.data.`class`.CategoryAdapter
import com.wk.projects.activities.data.add.ScheduleItemAddDialog
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
    private val mCategoryAdapter by lazy { CategoryAdapter() }

    override fun initResLay() = R.layout.schedules_activity_schedule_item_info
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
        initClick()
        rvItemClass.layoutManager = LinearLayoutManager(_mActivity)
        rvItemClass.adapter = mCategoryAdapter
        findAllCategory()
        rvItemClass.addOnItemTouchListener(object : BaseSimpleClickListener() {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (adapter is CategoryAdapter) {
                    val data = adapter.data
                    when (view?.id) {
                        R.id.tvCommon -> {
                            tvItemClassName.text = data[position].itemName
                            newCategoryId = data[position].baseObjId
                        }
                    }
                }

            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
        //修改项目名称
            tvModifyScheduleName -> {
                val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
                mScheduleItemAddDialog.setTargetFragment(this, RequestCode.RequestCode_ActivitiesInfoFragment)
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
            btAddCategory -> {
                //将要添加的类别
                val addCategoryName = etAddCategory.text.toString().trim()
                if (addCategoryName.isNotBlank())
                //如果类别名不为空，先判断数据库中是否有相同名字的WkActivity
                    LitePal.where("itemName=?", addCategoryName)
                            .findAsync(WkActivity::class.java)
                            .listen {
                                if (it.size > 0) {
                                    ToastUtil.show("$addCategoryName 已存在，添加失败", ToastUtil.LENGTH_SHORT)
                                    newCategoryId = it[0].baseObjId
                                    Timber.d("newCategoryId: $newCategoryId")
                                    etAddCategory.setText(addCategoryName)
                                } else {
                                    //不存在的话，保存一新的WkActivity
                                    val newWkActivity = WkActivity(addCategoryName)
                                    newWkActivity.saveAsync().listen {
                                        if (it) {
                                            newCategoryId = newWkActivity.baseObjId
                                            Timber.d("newCategoryId: $newCategoryId")
                                            tvItemClassName.text = (addCategoryName)
                                            mCategoryAdapter.data.add(newWkActivity)
                                            mCategoryAdapter.notifyItemChanged(mCategoryAdapter.data.size - 1)
                                        } else
                                            ToastUtil.show(WkContextCompat.getString(R.string.common_str_save_failed), ToastUtil.LENGTH_SHORT)

                                    }

                                }
                            }

            }
        }

    }

    override fun onTimeSelect(date: Date?, v: View?) {
        Timber.d("76 $v")
        (v as? TextView)?.text = DateTime.getDateString(date?.time)
    }

    //取出所有类型
    private fun findAllCategory() {
        LitePal.findAllAsync(WkActivity::class.java).listen {
            Timber.d("现有的类别数： ${it.size}")
            it.forEach {
                Timber.d("现有的类别： ${it.itemName}")
            }
            mCategoryAdapter.setNewData(it)
        }
    }

    //注册各种监听
    private fun initClick() {
        tvScheduleStartTime.setOnClickListener(this)
        tvScheduleEndTime.setOnClickListener(this)
        btOk.setOnClickListener(this)
        btEndTime.setOnClickListener(this)
        btAddCategory.setOnClickListener(this)
        btStartTime.setOnClickListener(this)
        tvModifyScheduleName.setOnClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode :  $requestCode  resultCode:  $resultCode ")
        if (requestCode == RequestCode.RequestCode_ActivitiesInfoFragment &&
                resultCode == ResultCode.ScheduleItemAddDialog) {
            tvScheduleName.text = data?.getStringExtra(SchedulesBundleKey.SCHEDULE_ITEM_NAME)
        }
    }

}