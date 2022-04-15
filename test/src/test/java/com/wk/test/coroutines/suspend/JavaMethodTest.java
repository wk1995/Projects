package com.wk.test.coroutines.suspend;

import org.junit.Test;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/13
 * desc         :
 */


public class JavaMethodTest {

    private final JavaMethod mJavaMethod = new JavaMethod();

    @Test
    public void way() {
        sample();
    }

    private void sample(){
        mJavaMethod.invokeSuspendMethod(new SuspendKotlinMethod());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sample1(){
        mJavaMethod.execute();
    }
}