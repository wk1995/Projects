package com.wk.test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : wk <br/>
 * e-mail : 1226426603@qq.com<br/>
 * time   : 2020/9/28<br/>
 * desc   :   <br/>
 * GitHub : https://github.com/wk1995 <br/>
 * CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 */
public class TestBean implements Parcelable {
    private int arrInt;
    private String arrString;

    @Override
    public int describeContents() {
        return 0;
    }

    public TestBean(Parcel dest) {
        arrInt = dest.readInt();
        arrString = dest.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(arrInt);
        dest.writeString(arrString);
    }

    public static final Parcelable.Creator<TestBean> CREATOR = new Parcelable.Creator<TestBean>() {

        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
