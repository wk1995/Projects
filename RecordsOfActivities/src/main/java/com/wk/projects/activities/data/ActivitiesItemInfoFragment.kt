package com.wk.projects.activities.data

import android.os.Bundle
import com.wk.projects.activities.ActivitiesMainFragment
import com.wk.projects.activities.R
import com.wk.projects.common.BaseFragment

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
class ActivitiesItemInfoFragment : BaseFragment() {
    companion object {
        fun getInstance(bundle: Bundle? = null): ActivitiesItemInfoFragment {
            val mActivitiesItemInfoFragment = ActivitiesItemInfoFragment()
            mActivitiesItemInfoFragment.arguments = bundle
            return mActivitiesItemInfoFragment
        }
    }

    override fun initResLay() = R.layout.schedules_activity_schedule_item_info

}