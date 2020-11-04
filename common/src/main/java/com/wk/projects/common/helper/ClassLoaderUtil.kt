package com.wk.projects.common.helper

import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.common.constant.WkSuppressConstants

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/10/21<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
@Suppress(WkSuppressConstants.UNUSED)
object ClassLoaderUtil {
    private const val BOOT_STRAP = "sun.boot.class.path"
    private const val EXTENSIONS = "java.ext.dirs"

    /** 获取BootStrapClassLoader 加载的类的路径*/
    @JvmStatic
    fun getBootStrapLoaderPath(): List<String>? {
        val paths = System.getProperty(BOOT_STRAP)
        return paths?.split(";")
    }
    /** 获取ExtensionsClassLoader 加载的类的路径*/
    @JvmStatic
    fun getExtensionsLoaderPath() = System.getProperty(EXTENSIONS)
            ?.split(WkStringConstants.COMMON_PUNCTUATION_SEMICOLON)
}