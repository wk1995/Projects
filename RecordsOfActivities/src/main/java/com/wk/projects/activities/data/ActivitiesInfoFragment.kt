package com.wk.projects.activities.data

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
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
import com.wk.projects.activities.data.add.CategoryDialog
import com.wk.projects.activities.data.add.ScheduleItemAddDialog
import com.wk.projects.activities.data.add.adapter.ActivitiesBean
import com.wk.projects.activities.data.add.adapter.CategoryListAdapter
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.communication.eventBus.EventMsg.Companion.ADD_ITEM
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.date.DateTime
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import kotlinx.android.synthetic.main.schedules_activity_schedule_item_info.*
import org.litepal.LitePal
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
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

    private val locationManager by lazy { _mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private val itemId: Long by lazy {
        arguments?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, ScheduleItem.SCHEDULE_NO_PARENT_ID)
                ?: ScheduleItem.SCHEDULE_NO_PARENT_ID
    }
    //改变后的parentId
    private var newCategoryId: Long? = -1L
    private var currentId: Long? = -1L
    private val mCategoryListAdapter by lazy { CategoryListAdapter() }
    private val cateGoryList by lazy { ArrayList<ActivitiesBean>() }
    // 位置监听器
    private val locationListener by lazy {
        val locationListener = object : LocationListener {

            /**
             * 当位置改变时触发
             * */
            override fun onLocationChanged(location: Location) {
                Timber.i("onLocationChanged  location  $location")

            }

            /**
             * Provider失效时触发
             * */
            override fun onProviderDisabled(arg0: String) {
                Timber.i("onProviderDisabled arg0: $arg0")

            }

            /**
             * Provider可用时触发
             * */
            override fun onProviderEnabled(arg0: String) {
                Timber.i("onProviderEnabled arg0: $arg0")
            }

            /**
             * Provider状态改变时触发
             * */
            override fun onStatusChanged(arg0: String, arg1: Int, arg2: Bundle) {
                Timber.i("onStatusChanged arg0: $arg0  arg1 : $arg1  arg2  $arg2")
            }
        }
        locationListener
    }

    override fun initResLay() = R.layout.schedules_activity_schedule_item_info
    private var i = 1
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
                if (currentId == null || currentId == ScheduleItem.SCHEDULE_NO_PARENT_ID) return@listen
                LitePal.findAsync(WkActivity::class.java, currentId
                        ?: WkActivity.NO_PARENT).listen { parentWkaActivity ->
                    //说明该WkActivity已经无效，比如被删除了
                    if (parentWkaActivity == null) {
                        val mContentValues = ContentValues()
                        mContentValues.put(ScheduleItem.SCHEDULE_PARENT_ID,
                                ScheduleItem.SCHEDULE_NO_PARENT_ID)
                        LitePal.updateAsync(ScheduleItem::class.java,
                                mContentValues, itemId).listen { num ->
                            Timber.i("num : $num")
                        }
                    }
                    tvItemClassName.text = parentWkaActivity?.itemName ?: ""
                }

            }
        else {
            tvScheduleStartTime.text = DateTime.getDateString(System.currentTimeMillis())
            tvScheduleEndTime.text = DateTime.getDateString(0)
        }
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

            /**
             * 选择选中的类别
             * */
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                if (adapter !is CategoryListAdapter) return
                Timber.i("onItemClick position:  $position")
                currentBean = adapter.getItem(position)
                val wkActivity = currentBean?.wkActivity
                //说明是个额外的item
                if (wkActivity == null) {
                    ToastUtil.show("增加")
                    val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
                    mScheduleItemAddDialog.setTargetFragment(this@ActivitiesInfoFragment, RequestCode.ActivitiesInfoFragment_CategoryName)
                    mScheduleItemAddDialog.show(fragmentManager)

                } else {
                    tvItemClassName.text = wkActivity.itemName
                    newCategoryId = wkActivity.baseObjId
                }

            }

            override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

                super.onItemLongClick(adapter, view, position)
                if (adapter !is CategoryListAdapter) return
                targetItem = adapter.getItem(position)
                        ?: return
                val targetActivity = targetItem?.wkActivity
                        ?: return
                val popupMenu = PopupMenu(_mActivity, view ?: return)
                //加载菜单文件
                popupMenu.menuInflater.inflate(R.menu.activities_category_delete_and_move, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuCategoryDelete -> {
                            //删除

                            Observable.just(targetActivity)
                                    .map {
                                        deleteActivities(it)
                                    }.subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object : Subscriber<Unit>() {
                                        override fun onNext(t: Unit?) {

                                        }

                                        override fun onCompleted() {
                                            val parentPosition = adapter.getParentPosition(targetItem
                                                    ?: return)
                                            adapter.collapse(parentPosition)
                                            targetItem?.parentBean?.removeSubItem(targetItem)
                                        }

                                        override fun onError(e: Throwable?) {
                                            Timber.i("删除失败： ${e?.message}")
                                        }
                                    })
                        }
                        //更换父类别
                        R.id.menuCategoryMove -> {
                            //需要变更父类的WkActivity
                            val parentPosition = adapter.getParentPosition(targetItem
                                    ?: return@setOnMenuItemClickListener true)
                            adapter.collapse(parentPosition)
                            val needMoveId = targetActivity.baseObjId
                            Timber.i("targetActivityId: $needMoveId")
                            val bundle = Bundle()
                            bundle.putLong(WkActivity.ACTIVITY_ID, needMoveId)
                            val mCategoryDialog = CategoryDialog.create(bundle)
                            mCategoryDialog.setTargetFragment(this@ActivitiesInfoFragment, RequestCode.ActivitiesInfoFragment_CHANGE_PARENTID)
                            mCategoryDialog.show(fragmentManager)
                        }
                    }
                    true
                }
                popupMenu.show()

            }
        })
    }

    //需要更换父类的WkActivity
    private var targetItem: ActivitiesBean? = null

    //从数据库中移除wkActivity及其子类
    private fun deleteActivities(wkActivity: WkActivity) {
        val activityId = wkActivity.baseObjId
        //找到其子类
        val subList = LitePal.where("parentId=?", activityId.toString())
                .find(WkActivity::class.java)
        if (subList.isNotEmpty())
            subList.forEach {
                deleteActivities(it)
            }
        LitePal.delete(WkActivity::class.java, activityId)

    }

    private var currentBean: ActivitiesBean? = null


    private val TIME_OUT=1000
    private var tmpTime=0L
    override fun onClick(v: View?) {
        when (v) {
            //修改项目名称
            tvScheduleName -> {
                val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
                mScheduleItemAddDialog.setTargetFragment(this, RequestCode.ActivitiesInfoFragment_itemName)
                mScheduleItemAddDialog.show(fragmentManager)
            }
            //返回
            btOk -> {
                //修改信息
                val startTime = DateTime.getDateLong(tvScheduleStartTime.text.toString())
                val endTime = DateTime.getDateLong(tvScheduleEndTime.text.toString())
                //很有可能是点错了,防止误点到以保存的活动
                if(endTime>startTime){
                    if(tmpTime==0L) {
                        tmpTime = System.currentTimeMillis()
                        return
                    }else{
                        if(System.currentTimeMillis()-tmpTime<TIME_OUT){
                            ToastUtil.show("再点一次保存")
                            return
                        }
                    }

                }
                val mScheduleItemName = tvScheduleName.text.toString()
                if (mScheduleItemName.isBlank()) {
                    ToastUtil.show("活动名称为空")
                    return
                }

                val note = etScheduleNote.text.toString()

                //修改信息
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
                        arguments?.putString(SchedulesBundleKey.SCHEDULE_ITEM_NAME, mScheduleItemName)
                        arguments?.putString(SchedulesBundleKey.SCHEDULE_OPERATION, OPERATION_MODIFY)
                        setFragmentResult(ResultCode.ResultCode_ScheduleItemInfoActivity, arguments)
                        pop()
                    }
                } else {
                    //增加项目
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
        tvScheduleName.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode :  $requestCode  resultCode:  $resultCode ")
        if (resultCode == ResultCode.ScheduleItemAddDialog)
            when (requestCode) {
                RequestCode.ActivitiesInfoFragment_itemName -> {
                    val scheduleItemName = data?.getStringExtra(SchedulesBundleKey.SCHEDULE_ITEM_NAME)
                            ?: ""
                    tvScheduleName.text = scheduleItemName
                }
                RequestCode.ActivitiesInfoFragment_CategoryName -> {
                    val categoryName = data?.getStringExtra(SchedulesBundleKey.CATEGORY_NAME)
                            ?: throw Exception("ActivitiesInfoFragment category name is null ")
                    if (categoryName.isBlank()) {
                        return
                    }
                    Timber.i("增加的类别名称： $categoryName")
                    //上一层
                    val parentBean = currentBean?.parentBean
                    val newWkActivity = WkActivity(categoryName, System.currentTimeMillis(), parentBean?.wkActivity?.baseObjId
                            ?: WkActivity.NO_PARENT)
                    newWkActivity.saveAsync().listen {
                        if (!it) {
                            ToastUtil.show("新建类别失败")
                            return@listen
                        }
                        parentBean?.run {
                            val parentPosition = mCategoryListAdapter.getParentPosition(currentBean!!)
                            Timber.i("parent name: ${wkActivity?.itemName} ")
                            val subSize = subItems.size
                            addSubItem(subSize - 1,
                                    ActivitiesBean(
                                            newWkActivity,
                                            parentBean.wkLevel + 1,
                                            parentBean))
                            i++
                            if (isExpanded) {
                                mCategoryListAdapter.collapse(parentPosition)
                                mCategoryListAdapter.expand(parentPosition)
                            }
                        }

                    }


                }
            }

        if (requestCode == RequestCode.ActivitiesInfoFragment_CHANGE_PARENTID
                && resultCode == ResultCode.CategoryDialog) {
            val moveId = data?.getLongExtra(SchedulesBundleKey.ACTIVITY_PARENT_ID, -1) ?: -1
            val newParentPosition = data?.getIntExtra(BundleKey.LIST_POSITION, -1) ?: -1
            Timber.i("moveId: $moveId  newParentPosition:  $newParentPosition")
            if (moveId >= 0 && newParentPosition >= 0) {
                mCategoryListAdapter.collapse(newParentPosition)
                val newParent = mCategoryListAdapter.getItem(newParentPosition)
                val mContentValues = ContentValues()
                mContentValues.put(WkActivity.ACTIVITY_PARENT_ID,
                        moveId)
                targetItem?.run {
                    LitePal.updateAsync(WkActivity::class.java, mContentValues, wkActivity?.baseObjId
                            ?: -1).listen {
                        Timber.i("it $it")
                        if (it > 0) {
                            Timber.i("移动成功")
                            newParent?.subItems?.clear()
                            parentBean?.subItems?.clear()
                        }
                    }
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    override fun onResume() {
        super.onResume()
        val check = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (check == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0f, locationListener)
    }

    private fun checkOpenGps() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ToastUtil.show("请开启GPS导航...")
        }
    }
}