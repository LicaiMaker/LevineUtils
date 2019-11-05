package com.levine.utils.app.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levine.utils.app.data.BaseBean;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TitleItemDecoration extends RecyclerView.ItemDecoration {

    private List<BaseBean> mDatas;

    private int mTitleHeight;

    private Paint mPaint;

    private int mTitleTextSize = 40;

    private Rect mBounds;

    private int mTitleBgColor = Color.parseColor("#D8D8D8");

    private int mTitleFontColor = Color.parseColor("#FFFFFF");

    private int mTitleMarginLeft = 10;

    private int selectedAnimation = TopBarChangeAnimation.TRANSLATION;

    private int headerViewLayoutId = 0;

    public class TopBarChangeAnimation {
        public static final int TRANSLATION = 0;
        private static final int OVERLAY = 1;


    }

    ;

    public TitleItemDecoration(List datas, int height) {
        this.mDatas = datas;
        this.mTitleHeight = height;
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBounds = new Rect();
    }

    public void setmTitleBgColor(int mTitleBgColor) {
        this.mTitleBgColor = mTitleBgColor;
    }

    public void setmTitleFontColor(int mTitleFontColor) {
        this.mTitleFontColor = mTitleFontColor;
    }

    public void setmTitleTextSize(int mTitleTextSize) {
        this.mTitleTextSize = mTitleTextSize;
        mPaint.setTextSize(mTitleTextSize);
    }

    public void setmTitleMarginLeft(int mTitleMarginLeft) {
        this.mTitleMarginLeft = mTitleMarginLeft;
    }

    public void setSelectedAnimation(int selectedAnimation) {
        this.selectedAnimation = selectedAnimation;
    }

    public void setHeaderViewLayoutId(int headerViewLayoutId) {
        this.headerViewLayoutId = headerViewLayoutId;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //获取到当前的position
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                //此时是需要绘制title
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                if (mDatas.get(position).getBeanType() != null &&
                        !mDatas.get(position).getBeanType().equals(mDatas.get(position - 1).getBeanType())) {
                    //beanType不为空，而且和上一个的beanType不一致，则说明连个分类不一致，则需要添加title
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }

            }
        }

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                if (position == 0) {
                    drawTitleText(c, left, right, child, params, position);
                } else {
                    if (mDatas.get(position).getBeanType() != null &&
                            !mDatas.get(position).getBeanType().equals(mDatas.get(position - 1).getBeanType())) {
                        drawTitleText(c, left, right, child, params, position);
                    } else {
                    }
                }
            }
        }
    }

    private void drawTitleText(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        //绘制title背景
        mPaint.setColor(mTitleBgColor);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(mTitleFontColor);
        mPaint.getTextBounds(mDatas.get(position).getBeanType(), 0, mDatas.get(position).getBeanType().length(), mBounds);
        c.drawText(mDatas.get(position).getBeanType(), left + child.getPaddingLeft() + mTitleMarginLeft, child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        String beanType = mDatas.get(position).getBeanType();
        View child = parent.findViewHolderForLayoutPosition(position).itemView;
        boolean isSavingCanvas = false;
        if (position + 1 < mDatas.size()) {
            if (beanType != null && !mDatas.get(position + 1).getBeanType().equals(beanType)) {
                if (child.getHeight() + child.getTop() < mTitleHeight) {
                    //当可见child部分小于title的高度
                    c.save();
                    isSavingCanvas = true;
                    if (selectedAnimation == TopBarChangeAnimation.TRANSLATION)
                        c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
                    else if (selectedAnimation == TopBarChangeAnimation.OVERLAY)
                        c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + child.getHeight() + child.getTop());

                }
            }
        }
        if (headerViewLayoutId != 0) {
            //设置了悬停的头部view
            drawHeaderView(c, parent, headerViewLayoutId);
        } else {
            //采用默认的头部view
            mPaint.setColor(mTitleBgColor);
            //绘制背景
            c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
            //绘制文字
            mPaint.setColor(mTitleFontColor);
            mPaint.getTextBounds(beanType, 0, mDatas.get(position).getBeanType().length(), mBounds);
            c.drawText(beanType, child.getPaddingLeft() + parent.getPaddingLeft() + mTitleMarginLeft, parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
        }
        if (isSavingCanvas)
            c.restore();
    }

    private void drawHeaderView(@NonNull Canvas c, @NonNull RecyclerView parent, int viewLayoutId) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View toDrawView = mInflater.inflate(viewLayoutId, parent, false);
        int toDrawWidthSpec;//用于测量的widthMeasureSpec
        int toDrawHeightSpec;//用于测量的heightMeasureSpec
        //拿到复杂布局的LayoutParams，如果为空，就new一个。
        // 后面需要根据这个lp 构建toDrawWidthSpec，toDrawHeightSpec
        ViewGroup.LayoutParams lp = toDrawView.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//这里是根据复杂布局layout的width height，new一个Lp
            toDrawView.setLayoutParams(lp);
        }
        if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
        } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //高度同理
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.EXACTLY);
        } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.AT_MOST);
        } else {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上。
        toDrawView.measure(toDrawWidthSpec, toDrawHeightSpec);
        toDrawView.layout(parent.getPaddingLeft(), parent.getPaddingTop(),
                parent.getPaddingLeft() + toDrawView.getMeasuredWidth(), parent.getPaddingTop() + toDrawView.getMeasuredHeight());
        toDrawView.draw(c);
    }
}
