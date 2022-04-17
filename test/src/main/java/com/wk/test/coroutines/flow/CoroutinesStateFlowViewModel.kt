package com.wk.test.coroutines.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.test.TestLogUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2022/4/17
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class CoroutinesStateFlowViewModel : ViewModel() {

    var i=0

     val _sample = MutableStateFlow<Int>(1)
    val sample = _sample.asStateFlow()


    fun way() {
        ++i
        viewModelScope.launch {
            TestLogUtil.log("sample  sample 1")
            _sample.emit(i)
        }
    }


}