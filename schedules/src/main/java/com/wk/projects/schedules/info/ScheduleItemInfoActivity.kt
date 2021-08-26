package com.wk.projects.schedules.info

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.constant.NumberConstants.number_long_one_Negative
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.ui.WkToast
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.constant.ActivityRequestCode.RequestCode_SchedulesMainActivity
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.data.ScheduleCategory
import com.wk.projects.schedules.data.add.ScheduleItemAddDialog
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
class ScheduleItemInfoActivity : BaseProjectsActivity(), View.OnClickListener, OnTimeSelectListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {
    companion object {
        /**无效的 id*/
        private const val INVALID_ITEM_ID = number_long_one_Negative
    }

    /**当前schedule id*/
    private var itemId = INVALID_ITEM_ID

    /**改变后的parentId*/
    private var newCategoryId: Long? = -1L
    private var currentId: Long? = -1L

    private var currentSchedule: ScheduleItem = ScheduleItem()

    /**当前的类别*/
    private var currentCategoryId: Long = INVALID_ITEM_ID

    private val mScheduleInfoAdapter by lazy { ScheduleInfoAdapter() }

    override fun initResLayId() = R.layout.schedules_activity_schedule_item_info

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        itemId = intent?.extras?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, INVALID_ITEM_ID)
                ?: INVALID_ITEM_ID
        if (itemId == INVALID_ITEM_ID) {
            return
        }
        initView()
        findTargetSchedule()
        initClick()
    }

    private fun initView() {
        initInfoRv()
    }

    /**项目信息的视图*/
    private fun initInfoRv() {
        rvScheduleInfo.layoutManager = GridLayoutManager(this, 3)
        rvScheduleInfo.adapter = mScheduleInfoAdapter
    }

    private fun findTargetSchedule() {
        LitePal.findAsync(ScheduleItem::class.java, itemId).listen { it: ScheduleItem? ->
            if (it == null) {
                return@listen
            }
            currentSchedule = it
            currentCategoryId = it.categoryId

            WkLog.d("currentId:  $currentId")
            tvScheduleName.text = it.itemName
            tvScheduleDate.text = DateTime.getDateString(it.startTime, "yyyy年MM月dd日")
            val data = ArrayList<Pair<String, String>>()
            val startTime = DateTime.getDateString(it.startTime, "HH:mm:ss")
            val endTime = DateTime.getDateString(it.endTime, "HH:mm:ss")
            data.add(Pair(startTime, "起始时间"))
            data.add(Pair(endTime, "结束时间"))
            etScheduleNote.setText(it.note)
            mScheduleInfoAdapter.addData(data)
            LitePal.findAsync(ScheduleCategory::class.java, currentCategoryId).listen {
                val categoryName = it?.itemName ?: WkStringConstants.STR_EMPTY
                mScheduleInfoAdapter.addData(Pair(categoryName, "类别"))
            }

        }
    }


    override fun onClick(v: View?) {
        when (v) {
            tvScheduleName -> {
                ScheduleItemAddDialog.create().show(supportFragmentManager)
            }
            ivTitleBack->{
                finish()
            }
            btTitleSave,
            btScheduleSave -> {
                currentSchedule.note=etScheduleNote.text.toString()
                currentSchedule.saveAsync().listen {
                    if(it){
                        setResult(RequestCode_SchedulesMainActivity)
                        finish()
                    }else{
                        WkToast.showToast("保存失败")
                    }
                }


            }
            /*
               btAddCategory -> {
                   //将要添加的类别
                   val addCategoryName = etAddCategory.text.toString().trim()
                   if (addCategoryName != "") {
                       //如果类别名不为空，先判断数据库中是否有相同名字的WkActivity
                       LitePal.where("itemName=?", addCategoryName)
                               .findAsync(WkActivity::class.java)
                               .listen {
                                   if (it.size > 0) {
                                       Toast.makeText(this@ScheduleItemInfoActivity,
                                               "$addCategoryName 已存在，添加失败",
                                               Toast.LENGTH_SHORT).show()
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
                                               Toast.makeText(this@ScheduleItemInfoActivity,
                                                       "保存失败",
                                                       Toast.LENGTH_SHORT).show()
                                       }

                                   }
                               }
                   }
               }*/
        }

    }


    override fun communication(flag: Int, bundle: Bundle?, any: Any?) {
        val itemName=bundle?.getString(BundleKey.SCHEDULE_ITEM_NAME)?:WkStringConstants.STR_EMPTY
        currentSchedule.itemName=itemName
        tvScheduleName.text=itemName
    }

    override fun onTimeSelect(date: Date?, v: View?) {
        Timber.d("76 $v")
        (v as? TextView)?.text = DateTime.getDateString(date?.time)
    }


    /**注册各种监听*/
    private fun initClick() {
        btScheduleSave.setOnClickListener(this)
        tvScheduleName.setOnClickListener(this)
        tvScheduleDate.setOnClickListener(this)
        btTitleSave.setOnClickListener(this)
        ivTitleBack.setOnClickListener(this)
        mScheduleInfoAdapter.onItemClickListener = this
        mScheduleInfoAdapter.onItemLongClickListener = this
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (position) {
            0, 1 -> {
                TimePickerCreator.create(this) { date, _ ->
                    val starTv = view?.findViewById<TextView>(R.id.tvTopCommonLlIvVTv)
                    if (position == 0) {
                        currentSchedule.startTime = date?.time ?: NumberConstants.number_long_zero
                    } else {
                        currentSchedule.endTime = date?.time ?: NumberConstants.number_long_zero
                    }
                    starTv?.text = DateTime.getDateString(date?.time, "HH:mm:ss")
                }
            }

        }

    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
        when (position) {
            0, 1 -> {
                val starTv = view?.findViewById<TextView>(R.id.tvTopCommonLlIvVTv)
                val currentTime = System.currentTimeMillis()
                if (position == 0) {
                    currentSchedule.startTime = currentTime
                } else {
                    currentSchedule.endTime = currentTime
                }
                starTv?.text = DateTime.getDateString(currentTime, "HH:mm:ss")
            }
        }
        return true
    }
}