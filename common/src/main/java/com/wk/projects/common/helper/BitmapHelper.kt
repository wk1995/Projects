package com.wk.projects.common.helper

import android.graphics.Bitmap
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/11/25<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
object BitmapHelper {

    fun saveBitmapFile(bitmap:Bitmap, fileName:String){
        val file = File(fileName)//将要保存图片的路径
        if(!file.exists()){
            file.parentFile.mkdirs()
        }
        val bos = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        bos.flush()
        bos.close()
    }
}