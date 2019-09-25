package com.wk.projects.activities.data

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Message
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
import com.wk.projects.common.helper.BaseHandler
import com.wk.projects.common.helper.LogHelper
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import kotlinx.android.synthetic.main.activities_coordinate_list_item.*
import kotlinx.android.synthetic.main.schedules_activity_schedule_item_info.*
import org.litepal.LitePal
import org.litepal.extension.find
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/26
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   : 具体项目的详细信息
 * </pre>
 */
@Route(path = ARoutePath.ActivitiesInfoFragment)
class ActivitiesInfoFragment : BaseFragment(),
        View.OnClickListener, OnTimeSelectListener {

    companion object {
        const val OPERATION_MODIFY = "OPERATION_MODIFY"
        const val ADD_START = 0
        const val ADD_END = 1
    }

    private class InfoHandler(mActivitiesInfoFragment: ActivitiesInfoFragment)
        : BaseHandler<ActivitiesInfoFragment>(mActivitiesInfoFragment) {
        override fun handleMessage(msg: Message?) {
            if (!isValid()) {
                return
            }
            val locationBeans = msg?.obj ?: return
            getTarget()?.mCoordinateAdapter?.setNewData(locationBeans as? java.util.ArrayList<LocationBean>)
        }
    }

    /**
     * 点击路线的item的position
     * 修改路线item的坐标或时间会用到
     * */
    private var changeCoordinatePosition = 0


    /**
     * infoFragment里的recycleitem的点击事件
     * */
    private inner class InfoRecycleClickListener : BaseSimpleClickListener() {
        override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            when (adapter) {
                is CategoryListAdapter -> {
                    //展开
                    Timber.i("onItemChildClick position:  $position")
                    val wkActivityBean = adapter.getItem(position) as ActivitiesBean
                    val wkActivity = wkActivityBean.wkActivity ?: return
                    //如果没有下一层级的
                    if (!wkActivityBean.hasSubItem()) {
                        val wkActivityId = wkActivity.baseObjId
                        LitePal.where("parentId=?", wkActivityId.toString())
                                .findAsync(WkActivity::class.java).listen { wkActivities ->
                                    Timber.i("size: ${wkActivities.size}")
                                    wkActivities.forEach {
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
                is CoordinateAdapter -> {
                    ToastUtil.show("position: $position")
                    changeCoordinatePosition = position
                    when (view?.id) {
                        /*改变坐标，不能是改变坐标的desc
                        * 可以新增，更换坐标*/
                        R.id.tvDescCoordinate -> {
                            val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
                            mScheduleItemAddDialog.setTargetFragment(this@ActivitiesInfoFragment, RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE)
                            mScheduleItemAddDialog.show(fragmentManager)
                        }
                        /*
                        改变路线坐标的时间
                        */
                        R.id.tvTimeCoordinate -> {
                            TimePickerCreator.create(_mActivity, this@ActivitiesInfoFragment, tvTimeCoordinate)
                        }
                    }
                }
            }

        }

        override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            Timber.i("onItemClick position:  $position")
            when (adapter) {
                is CategoryListAdapter -> {
                    selectActivityBean(adapter, position)
                }
            }
        }

        override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            super.onItemLongClick(adapter, view, position)
            when (adapter) {
                is CategoryListAdapter -> {
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
                                        .map { wkActivity ->
                                            deleteActivities(wkActivity)
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
                is CoordinateAdapter -> {
                    val route: com.wk.projects.activities.data.Route = adapter.getItem(position)?.route
                            ?: return

                    val popupMenu = PopupMenu(_mActivity, view ?: return)
                    //加载菜单文件
                    popupMenu.menuInflater.inflate(R.menu.activities_category_delete_and_move, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener {
                        when (position) {
                            //删除的是第一个
                            0 -> {
                                route.deleteAsync().listen {
                                    LogHelper.TimberI("route: $route 删除： $it")
                                    if(it>0) {
                                        mCoordinateAdapter.getItem(1)?.isStart = true
                                        mCoordinateAdapter.remove(0)
                                    }
                                }
                            }
                            //删除的是最后一个
                            adapter.itemCount - 1 -> {
                                route.endTime=0
                                route.endCoordinateId=-1L
                                route.saveAsync().listen {
                                    LogHelper.TimberI("route: $route 删除最后一个 $it")
                                    if(it) {
                                        mCoordinateAdapter.remove(position)
                                    }
                                }
                            }
                            else -> {}
                        }
                        true
                    }
                    adapter.notifyDataSetChanged()
                }
            }


        }
    }

    /**选择选中的类别*/
    fun selectActivityBean(adapter: CategoryListAdapter, position: Int) {
        currentBean = adapter.getItem(position)
        val wkActivity = currentBean?.wkActivity
        //说明是个额外的item
        if (wkActivity == null) {
            ToastUtil.show("增加")
            val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
            mScheduleItemAddDialog.setTargetFragment(this@ActivitiesInfoFragment, RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName)
            mScheduleItemAddDialog.show(fragmentManager)

        } else {
            val categoryName = wkActivity.itemName
            tvItemClassName.text = categoryName
            newBelongActivity = wkActivity
        }
    }

    private val mInfoRecycleClickListener by lazy { InfoRecycleClickListener() }

    private lateinit var infoHandler: InfoHandler

    private val itemId: Long by lazy {
        arguments?.getLong(SchedulesBundleKey.SCHEDULE_ITEM_ID, ScheduleItem.SCHEDULE_INVALID)
                ?: ScheduleItem.SCHEDULE_INVALID
    }

    /**传过来的ScheduleItem*/
    private var transmitScheduleItem: ScheduleItem? = null


    /**
     * 改变后的belongActivity
     * */
    private var newBelongActivity: WkActivity? = null
    private var currentBelongActivity: WkActivity? = null
    private val mCategoryListAdapter by lazy { CategoryListAdapter() }

    /**
     * 用于类别的recycleView
     * */
    private val cateGoryList by lazy { ArrayList<ActivitiesBean>() }

    private var currentBean: ActivitiesBean? = null

    override fun initResLay() = R.layout.schedules_activity_schedule_item_info

    private var i = 1

    override fun initView() {
        super.initView()
        infoHandler = InfoHandler(this)
        if (itemId >= 0) {
            LitePal.findAsync(ScheduleItem::class.java, itemId, true).listen { mScheduleItem ->
                /*
                transmitScheduleItem：传递过来的scheduleItem
                取出相应的值,放入相应的控件里
                */
                transmitScheduleItem = mScheduleItem
                initCoordinateRecycler(transmitScheduleItem)
                mScheduleItem?.run {
                    currentBelongActivity = belongActivity
                    tvScheduleName.text = itemName
                    tvScheduleStartTime.text = DateTime.getDateString(startTime)
                    tvScheduleEndTime.text = DateTime.getDateString(endTime)
                    etScheduleNote.setText(note)
                    currentBelongActivity?.run {
                        LitePal.findAsync(WkActivity::class.java, baseObjId).listen { wkActivity ->
                            //说明该WkActivity已经无效，比如被删除了
                            if (wkActivity == null) {
                                val currentScheduleItem = ScheduleItem()
                                currentScheduleItem.belongActivity = null
                                currentScheduleItem.update(itemId)
                            }
                            tvItemClassName.text = wkActivity?.itemName ?: ""
                        }
                    }

                }
            }
        } else {//新建的活动项目
            tvScheduleStartTime.text = DateTime.getDateString(System.currentTimeMillis())
            tvScheduleEndTime.text = DateTime.getDateString(0)
        }
        initClick()
        rvItemClass.layoutManager = LinearLayoutManager(_mActivity)
        rvItemClass.adapter = mCategoryListAdapter
        findRootCategory()
        rvItemClass.addOnItemTouchListener(mInfoRecycleClickListener)
    }

    private val mCoordinateAdapter by lazy { CoordinateAdapter() }

    /**
     * 初始化关于坐标的RecyclerView
     * */
    private fun initCoordinateRecycler(transmitScheduleItem: ScheduleItem?) {
        rvLocations.layoutManager = LinearLayoutManager(_mActivity)
        rvLocations.adapter = mCoordinateAdapter
        rvLocations.addOnItemTouchListener(mInfoRecycleClickListener)
        //取出额外的数据，这里是坐标
        val extraDatas = transmitScheduleItem?.routes ?: return
        Timber.i(extraDatas.size.toString())

        Thread {
            //是否是整条路线的起点标志
            var isFirstRoute = true
            val locationBeans = ArrayList<LocationBean>()
            extraDatas.forEach { route ->
                LogHelper.TimberI(route.toString())
                var startCoordinateId = route.startCoordinateId
                val endCoordinateId = route.endCoordinateId
                if (!isFirstRoute) {//如果不是整个路线的起点，多段路程中，可能有一段的起点是上一段的终点
                    val lastEndCoordinate = locationBeans.last()
                    val endTime = lastEndCoordinate.route.endTime
                    if (lastEndCoordinate.route.endCoordinateId == startCoordinateId && (Math.abs(route.startTime - endTime) < 6000)) {
                        startCoordinateId = -1
                    }
                }
                LitePal.find<Coordinate>(startCoordinateId)?.run {
                    locationBeans.add(LocationBean(route, true))
                }
                LitePal.find<Coordinate>(endCoordinateId)?.run {
                    locationBeans.add(LocationBean(route, false))
                }
                if (isFirstRoute)
                    isFirstRoute = false
            }
            Message.obtain(infoHandler, 0, locationBeans).sendToTarget()
        }.start()
    }

    /**专门为了这个CoordinateRecycler创建的bean*/
    class LocationBean(var route: com.wk.projects.activities.data.Route, var isStart: Boolean)

    /***
     * 需要更换父类的WkActivity
     * */
    private var targetItem: ActivitiesBean? = null

    /**
     * 从数据库中移除wkActivity及其子类
     * */
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

    private fun addCordination() {

    }

    override fun onClick(v: View?) {
        when (v) {
            tvLocationName -> {
                //弹窗，增加desc
                val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
                mScheduleItemAddDialog.setTargetFragment(this, RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination)
                mScheduleItemAddDialog.show(fragmentManager)
            }
            //修改项目名称
            tvScheduleName -> {
                val mScheduleItemAddDialog = ScheduleItemAddDialog.create()
                mScheduleItemAddDialog.setTargetFragment(this, RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName)
                mScheduleItemAddDialog.show(fragmentManager)
            }
            //返回
            btOk -> {
                //修改信息
                val startTime = DateTime.getDateLong(tvScheduleStartTime.text.toString())
                val endTime = DateTime.getDateLong(tvScheduleEndTime.text.toString())

                val mScheduleItemName = tvScheduleName.text.toString()
                if (mScheduleItemName.isBlank()) {
                    ToastUtil.show("活动名称为空")
                    return
                }

                val note = etScheduleNote.text.toString()

                //修改信息
                if (itemId >= 0) {
                    val mScheduleItem = ScheduleItem()
                    mScheduleItem.startTime = startTime
                    mScheduleItem.endTime = endTime
                    mScheduleItem.note = note
                    if (newBelongActivity != currentBelongActivity) {
                        mScheduleItem.belongActivity = newBelongActivity
                    }
                    mScheduleItem.updateAsync(itemId).listen {
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
                            etScheduleNote.text.toString(), newBelongActivity)
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
        //路线recycle中的item
        if (v?.id == R.id.tvTimeCoordinate) {
            /*先找到相应的route，然后改掉其坐标的id
            * 注意的是：如果是连续的话，需要改掉起点和终点为这个对应的坐标的小路线
            * */
            val mLocationBean = mCoordinateAdapter.data[changeCoordinatePosition]
            val isStart = mLocationBean.isStart
            val route = mLocationBean.route
            if (isStart) {
                route.startTime = date?.time ?: 0L
            } else {
                route.endTime = date?.time ?: 0L
            }
            route.saveAsync().listen {
                mCoordinateAdapter.notifyItemChanged(changeCoordinatePosition)
            }
        } else {
            (v as? TextView)?.text = DateTime.getDateString(date?.time)
        }

    }

    /**
     * 取出顶部类型
     * */
    private fun findRootCategory() {
        //先取出最大的类别
        LitePal.where("parentId=?", WkActivity.NO_PARENT.toString())
                .findAsync(WkActivity::class.java).listen { wkActivities ->
                    wkActivities.forEach {
                        cateGoryList.add(ActivitiesBean(it, 0))
                    }
                    mCategoryListAdapter.setNewData(cateGoryList)
                }
    }

    private fun initClick() {
        tvScheduleStartTime.setOnClickListener(this)
        tvScheduleEndTime.setOnClickListener(this)
        btOk.setOnClickListener(this)
        btEndTime.setOnClickListener(this)
        btStartTime.setOnClickListener(this)
        tvScheduleName.setOnClickListener(this)
        btAddLocation.setOnClickListener(this)
        tvLocationName.setOnClickListener(this)
    }


    /**
     *
     * */
    private fun saveOrUpdateRoute(route: com.wk.projects.activities.data.Route, type: Int, coordinateDesc: String) {
        when (type) {
            ADD_END -> {
                val mCoordinate = Coordinate()
                mCoordinate.coordinateDesc = coordinateDesc
                mCoordinate.saveOrUpdateAsync("coordinatedesc=?", coordinateDesc).listen {
                    if (!it) {
                        ToastUtil.show("Coordinate   saveOrUpdateAsync  failed  ")
                    }
                    //起点
                    if (route.endCoordinateId == -1L) {
                        route.endCoordinateId = mCoordinate.baseObjId
                        route.endTime = System.currentTimeMillis()
                        route.belongScheduleItem = transmitScheduleItem
                        route.updateAsync(route.baseObjId).listen { updateCount ->
                            if (updateCount > 0) {
                                ToastUtil.show("Route更新成功")
                                mCoordinateAdapter.addData(LocationBean(route, false))
                            } else
                                ToastUtil.show("Route更新失败")
                        }

                    } else {
                        //列表最后一个点是route的end，现在它要变成新的route的start
                        val newRoute = Route()
                        newRoute.startCoordinateId = route.endCoordinateId
                        newRoute.startTime = route.endTime
                        newRoute.belongScheduleItem = transmitScheduleItem
                        newRoute.endCoordinateId = mCoordinate.baseObjId
                        newRoute.endTime = System.currentTimeMillis()
                        newRoute.saveAsync().listen { isSuccessful ->
                            if (isSuccessful) {
                                ToastUtil.show("Route保存成功")
                                mCoordinateAdapter.addData(LocationBean(newRoute, false))
                            } else
                                ToastUtil.show("Route保存失败")
                        }
                    }


                }
            }
            ADD_START -> {
                val mCoordinate = Coordinate()
                mCoordinate.coordinateDesc = coordinateDesc
                mCoordinate.saveOrUpdateAsync("coordinatedesc=?", coordinateDesc).listen {
                    if (!it) {
                        ToastUtil.show("Coordinate   saveOrUpdateAsync  failed  ")
                    }
                    route.startCoordinateId = mCoordinate.baseObjId
                    route.startTime = System.currentTimeMillis()
                    route.belongScheduleItem = transmitScheduleItem
                    route.saveAsync().listen { isSuccessful ->
                        if (isSuccessful) {
                            ToastUtil.show("Route保存成功")
                            mCoordinateAdapter.addData(LocationBean(route, true))
                        } else
                            ToastUtil.show("Route保存失败")
                    }
                }

            }
        }
    }


    private fun updateRouteCoordinate(newCoordinate: Coordinate) {
        val changeLocationBean = mCoordinateAdapter.getItem(changeCoordinatePosition)
                ?: return
        val route = changeLocationBean.route
        if (changeLocationBean.isStart) {
            route.startCoordinateId = newCoordinate.baseObjId
        } else {
            route.endCoordinateId = newCoordinate.baseObjId
        }
        route.saveAsync().listen { isRouteSaveSuccess ->
            if (isRouteSaveSuccess) {
                LogHelper.i("wk", "修改路程中的坐标---(路线保存成功)")
            } else {
                LogHelper.i("wk", "修改路程中的坐标---(路线保存失败)")
            }
        }
        mCoordinateAdapter.notifyItemChanged(changeCoordinatePosition)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.i("requestCode :  $requestCode  resultCode:  $resultCode ")
        when (resultCode) {
            ResultCode.ScheduleItemAddDialog -> {
                when (requestCode) {
                    RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination_UPDATE -> {
                        //先确定是否有这个坐标
                        val coordinateDesc = data?.getStringExtra(SchedulesBundleKey.COORDINATE_DESC)
                                ?: return
                        LitePal.where("coordinateDesc=?", coordinateDesc)
                                .findAsync(Coordinate::class.java)
                                .listen {
                                    //说明是新建的坐标
                                    if (it.isEmpty()) {
                                        val newCoordinate = Coordinate(null, null, coordinateDesc)
                                        newCoordinate.saveAsync().listen { isSaveSuccess ->
                                            if (isSaveSuccess) {
                                                LogHelper.i("wk", "修改路程中的坐标成功---(新增坐标)")
                                                updateRouteCoordinate(newCoordinate)
                                            } else {
                                                LogHelper.i("wk", "修改路程中的坐标失败---(新增坐标)")
                                            }
                                        }
                                    } else {
                                        //说明是用曾经记录的的坐标
                                        LogHelper.i("wk", "找到des为$coordinateDesc 的坐标有${it.size} 个")
                                        updateRouteCoordinate(it[0])
                                    }
                                }

                    }
                    RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_coordination -> {
                        val coordinateDesc = data?.getStringExtra(SchedulesBundleKey.COORDINATE_DESC)
                                ?: return
                        val locationBeanSize = mCoordinateAdapter.itemCount
                        LogHelper.TimberI("size628: $locationBeanSize")
                        //已经有路线数据，先确定新的坐标是否是记录的最后一个坐标，即是否是在同一地点等待
                        if (locationBeanSize > 0) {
                            val lastLocationBean = mCoordinateAdapter.getItem(locationBeanSize - 1)
                            val isStart = lastLocationBean?.isStart ?: return

                            LitePal.findAsync(Coordinate::class.java,
                                    if (isStart) {
                                        lastLocationBean.route.startCoordinateId
                                    } else {
                                        lastLocationBean.route.endCoordinateId
                                    })
                                    .listen {
                                        val desc = it?.coordinateDesc
                                        if (desc == null) {
                                            LogHelper.TimberI("645  desc is null")
                                            return@listen
                                        }
                                        //说明在一个地点停留了一段时间，这时候就应该新建一个route
                                        if (desc == coordinateDesc) {
                                            LogHelper.TimberI("新的起点")
                                            saveOrUpdateRoute(Route(), ADD_START, coordinateDesc)
                                        } else {
                                            //取出额外的数据，这里是坐标
                                            LogHelper.TimberI("增加终点")
                                            val lastRoute = transmitScheduleItem?.routes?.last()
                                                    ?: return@listen
                                            saveOrUpdateRoute(lastRoute, ADD_END, coordinateDesc)
                                        }
                                    }

                            //一个坐标都没有
                        } else {
                            LogHelper.TimberI("开始")
                            saveOrUpdateRoute(Route(), ADD_START, coordinateDesc)
                        }
                    }

                    RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_update_itemName -> {
                        val scheduleItemName = data?.getStringExtra(SchedulesBundleKey.SCHEDULE_ITEM_NAME)
                                ?: ""
                        tvScheduleName.text = scheduleItemName
                    }
                    RequestCode.RequestCode_ActivitiesInfoFragment_ScheduleItemAddDialog_CategoryName -> {
                        val categoryName = data?.getStringExtra(SchedulesBundleKey.CATEGORY_NAME)
                                ?: throw Exception("ActivitiesInfoFragment category name is null ")
                        if (categoryName.isBlank()) {
                            return
                        }
                        Timber.i("增加的类别名称： $categoryName")
                        //上一层
                        val parentBean = currentBean?.parentBean
                        val newWkActivity = WkActivity(categoryName, System.currentTimeMillis(), parentBean?.wkActivity?.baseObjId
                                ?: WkActivity.NO_PARENT, false)
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
            }
            RequestCode.ActivitiesInfoFragment_CHANGE_PARENTID -> {
                if (resultCode == ResultCode.CategoryDialog) {
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
        }

    }

}