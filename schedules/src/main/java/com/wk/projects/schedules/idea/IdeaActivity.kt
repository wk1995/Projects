package com.wk.projects.schedules.idea

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.configuration.ConfigureKey
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.CommonFilePath.COMMON_ROOT_PATH
import com.wk.projects.common.constant.CommonFilePath.ES_PATH
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.schedules.R
import kotlinx.android.synthetic.main.schedules_activity_idea.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.io.*

@Route(path = ARoutePath.IdeaActivity)
class IdeaActivity : BaseProjectsActivity() {
    companion object {
        private const val IDEA_FILE_NAME = "/idea.txt"
        private val moduleName = WkProjects.getConfiguration<String>(ConfigureKey.MODULE_NAME)
        val IDEA_FILE_PATH = ES_PATH + COMMON_ROOT_PATH + moduleName + IDEA_FILE_NAME
    }

    private val ideaFile by lazy { File(IDEA_FILE_PATH) }

    private var isSave = false
    override fun initResLayId() = R.layout.schedules_activity_idea
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        //读取文件
        Observable.just(ideaFile).map {
            read(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    override fun onNext(t: String?) {
                        Timber.i("onNext: $t")
                        etIdea.setText(t)
                    }

                    override fun onCompleted() {
                        Timber.i("onCompleted")
                    }

                    override fun onError(e: Throwable?) {
                        Timber.i("onError: ${e?.message}")
                    }
                })
        etIdea.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isSave = false
                super.onTextChanged(s, start, before, count)
            }
        })
        btSaveIdea.setOnClickListener {
            //保存
            val idea = etIdea.text.toString()
            Observable.just(ideaFile).map {
                write(it, idea)
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Any>() {
                        override fun onNext(t: Any?) {
                            Timber.i("onNext: $t")
                        }

                        override fun onCompleted() {
                            Timber.i("onCompleted")
                        }

                        override fun onError(e: Throwable?) {
                            Timber.i("onError: ${e?.message}")
                        }
                    })

        }
    }

    private fun write(file: File, content: String) {
        if (!file.exists()) {
            file.parentFile.mkdirs()
        }
        val pw = PrintWriter(
                BufferedWriter(
                        FileWriter(file)), true)
        pw.println(content)
        pw.close()
    }

    private fun read(file: File): String {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            return ""
        }
        val sb = StringBuilder()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(InputStreamReader(FileInputStream(file)))

            var tmp = br.readLine()
            while (tmp != null) {
                sb.append(tmp)
                tmp = br.readLine()
            }
        } catch (e: IOException) {
            sb.append("读取文件发生异常： \r\n ${e.message}")
        } catch (e: FileNotFoundException) {
            sb.append("未找到文件：\r\n ${file.path}")
        } catch (e: Exception) {
            sb.append("发生异常： \r\n ${e.message}")
        } finally {
            br?.close()
            return sb.toString()
        }

    }

}
