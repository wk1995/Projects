package com.wk.projects.common.unit

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/11/16<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
enum class WkUnit {



    StorageUnit(){

        override fun getValue(value: String, unit: String) {
            Pair<String,String>("12","jd")
        }
    };


    abstract fun getValue(value:String,unit:String)
}