package com.wk.projects.common.helper;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/24
 * desc         :
 */


public class WkBuildConfigHelper {

    /**API level 小于21*/
    public static boolean isLessThanLOLLIPOP(){
        return false
//                || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP
                ;
    }
}
