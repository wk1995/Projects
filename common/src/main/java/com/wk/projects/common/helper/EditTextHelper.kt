package com.wk.projects.common.helper

import android.view.Window
import android.view.WindowManager
import android.widget.EditText

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/22
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class EditTextHelper private constructor() {
    companion object {
        private object EditTextHelperVH {
            val INSTANCE = EditTextHelper()
        }

        fun getInstance() = EditTextHelperVH.INSTANCE
    }

    //获取焦点并弹出软键盘
    fun showFocus(editText: EditText, window: Window) {
        editText.isFocusable = (true)
        editText.isFocusableInTouchMode = (true)
        editText.requestFocus()
        //显示软键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        // InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)
        // imm.showSoftInput(editText, 0);
    }

    //editText光标显示在最后
    fun showLastPosition(editText: EditText) {
        editText.setSelection(editText.text.length)
    }
}