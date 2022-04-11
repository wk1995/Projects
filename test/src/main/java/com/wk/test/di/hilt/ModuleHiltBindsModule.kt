package com.wk.test.di.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/01
 * desc         :
 */

@Module
@InstallIn(ActivityComponent::class)
abstract class ModuleHiltBindsModule {
    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceBindsImpl: AnalyticsServiceBindsImpl
    ): IAnalyticsService
}