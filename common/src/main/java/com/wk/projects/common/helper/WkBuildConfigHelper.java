package com.wk.projects.common.helper;

import android.os.Build;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/24
 * desc         :
 */


public class WkBuildConfigHelper {

    /**API level 小于21  {@link Build.VERSION_CODES#LOLLIPOP}*/
    public static boolean isLessThanLOLLIPOP(){
        return false
//                || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP
                ;
    }

    /**API level 大于等于 19 {@link Build.VERSION_CODES#KITKAT}*/
    public static boolean isGreaterThanOrEqualKITKAT(){
        return true
//                && Build.VERSION.SDK_INT >= 19
                ;
    }
}
