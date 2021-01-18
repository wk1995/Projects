package com.wk.projects.common.collapse

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.os.Process
import androidx.annotation.RequiresApi
import com.wk.projects.common.communication.constant.BundleKey.PATH
import com.wk.projects.common.helper.file.path.CommonFilePath.COMMON_ROOT_PATH
import com.wk.projects.common.helper.file.path.CommonFilePath.ES_PATH
import rx.Observable
import rx.schedulers.Schedulers
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused")
class CrashHandler private constructor(private val context: Context,
                                       private val moduleName: String)
    : Thread.UncaughtExceptionHandler {
    //先把原先的取出来
    private val mDefaultCrashHandler: Thread.UncaughtExceptionHandler?  by lazy {Thread.getDefaultUncaughtExceptionHandler()}
    private val logPath by lazy { ES_PATH + COMMON_ROOT_PATH + moduleName + LOG }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 这是最关键的函数，当程序中有未捕获的异常，系统将会自动调用这个方法，
     * thread为出现未捕获异常的线程，ex为为未捕获的异常，有了这个ex，我们就可以得到
     * 异常信息了
     *
     * @param thread
     * @param ex
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        try {
            //导出异常信息到SD卡中
            val fileName = dumpExceptionToSDCard(ex)
            //上传异常信息到服务器，便于开发人员分析日志从而解决bug
            uploadExceptionToServer(fileName)
        } catch (e1: Exception) {
            e1.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            val intent = Intent()
            intent.putExtra(PATH, logPath)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = "com.wk.projects.collapse"
            Observable.just(intent).subscribeOn(Schedulers.newThread()).subscribe {
                Looper.prepare()
                try {
                    context.startActivity(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Looper.loop()
            }
        }
        ex.printStackTrace()
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultCrashHandler != null)
            mDefaultCrashHandler?.uncaughtException(thread, ex)
        else
            Process.killProcess(Process.myPid())


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Throws(IOException::class)
    private fun dumpExceptionToSDCard(ex: Throwable): String {
        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡中
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            throw Exception("dumpExceptionToSDCard: SD卡不存在")
        }
        val current = System.currentTimeMillis()
        val time = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.getDefault())
                .format(Date(current))
        val file = File(logPath)
        //先删除之前的异常信息
        /** if (file.exists()) {
         * DeleteFile(file);
         * } */
        if (!file.exists()) {
            file.mkdirs()
        }
        val stackTrace = ex.stackTrace

        var errorText = "错误：" + ex.toString() + "  \n  "
        for (i in stackTrace.indices) {
            errorText += (stackTrace[i].fileName + " class:"
                    + stackTrace[i].className + " method:"
                    + stackTrace[i].methodName + " line:"
                    + stackTrace[i].lineNumber + "  \n  ")
        }
        val fileName = file.toString() + File.separator + time + FILE_NAME_SUFFIX
        val pw = PrintWriter(
                BufferedWriter(
                        FileWriter(fileName)), true)
        pw.println(time)
        dumpPhoneInfo(pw)
        pw.println()
        ex.printStackTrace(pw)
        pw.close()
        return fileName
    }

    // Upload Exception Message To Your Web Server
    private fun uploadExceptionToServer(fileName: String) {
        val uploadFile = File(fileName)
        if (!uploadFile.exists()) throw FileNotFoundException("$fileName is not exists")

    }

    /**
     * 写入手机的基本信息
     *
     * @param pw
     */
    @Throws(PackageManager.NameNotFoundException::class)
    private fun dumpPhoneInfo(pw: PrintWriter) {
        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        pw.print("App Version: ")
        pw.print(pi.versionName)
        pw.print('_')
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            pw.println(pi.longVersionCode)*/

        //Android版本号
        pw.print("OS Version: ")
        pw.print(Build.VERSION.RELEASE)
        pw.print('_')
        pw.println(Build.VERSION.SDK_INT)

        //手机制造商
        pw.print("Vendor: ")
        pw.println(Build.MANUFACTURER)

        //手机型号
        pw.print("Model: ")
        pw.println(Build.MODEL)

        //CUP架构
        pw.print("CUP ABI: ")
        Build.SUPPORTED_ABIS.forEach {
            pw.println(it)
        }


        //奔溃发生时间
        pw.print("CURRENT DATE: ")
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        pw.println(dateFormat.format(currentDate))
    }


    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    private fun deleteFile(file: File) {
        if (file.isFile) {
            file.delete()
            return
        }
        if (file.isDirectory) {
            val childFile = file.listFiles()
            if (childFile == null || childFile.isEmpty()) {
                file.delete()
                return
            }
            for (f in childFile) {
                deleteFile(f)
            }
            file.delete()
        }
    }

    companion object {
        private const val FILE_NAME_SUFFIX = "log.txt"
        private const val LOG = "/LOG"

        object CrashHandlerFactory {
            fun getCrashHandler(context: Context, moduleName: String) = CrashHandler(context, moduleName)
        }

        fun init(context: Context, moduleName: String): CrashHandler {
            return CrashHandlerFactory.getCrashHandler(context.applicationContext, moduleName)
        }
    }

}