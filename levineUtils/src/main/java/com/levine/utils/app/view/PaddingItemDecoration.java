package com.levine.utils.app.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.levine.utils.app.data.BaseBean;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;


    private int orientation = 1;
    private int mDividerWidth = 2;
    private int mPaddingStart = 0;
    private int mPaddingEnd = 0;
    private Paint mPaint;
    private int mTitleBgColor = Color.parseColor("#D8D8D8");

    private List<? extends BaseBean> dataList = null;

    public PaddingItemDecoration(int orientation) {

        mPaint = new Paint();
        if (orientation == 0 || orientation == 1) {
            this.orientation = orientation;
        }
    }

    public PaddingItemDecoration setDataList(List<?extends BaseBean> dataList) {
        this.dataList = dataList;
        return this;
    }

    public PaddingItemDecoration setmDividerWidth(int mDividerWidth) {
        this.mDividerWidth = mDividerWidth;
        return this;
    }

    public PaddingItemDecoration setmPaddingStart(int mPaddingStart) {
        this.mPaddingStart = mPaddingStart;
        return this;
    }

    public PaddingItemDecoration setmPaddingEnd(int mPaddingEnd) {
        this.mPaddingEnd = mPaddingEnd;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == HORIZONTAL) {
            //画垂直线
            if (drawDivider(view))
                outRect.set(0, 0, mDividerWidth, 0);
            else
                outRect.set(0, 0, 0, 0);
        } else if (orientation == VERTICAL) {
            if (drawDivider(view))
                outRect.set(0, 0, 0, mDividerWidth);
            else
                outRect.set(0, 0, 0, 0);
        }
    }

    private boolean drawDivider(View child) {
        if (dataList == null) {
            return true;
        }
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int position = params.getViewLayoutPosition();
        if (position > -1) {
            if (position + 1 < dataList.size()) {
                if (dataList != null
                        && dataList.get(position).getBeanType() != null
                        && !dataList.get(position).getBeanType().equals(dataList.get(position + 1).getBeanType())) {
                    return false;
                } else {
                    return true;
                }

            }
        }
        return true;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (orientation == HORIZONTAL) {
            drawVertical(c, parent, state);
        } else if (orientation == VERTICAL) {
            drawHorizontal(c, parent, state);
        }

    }


    private void drawBackGroundHorizontal(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params) {
        //绘制水平的divider
        mPaint.setColor(mTitleBgColor);
        c.drawRect(left + mPaddingStart, child.getBottom() + params.bottomMargin, right - mPaddingEnd, child.getBottom() + params.bottomMargin + mDividerWidth, mPaint);

    }

    private void drawBackGroundVertical(Canvas c, int top, int bottom, View child, RecyclerView.LayoutParams params) {
        //绘制竖直的divider
        mPaint.setColor(mTitleBgColor);
        c.drawRect(child.getRight() + params.rightMargin, top + mPaddingStart, child.getRight() + params.rightMargin + mDividerWidth, bottom - mPaddingEnd, mPaint);
    }

    /**
     * 画垂直分割线
     */
    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //去除最底部的分割线，所以减一
        int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop();
            int bottom = child.getBottom();
            if (drawDivider(child))
                drawBackGroundVertical(c, top, bottom, child, params);

        }
    }


    /**
     * 画水平分割线
     */
    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //去除最底部的分割线，所以减一
        int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft();
            int right = child.getRight();
            if (drawDivider(child))
                drawBackGroundHorizontal(c, left, right, child, params);
        }
    }


}
