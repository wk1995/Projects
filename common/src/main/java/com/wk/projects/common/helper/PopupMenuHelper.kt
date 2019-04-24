package com.wk.projects.common.helper

import android.content.Context
import android.support.v7.widget.PopupMenu
import android.view.View

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/23
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class PopupMenuHelper private constructor() {
    companion object {
        private object PopupMenuHelperVH {
            val INSTANCE = PopupMenuHelper()
        }

        fun getInstance() = PopupMenuHelperVH.INSTANCE
    }

    class Builder(val context:Context){
        private var rootView: View?=null
        private var menuRes:Int=-1
        private var mOnMenuItemClickListener: PopupMenu.OnMenuItemClickListener?=null
        fun setRootView(rootView: View):Builder{
            this.rootView=rootView
            return this
        }

        fun build(){
            if(rootView==null)
                throw NullPointerException("rootView is null")
            if(menuRes<=0)
                menuRes
            val popupMenu = PopupMenu(context, rootView!! )
        }
    }
}