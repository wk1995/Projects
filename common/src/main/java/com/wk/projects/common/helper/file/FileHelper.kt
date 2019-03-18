package com.wk.projects.common.helper.file

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.io.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 文件操作的封装类
 * </pre>
 */
class FileHelper private constructor() {
    companion object {
        private object FileHelperVH {
            val INSTANCE = FileHelper()
        }

        fun getInstance() = FileHelperVH.INSTANCE
    }

    /**
     * @param file 目标文件
     * @param content 写入目标文件的内容
     * */
    fun write(file: File, content: String) {
        if (!file.exists()) {
            file.parentFile.mkdirs()
        }
        val pw = PrintWriter(
                BufferedWriter(
                        FileWriter(file)), true)
        pw.println(content)
        pw.close()
    }


    /**
     * 异步读取文件内容
     * */
    fun readIO(file:File?,readStatus:IFileStatusListener.IReadStatusListener){
        //读取文件
        Observable.just(file).map {
            read(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    override fun onNext(t: String?) {
                        readStatus.readResult(t)
                    }

                    override fun onCompleted() {
                        readStatus.readFinish()
                    }

                    override fun onError(e: Throwable?) {
                        readStatus.readError(e)
                    }
                })
    }

    /**
     * 读取文件内容
     * */
    private fun read(file: File?): String {
        if (file == null || !file.exists()) {
            return "文件不存在"
        }
        val sb = StringBuilder()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(InputStreamReader(FileInputStream(file)))

            var tmp = br.readLine()
            while (tmp != null) {
                sb.append(tmp)
                sb.append("\r\n")
                tmp = br.readLine()
            }
            val length=sb.length
            sb.delete(length-2,length-1)
        } catch (e: IOException) {
            Timber.i("读取文件发生异常： \r\n ${e.message}")
        } catch (e: FileNotFoundException) {
            Timber.i("未找到文件：\r\n ${file.path}")
        } catch (e: Exception) {
            Timber.i("发生异常： \r\n ${e.message}")
        } finally {
            br?.close()
            return sb.toString()
        }
    }

}