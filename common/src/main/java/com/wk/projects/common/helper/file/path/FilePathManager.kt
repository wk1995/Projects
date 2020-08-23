package com.wk.projects.common.helper.file.path

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Environment
import com.wk.projects.common.configuration.WkProjects
import java.io.File

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/4/7<br/>
 *      desc   :   <br/>
 *      address: https://blog.csdn.net/qq_33882671/article/details/105362045

Android 分内部存储与外部存储

Android 4.4以上外部存储会分为机身存储以及外部sd卡存储


获取内部存储的方法 getInternal 前缀
获取外部存储的方法getExternal(getBody)前缀，sdk存储getExternal前缀，
先获取外部存储，若没有则获取内部存储 getAll前缀
获取关于某个应用的路径的方法_app后缀

 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
@Suppress(WkProjects.UNUSED)
object FilePathManager {

    /**
     * 获取内部存储的根路径
     * @sample /data
     * */
    fun getInsideRootFile(): File {
        return Environment.getDataDirectory()
    }

    /**
     * 获取某个应用在内部存储中的files路径
     * @sample /data/user/0/packname/files
     *  */
    fun getInternalFileApp(context: Context):File{
        return context.filesDir
    }

    /**
     * 获取某个应用在内部存储中的cache路径
     * @sample  /data/user/0/packname/cache
     * */
    fun getInternalCacheFileApp(context: Context):File{
        return context.cacheDir
    }

    /**
     * 获取某个应用在内部存储中的自定义路径
     * */
    fun getInternalCustomFileApp(context: Context,fileName:String,mode:Int=MODE_PRIVATE):File{
        return context.getDir(fileName, mode)
    }

    /**
     * 获取外部存储的根路径
     * @sample /storage/emulated/0
     * */
    fun getExternalRootFile():File{
//        Environment.getExternalStoragePublicDirectory(“”)
        return Environment.getExternalStorageDirectory()
    }

    /**
     * 获取某个应用在外部存储中的files路径
     * @sample  /storage/emulated/0/Android/data/packname/files
     * */
    fun getExternalFilesApp(context: Context):File?{
        return context.getExternalFilesDir("")
    }

    /**
     * 获取某个应用在外部存储/内部中的files路径
     * */
    fun getAllFilesApp(context: Context):File{
        return getExternalFilesApp(context)?:getInternalFileApp(context)
    }

    /**
     * 获取某个应用在外部存储中的cache路径
     * @sample /storage/emulated/0/Android/data/packname/cache
     * */
    fun getExternalCacheApp(context: Context):File?{
        return context.externalCacheDir
    }




}