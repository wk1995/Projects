package com.wk.projects.records.consumption.data.account

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.records.consumption.R

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/28
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 资产账户
 * </pre>
 */
@Route(path = ARoutePath.ConsumptionAccountFragment)
class ConsumptionAccountFragment : BaseFragment() {
    override fun initResLay() = R.layout.common_only_recycler

}