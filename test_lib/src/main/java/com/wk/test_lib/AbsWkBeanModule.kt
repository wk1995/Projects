package com.wk.test_lib

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/17
 * desc         :
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class AbsWkBeanModule {


    @Binds
    abstract fun  binds(wk:WkBean):IWk
}