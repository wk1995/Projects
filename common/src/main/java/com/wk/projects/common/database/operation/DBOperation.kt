package com.wk.projects.common.database.operation

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/2/25
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class DBOperation private constructor(){
    companion object {
        object DBOperationHV{
             val INSTANCE= DBOperation()
        }
        @JvmStatic
        fun getInstance()= DBOperationHV.INSTANCE
    }



}