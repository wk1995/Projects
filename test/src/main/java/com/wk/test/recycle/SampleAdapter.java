package com.wk.test.recycle;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wk.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/10/28
 * desc         :
 */


public class SampleAdapter<T> extends RecyclerView.Adapter<SampleAdapter.SampleVH> {

    public static final int type_title=1;
    public static final int type_scene=2;

    private final List<T> mData;

    interface ICastToString<T> {
        String castToString(T t);
    }

    private ICastToString<T> mCastToString;

    public SampleAdapter(List<T> data) {
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }

    }

    public SampleAdapter() {
        mData = new ArrayList<>();
    }

    static class SampleVH extends RecyclerView.ViewHolder {
        TextView tvRecycleItem;

        SampleVH(View rootView) {
            super(rootView);
            tvRecycleItem = rootView.findViewById(R.id.tvRecycleItem);
        }
    }


    @NonNull
    @Override
    public SampleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_recycle_main_item, parent, false);
        if(viewType==type_scene){
            rootView.setBackgroundColor(Color.YELLOW);
        }
        return new SampleVH(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleVH holder, int position) {
        if (mCastToString != null) {
            holder.tvRecycleItem.setText(mCastToString.castToString(mData.get(position)));
        }

    }

    /**
     * 判断该position对应的位置是要固定
     *
     * @param position adapter position
     * @return true or false
     */
    public boolean isPinnedPosition(int position) {
        return position % 8 == 0;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPinnedPosition(position)){
            return type_title;
        }
        return type_scene;
    }

    public void addData(T data) {
        mData.add(data);
        notifyItemChanged(getItemCount());
    }


    public void replaceData(List<T> data) {
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setCastToString(ICastToString<T> castToString) {
        mCastToString = castToString;
    }
}
