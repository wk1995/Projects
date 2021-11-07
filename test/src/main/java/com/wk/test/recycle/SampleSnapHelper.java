package com.wk.test.recycle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.wk.projects.common.log.WkLog;
import com.wk.test.R;

import static com.wk.test.recycle.SampleAdapter.type_title;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/10/28
 * desc         :
 */


public class SampleSnapHelper extends LinearSnapHelper {

    private static final String TAG = "SampleSnapHelper";
    private OrientationHelper mVerticalHelper;

    /**
     * 计算最终对齐要移动的距离，返回一个长度为2的int 数组out，out[0] 为 x 方向移动的距离，out[1] 为 y 方向移动的距离
     */
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
//        if (layoutManager.canScrollHorizontally()) {
//            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
//        } else {
//            out[0] = 0;
//        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, layoutManager, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    private int distanceToStart(View targetView, @NonNull RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        final int center;
        if (layoutManager.getClipToPadding()) {
            center = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        } else {
            center = helper.getEnd() / 2;
        }

        int decoratedStart = helper.getDecoratedStart(targetView);
        int decoratedMeasurement = helper.getDecoratedMeasurement(targetView);
        if (decoratedStart < -decoratedMeasurement || decoratedMeasurement > 2 * center) {
            return 0;
        }
        int childCenter = decoratedStart + decoratedMeasurement / 2;
        WkLog.i("center: " + center + "  decoratedStart  " + decoratedStart + "  decoratedMeasurement  " + decoratedMeasurement + " childCenter " + childCenter, TAG);


        //标题在上半部
        if (center >= childCenter) {
            if (decoratedStart > 0) {
                //上半部之间
                WkLog.i("处于上半部之间 将向上移动  " + (decoratedStart), TAG);
                return decoratedStart;
            } else if (decoratedStart == 0) {
                WkLog.i("不需要移动", TAG);
                return 0;
            } else {
                //在屏幕外
                WkLog.i("在屏幕上方外  将向下移动 " + decoratedStart, TAG);
                return decoratedStart;
            }
        }
        WkLog.i("向下移动  " + (2 * center - decoratedStart), TAG);
        return decoratedStart - 2 * center;
    }

    /**
     * 获取需要对齐的目标View
     */
    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        WkLog.i("findSnapView: " , TAG);
        int childCount = layoutManager.getChildCount();
        WkLog.i("childCount: " + childCount, TAG);
        if (childCount == 0) {
            return null;
        }
        View closestChild = null;
        try {
            for (int i = 0; i < childCount; i++) {
                View child = layoutManager.getChildAt(i);

                if (child == null) {
                    WkLog.i("i: " + i + "  child is null  ", TAG);
                    continue;
                }

                int type = layoutManager.getItemViewType(child);
                if (type == type_title) {
                    WkLog.i("i: " + i + "  is title  text: " + printText(child), TAG);
                    closestChild = child;
                }
            }
        } catch (NullPointerException exception) {
            WkLog.e(exception.getMessage(), TAG);
            exception.printStackTrace();
        }
        return closestChild;

    }

    private String printText(View target) {
        View textView = target.findViewById(R.id.tvRecycleItem);
        if (textView instanceof TextView) {
            return ((TextView) textView).getText().toString();
        }
        return "null";
    }


    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        WkLog.i("findTargetSnapPosition: " , TAG);

        OrientationHelper helper = getVerticalHelper(layoutManager);
        final int center;
        if (layoutManager.getClipToPadding()) {
            center = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        } else {
            center = helper.getEnd() / 2;
        }
        final View currentView = findSnapView(layoutManager);
        if(currentView==null){
            return RecyclerView.NO_POSITION;
        }
        int currentPosition = layoutManager.getPosition(currentView);
        int decoratedStart = helper.getDecoratedStart(currentView);
        int decoratedMeasurement = helper.getDecoratedMeasurement(currentView);
        int childCenter=decoratedStart+decoratedMeasurement/2;
        if(childCenter>center){
            //下半部分
            if(decoratedStart==center*2 || (decoratedStart<center*2 && (decoratedStart+decoratedMeasurement)>center*2)){
               return currentPosition;
            }
                while ((decoratedStart+decoratedMeasurement)<=center*2){
                    ++currentPosition;
                    decoratedStart+=decoratedMeasurement;
                }
        }else{
            //上半部分
            if(decoratedStart==0 ){
                return currentPosition;
            }

            while ((decoratedStart-decoratedMeasurement)>=0){
                --currentPosition;
                decoratedStart-=decoratedMeasurement;
            }
        }

        WkLog.i("findTargetSnapPosition: position： "+currentPosition , TAG);
        return currentPosition;
    }


    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null || mVerticalHelper.getLayoutManager() != layoutManager) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }
}
