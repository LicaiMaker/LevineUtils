package com.levine.utils.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.levine.utils.app.data.BaseBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LevineItemDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private int mPaddingStart = 0;
    private int mPaddingEnd = 0;


    private List<BaseBean> dataList = null;
    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();


    public LevineItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        a.recycle();
        setOrientation(orientation);
    }


    public LevineItemDecoration setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
        return this;
    }


    public LevineItemDecoration setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
        return this;
    }

    public LevineItemDecoration setmPaddingStart(int mPaddingStart) {
        this.mPaddingStart = mPaddingStart;
        return this;
    }

    public LevineItemDecoration setmPaddingEnd(int mPaddingEnd) {
        this.mPaddingEnd = mPaddingEnd;return this;
    }

    public LevineItemDecoration setDataList(List<BaseBean> dataList) {
        this.dataList = dataList;return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private boolean drawDivider(View child) {
        if(dataList==null){
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

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }
        //去除最底部的分割线，所以减一
        final int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDivider.getIntrinsicHeight();

            if (drawDivider(child)) {
                mDivider.setBounds(left + mPaddingStart, top, right - mPaddingEnd, bottom);
                mDivider.draw(canvas);
            }

        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }
        //去除最底部的分割线，所以减一
        final int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDivider.getIntrinsicWidth();

            if (drawDivider(child)) {
                mDivider.setBounds(left, top + mPaddingStart, right, bottom - mPaddingEnd);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            if (drawDivider(view))
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            else
                outRect.set(0, 0, 0, 0);
        } else {
            if (drawDivider(view))
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            else
                outRect.set(0, 0, 0, 0);
        }
    }
}


