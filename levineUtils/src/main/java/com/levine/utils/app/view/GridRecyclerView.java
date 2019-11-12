package com.levine.utils.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.levine.utils.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

public class GridRecyclerView extends RecyclerView {
    public GridRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }
    private int spanCount=3;
    private final GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),spanCount);
    private void init(Context context,AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.GridRecyclerViewPager);

        spanCount = a.getInteger(
                R.styleable.GridRecyclerViewPager_spanCount, spanCount);// 默认为每行3个
        a.recycle();


        gridLayoutManager.setSpanCount(spanCount);
        this.setLayoutManager(gridLayoutManager);
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        gridLayoutManager.setSpanCount(spanCount);
        this.setLayoutManager(gridLayoutManager);
    }

    public int getSpanCount() {
        return spanCount;
    }
}
