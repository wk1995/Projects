package com.wk.map.gaode.location;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/10
 * desc         : 高德定位策略
 */


public interface IGaoDeLocationStrategy {

    /**
     * 开始定位
     */
    void startLocation();

    /**
     * 停止定位
     */
    void stopLocation();

}
