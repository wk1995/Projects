package com.wk.test.recycle;

import androidx.core.view.NestedScrollingChildHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.wk.test.BaseTestActivity;
import com.wk.test.R;

import java.util.ArrayList;

public class RecycleViewMainActivity extends BaseTestActivity implements SampleAdapter.ICastToString<Integer> {

    private RecyclerView rvRecycleMain;


    private SampleAdapter<Integer> sampleAdapter;

    @Override
    public int initLayout() {
        return R.layout.test_recycle_main_activity;
    }

    @Override
    public void initView() {
        rvRecycleMain = findViewById(R.id.rvRecycleMain);
        rvRecycleMain.setLayoutManager(new LinearLayoutManager(this));
        rvRecycleMain.setNestedScrollingEnabled(true);
        sampleAdapter = new SampleAdapter<>();
        sampleAdapter.setCastToString(this);
        rvRecycleMain.setAdapter(sampleAdapter);
        rvRecycleMain.addItemDecoration(new SampleItemDecoration<Integer>());

// 将SnapHelper attach 到RecyclrView
        SnapHelper snapHelper = new SampleSnapHelper();
        snapHelper.attachToRecyclerView(rvRecycleMain);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add(i);
        }
        sampleAdapter.replaceData(list);
    }

    @Override
    public String castToString(Integer integer) {
        return integer.toString();
    }
}