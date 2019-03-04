package com.wk.projects.common.listener

import android.text.Editable
import android.text.TextWatcher
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/12/12
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
abstract class BaseTextWatcher: TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        Timber.d("19 afterTextChanged  $s")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Timber.d("23 beforeTextChanged  $s  start : $start count:$count  after:  $after")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Timber.d("27 beforeTextChanged  $s  start : $start count:$count  before:  $before")

    }
}