package com.levine.utils.app.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class RecyclerViewPager extends RecyclerView {

    public RecyclerViewPager(@NonNull Context context) {
        this(context,null,0);
    }

    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(attrs);
    }

    private int position=0;
    private void init(AttributeSet attrs) {

        String orientation="0";
        if(attrs!=null){
            orientation=attrs.getAttributeValue("http://schemas.android.com/apk/res/android","orientation");
            if(TextUtils.isEmpty(orientation)){
                orientation="0";
            }
        }
        int o=Integer.parseInt(orientation);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(o);
        this.setLayoutManager(llm);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
        //this.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager){
                    int firs = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    if (position != firs){
                        position = firs;
                        if (onPagerChangeListener != null)
                            onPagerChangeListener.onPageChange(position);
                    }
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setOnPagerPosition(int position){
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        layoutManager.scrollToPosition(position);
    }

    public int getOnPagerPosition(){
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        return ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
    }

    public interface onPagerChangeListener{
        void onPageChange(int position);
    }
    private onPagerChangeListener onPagerChangeListener;

    public void setOnPagerChageListener(onPagerChangeListener onpagerChageListener){
        this.onPagerChangeListener = onpagerChageListener;
    }
}
