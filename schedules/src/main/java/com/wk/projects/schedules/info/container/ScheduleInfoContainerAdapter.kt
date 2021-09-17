package com.wk.projects.schedules.info.container

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2021/9/15
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class ScheduleInfoContainerAdapter(val fm: FragmentManager, val fragments: List<Fragment>)
    : FragmentPagerAdapter(fm) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}