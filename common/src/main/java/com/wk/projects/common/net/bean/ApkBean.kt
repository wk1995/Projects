package com.wk.projects.common.net.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/20
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
data class ApkBean(val apkName: String, var state: String? = STATE_PREPARE, var progress: Int = 0)
    : Parcelable {

    companion object {
        const val STATE_DOWNLOADING = "STATE_DOWNLOADING"
        const val STATE_COMPLETE = "STATE_COMPLETE"
        const val STATE_STOP = "STATE_STOP"
        const val STATE_PREPARE = "STATE_PREPARE"
        @JvmField val CREATOR: Parcelable.Creator<ApkBean> = object : Parcelable.Creator<ApkBean> {
            override fun createFromParcel(parcel: Parcel): ApkBean {
                return ApkBean(parcel.readString(), parcel.readString(), parcel.readInt())
            }

            override fun newArray(size: Int): Array<ApkBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(apkName)
        dest?.writeString(state)
        dest?.writeInt(progress)

    }

    override fun describeContents() = 0
}