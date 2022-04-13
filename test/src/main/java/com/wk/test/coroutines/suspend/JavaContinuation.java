package com.wk.test.coroutines.suspend;

import androidx.annotation.NonNull;

import com.wk.test.TestLogUtil;

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


public class JavaContinuation implements Continuation<Object> {

    private final SuspendKotlinMethod mSuspendKotlinMethod = new SuspendKotlinMethod();
    private final Continuation<Unit> completion;

    public JavaContinuation(Continuation<Unit> completion) {
        this.completion = completion;
    }

    private int label = 0;

    @NonNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override
    public void resumeWith(@NonNull Object o) {
        try {
            Object result = o;
            TestLogUtil.log("label: " + label + " result: " + result);
            switch (label) {
                case 0: {
                    TestLogUtil.log(1);
                    result = mSuspendKotlinMethod.returnSuspend(this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 1: {
                    TestLogUtil.log(2);
                    result = DelayKt.delay(1000, this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 2: {
                    TestLogUtil.log(3);
                    result = mSuspendKotlinMethod.returnImmediately(this);
                    label++;
                    if (isSuspended(result)) return;
                }
                case 3: {
                    TestLogUtil.log(result);
                    TestLogUtil.log(4);
                }
            }
            completion.resumeWith(Unit.INSTANCE);
        } catch (Exception e) {
            completion.resumeWith(e);
        }
    }

    private boolean isSuspended(Object result) {
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}

