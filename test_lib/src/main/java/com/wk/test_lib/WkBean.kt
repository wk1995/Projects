package com.wk.test_lib

import javax.inject.Inject

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/17
 * desc         :
 */


class WkBean @Inject constructor():IWk {

    override fun analyticsMethods(){
        System.out.println("wk")
    }
}