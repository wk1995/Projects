package com.wk.test.di.dagger

import org.junit.Assert.*
import org.junit.Test

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/03/01
 * desc         :
 */


class DiDaggerActivityTest{

    @Test
    fun way(){
        val applicationGraph: ApplicationGraph = DaggerApplicationGraph.create()

        val userRepository: UserRepository = applicationGraph.repository()
        val userRepository2: UserRepository = applicationGraph.repository()
        print("userRepository: $userRepository userRepository2: $userRepository2")
    }

}