package com.wk.projects.common.helper.file.path

import android.os.Environment
import java.io.File

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
object CommonFilePath {
    const val COMMON_ROOT_PATH="wk/projects/"

    @JvmField
    // /storage/emulated/0/
    val ES_PATH = Environment.getExternalStorageDirectory().path + File.separator
}