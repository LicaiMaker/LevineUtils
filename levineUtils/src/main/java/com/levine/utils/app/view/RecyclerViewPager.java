package com.levine.utils.app.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.levine.utils.base.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class RecyclerViewPager extends RecyclerView {

    public RecyclerViewPager(@NonNull Context context) {
        this(context, null, 0);
    }

    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(attrs);
    }

    private TabLayout mTabLayout = null;
    private int orientation = 0;

    public void setTabLayout(TabLayout tabLayout) {
        this.mTabLayout = tabLayout;
        final RecyclerViewPager that = this;
        if (mTabLayout != null) {
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    that.setOnPagerPosition(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    private void init(AttributeSet attrs) {

        String o = "0";
        if (attrs != null) {
            o = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "orientation");
            if (TextUtils.isEmpty(o)) {
                o = "0";
            }
        }
        orientation = Integer.parseInt(o);
        final LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(orientation);
        this.setLayoutManager(llm);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
        //this.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        View viewIdle = snapHelper.findSnapView(llm);
                        if (viewIdle != null) {
                            int position = llm.getPosition(viewIdle);
                            if (onPagerChangeListener != null)
                                onPagerChangeListener.onPageChange(position);
                            if (mTabLayout != null) {
                                mTabLayout.getTabAt(position).select();
                            }
                        }
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setOnPagerPosition(int position) {
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        layoutManager.scrollToPosition(position);
    }

    public int getOnPagerPosition() {
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
    }

    public interface OnPagerChangeListener {
        void onPageChange(int position);
    }

    private OnPagerChangeListener onPagerChangeListener;

    public void setOnPagerChageListener(OnPagerChangeListener onpagerChageListener) {
        this.onPagerChangeListener = onpagerChageListener;
    }
}
