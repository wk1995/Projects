package com.wk.projects.schedules.list

import com.alibaba.android.arouter.launcher.ARouter
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.schedules.constant.ActivityRequestCode
import com.wk.projects.schedules.data.ScheduleItem
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/10/26
 * desc         :
 */


class SchedulesListPresent(private val mSchedulesListActivity: SchedulesListActivity) {
    private var mCompositeSubscription: CompositeSubscription? = null

    fun onCreate() {
        mCompositeSubscription = CompositeSubscription()
    }


    fun onDestroy() {
        mCompositeSubscription?.clear()
        mCompositeSubscription = null
    }

    /**
     * 立即增加
     */
    fun addItemImmediately() {
        val currentTime = System.currentTimeMillis()
        val scheduleItem = ScheduleItem(WkStringConstants.STR_EMPTY, currentTime)
        mCompositeSubscription?.add(Observable.create(Observable.OnSubscribe<Boolean> {
            it.onNext(scheduleItem.save())
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it) {
                        mSchedulesListActivity.addItemImmediately(scheduleItem)
                    }
                }
        )
    }


    fun gotoInfoActivity(){
        ARouter.getInstance()
                .build(ARoutePath.ScheduleItemInfoActivity)
                .navigation(mSchedulesListActivity,
                        ActivityRequestCode.RequestCode_SchedulesMainActivity)
    }


}