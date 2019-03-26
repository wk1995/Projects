package com.wk.projects.activities.data

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.ActivityResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.`class`.CategoryAdapter
import com.wk.projects.activities.date.DateTime
import com.wk.projects.activities.date.DateTime.getDateLong
import com.wk.projects.activities.ui.time.TimePickerCreator
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
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
    //改变后的parentId
    private var newCategoryId: Long? = -1L
    private var currentId: Long? = -1L
    private val mCategoryAdapter by lazy { CategoryAdapter() }


    override fun initResLay() = R.layout.schedules_activity_schedule_item_info

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        LitePal.findAsync(ScheduleItem::class.java, itemId).listen {
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
        findAllCategory()
        initClick()
        rvItemClass.layoutManager = LinearLayoutManager(this)
        rvItemClass.adapter = mCategoryAdapter
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

            btOk -> {
                val startTime = tvScheduleStartTime.text.toString()
                val endTime = tvScheduleEndTime.text.toString()
                val mContentValues = ContentValues()
                mContentValues.put(ScheduleItem.COLUMN_START_TIME,
                        DateTime.getDateLong(startTime))
                mContentValues.put(ScheduleItem.COLUMN_END_TIME,
                        DateTime.getDateLong(endTime))
                mContentValues.put(ScheduleItem.COLUMN_ITEM_NOTE,
                        etScheduleNote.text.toString())
                Timber.d("newCategoryId : $newCategoryId    currentId:  $currentId")
                if (newCategoryId != currentId)
                    mContentValues.put(ScheduleItem.COLUMN_PARENT_ID,
                            newCategoryId)
                LitePal.updateAsync(ScheduleItem::class.java,
                        mContentValues, itemId).listen {
                    Timber.i("保存的个数 $it")
                    ToastUtil.show(WkContextCompat.getString(R.string.common_str_update_successful), ToastUtil.LENGTH_SHORT)
                    val bundle=Bundle()
                    intent.putExtra(ScheduleItem.COLUMN_START_TIME, getDateLong(startTime))
                    intent.putExtra(ScheduleItem.COLUMN_END_TIME, getDateLong(endTime))
                    setResult(ActivityResultCode.ResultCode_ScheduleItemInfoActivity, intent)
                    finish()
                }

            }
            btCancel -> finish()
            tvScheduleStartTime,
            tvScheduleEndTime -> {
                TimePickerCreator.create(this, object : OnTimeSelectListener {
                    override fun onTimeSelect(date: Date?, view: View?) {
                        (v as? TextView)?.text = DateTime.getDateString(date?.time)
                    }
                })
            }

        //快捷方式直接设置当前时间
            btEndTime -> {
                tvScheduleEndTime.text = DateTime.getDateString(System.currentTimeMillis())
            }
            btAddCategory -> {
                //将要添加的类别
                val addCategoryName = etAddCategory.text.toString().trim()
                if (addCategoryName != "") {
                    //如果类别名不为空，先判断数据库中是否有相同名字的WkActivity
                    LitePal.where("itemName=?", addCategoryName)
                            .findAsync(WkActivity::class.java)
                            .listen {
                                if (it.size > 0) {
                                    ToastUtil.show("$addCategoryName 已存在，添加失败",ToastUtil.LENGTH_SHORT)
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
                                            ToastUtil.show(WkContextCompat.getString(R.string.common_str_save_failed),ToastUtil.LENGTH_SHORT)

                                    }

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
            mCategoryAdapter.setNewData(it)
        }
    }

    //注册各种监听
    private fun initClick() {
        tvScheduleStartTime.setOnClickListener(this)
        tvScheduleEndTime.setOnClickListener(this)
        btOk.setOnClickListener(this)
        btCancel.setOnClickListener(this)
        btEndTime.setOnClickListener(this)
        btAddCategory.setOnClickListener(this)
    }
}