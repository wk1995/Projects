package com.wk.test.di.hilt

import dagger.Module
import dagger.Provides
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
object ModuleHiltProvide {

    @AnalyticsService1
    @Provides
    fun provideAnalyticsService1(analyticsService1: AnalyticsServiceProvidesImpl):IAnalyticsService{
        return analyticsService1
    }

    @AnalyticsService2
    @Provides
    fun provideAnalyticsService2():IAnalyticsService{
        return AnalyticsServiceProvidesImpl2()
    }
}