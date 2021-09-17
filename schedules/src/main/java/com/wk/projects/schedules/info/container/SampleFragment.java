package com.wk.projects.schedules.info.container;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wk.projects.common.BaseFragment;
import com.wk.projects.common.constant.WkStringConstants;
import com.wk.projects.schedules.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/9/17
 * desc   :
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 */
public class SampleFragment extends BaseFragment {
    private String mShowText= WkStringConstants.STR_EMPTY;

    public SampleFragment(String showText) {
        mShowText=showText;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_only_text, container, false);
        TextView textView=rootView.findViewById(R.id.tvCommon);
        textView.setText(mShowText);
        return rootView;
    }
}
