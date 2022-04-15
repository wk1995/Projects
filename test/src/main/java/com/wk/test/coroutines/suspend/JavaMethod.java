package com.wk.test.coroutines.suspend;

import com.wk.test.TestLogUtil;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/12
 * desc         :
 */


public class JavaMethod {

    /**一个回调的java 方法*/
    interface IJavaInterfaceOneMethod {
        void oneMethod(Object object);
    }

    /**两个个回调的java 方法*/
    interface IJavaInterfaceTwoMethod {
        void oneMethod(Object object);

        void twoMethod(Object object);
    }

    /**多个回调的java 方法*/
    interface IJavaInterfaceThreeMethod {

        void oneMethod(Object object);

        void twoMethod(Object object);

        void threeMethod(Object object);
    }

    /**
     * 空方法
     * */
    public void javaEmptyMethod() {
        TestLogUtil.log("javaSimpleMethod");
    }

    /**
     * 使用一个Java回调的方法
     * */
    public void javaSimpleCallBackMethod(IJavaInterfaceOneMethod callback) {
        TestLogUtil.log("javaSimpleCallBackMethod OneMethod");
        callback.oneMethod("IJavaInterfaceOneMethod");
    }

    /**
     * 使用两个Java回调的方法
     * */
    public void javaSimpleCallBackMethod(IJavaInterfaceTwoMethod callback) {
        TestLogUtil.log("javaSimpleCallBackMethod TwoMethod");
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                if (i == 4) {
                    callback.oneMethod("IJavaInterfaceTwoMethod oneMethod");
                }
            }
        } catch (Exception e) {
            callback.twoMethod("IJavaInterfaceTwoMethod  oneMethod");
        }
    }

    /**
     * 使用多个Java回调的方法
     * */
    public void javaSimpleCallBackMethod(IJavaInterfaceThreeMethod callback) {
        TestLogUtil.log("javaSimpleCallBackMethod ThreeMethod");
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                if (i == 4) {
                    callback.oneMethod("IJavaInterfaceThreeMethod oneMethod");
                }
            }
        } catch (Exception e) {
            callback.twoMethod("IJavaInterfaceThreeMethod twoMethod");
        }
        callback.threeMethod("IJavaInterfaceThreeMethod  oneMethod");
    }

    public Object javaSuspendMethod(@NotNull Continuation $completion){
        Object object=DelayKt.delay(1000,$completion);
        return object == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? object : Unit.INSTANCE;
    }


    /**
     * Java使用 suspend方法
     * */
    public void invokeSuspendMethod(SuspendKotlinMethod suspendKotlinMethod) {
        Object value = suspendKotlinMethod.returnSuspend(new Continuation<Integer>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                TestLogUtil.log("o:  " + o.getClass().getSimpleName());
                if (o instanceof Integer) {
                    handleResult(o);
                } else {
                    Throwable throwable = (Throwable) o;
                    throwable.printStackTrace();
                }
            }
        });

        if (value == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            TestLogUtil.log("Suspended.");
        } else {
            handleResult(value);
        }
    }

    private void handleResult(Object o) {
        TestLogUtil.log("The result is " + o);
    }


    public void execute() {
        CallBackContinuation callBackContinuation = new CallBackContinuation();
        JavaContinuation javaContinuation = new JavaContinuation(callBackContinuation);
        try {
            callBackContinuation.await();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        javaContinuation.resumeWith(Unit.INSTANCE);
    }

}
