package com.wk.test.coroutines.suspend;

import androidx.annotation.NonNull;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/13
 * desc         :
 */


public class CallBackContinuation implements Continuation<Unit> {
    private Object result;
    @NonNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    @Override
    public void resumeWith(@NonNull Object result) {
        synchronized (this){
            this.result = result;
            notifyAll(); // 协程已经结束，通知下面的 wait() 方法停止阻塞
        }
    }
    public void await() throws Throwable {
        synchronized (this){
            while (true){
                Object result = this.result;
                if(result == null) wait(); // 调用了 Object.wait()，阻塞当前线程，在 notify 或者 notifyAll 调用时返回
                else if(result instanceof Throwable){
                    throw (Throwable) result;
                } else return;
            }
        }
    }

}
