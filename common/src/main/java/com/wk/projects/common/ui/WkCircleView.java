package com.wk.projects.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author : wk <br/>
 * e-mail : 1226426603@qq.com<br/>
 * time   : 2020/11/7<br/>
 * desc   :   <br/>
 * GitHub : https://github.com/wk1995 <br/>
 * CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 */
public class WkCircleView extends View {

    public WkCircleView(Context context) {
        super(context);
    }

    public WkCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WkCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
