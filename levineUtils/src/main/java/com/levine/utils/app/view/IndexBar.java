package com.levine.utils.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.levine.utils.R;
import com.levine.utils.app.data.BaseBean;
import com.levine.utils.base.DensityUtils;
import com.levine.utils.base.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IndexBar extends AppCompatTextView {

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private int mPressedBackground = 0;
    private int mUnPressedBackgroud = 0;
    private int textSize = 20;
    private int textColor = Color.BLACK;
    private int selectedTextColor = Color.RED;
    private Paint mPaint;
    private List<String> mIndexDatas = null;
    private List<? extends BaseBean> mSourceDatas = null;

    private int mGapHeight=40;//每个index区域的高度
    private int mWidth = 30;
    private int mHeight = 100;
    private Drawable mUnPressedDrawable = null;
    private Drawable mPressedDrawable = null;

    private TextView mPressedShowTextView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mIndexDatas = new ArrayList<>();

        textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());//默认的TextSize

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.IndexBar_textSize) {
                textSize = typedArray.getDimensionPixelSize(attr, textSize);
            } else if (attr == R.styleable.IndexBar_pressBackground) {
                mPressedDrawable = typedArray.getDrawable(attr);
                if (mPressedDrawable == null) {

                    mPressedBackground = typedArray.getColor(attr, mPressedBackground);

                }
            } else if (attr == R.styleable.IndexBar_unpressBackground) {
                mUnPressedDrawable = typedArray.getDrawable(attr);
                if (mUnPressedDrawable == null) {
                    mUnPressedBackgroud = typedArray.getColor(attr, mUnPressedBackgroud);
                    setBackgroundColor(mUnPressedBackgroud);
                } else {
                    setBackground(mUnPressedDrawable);
                }
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(textSize);

        initIndexBarListener();

    }

    private int selectedIndexBarPosition = 0;

    private void initIndexBarListener() {
        //设置index触摸监听器
        setmOnIndexPressedListener(new onIndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                if (mPressedShowTextView != null) { //显示hintTexView
                    mPressedShowTextView.setVisibility(View.VISIBLE);
                    mPressedShowTextView.setText(text);
                }
                //滑动Rv
                if (mLayoutManager != null) {
                    int position = getPosByTag(text);
                    if (position != -1) {
                        mLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }
                setSelectedIndexBarPosition(index);
            }

            @Override
            public void onMotionEventEnd() {
                //隐藏hintTextView
                if (mPressedShowTextView != null) {
                    mPressedShowTextView.setVisibility(View.GONE);
                }
            }
        });

    }

    public IndexBar setSelectedIndexBarPosition(int pos) {
        this.selectedIndexBarPosition = pos;
        invalidate();
        return this;
    }


    public IndexBar setTextSize(int textSize) {
        this.textSize = textSize;
        mPaint.setTextSize(textSize);
        invalidate();
        return this;
    }

    public IndexBar setmIndexDatas(List<? extends BaseBean> sourceDatas) {

        notifyIndexBarDataChanged(sourceDatas);
        return this;
    }

    public IndexBar notifyIndexBarDataChanged(List<? extends BaseBean> sourceDatas){
        this.mSourceDatas = sourceDatas;
        this.mIndexDatas = getmIndexDatas(sourceDatas);
        this.measure(0,0);
        if(this.sizeChangedListener!=null){
            this.sizeChangedListener.onSizeChanged(mIndexDatas);
        }

        addRecyclerViewScrollListener(recyclerView);
        return this;
    };

    private List<String> getmIndexDatas(List<? extends BaseBean> sourceDatas) {
        List<String> list1 = new ArrayList<>();
        for (BaseBean bean : sourceDatas) {
            if (!list1.contains(bean.getBeanType()))
                list1.add(bean.getBeanType());
        }
        return list1;
    }

    public IndexBar setIndexTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
        return this;
    }

    public IndexBar setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
        invalidate();
        return this;
    }

    public IndexBar setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        addRecyclerViewScrollListener(recyclerView);
        invalidate();
        return this;
    }

    /**
     * 在setmIndexDatas方法之后
     *
     * @param mRecyclerView
     */
    private void addRecyclerViewScrollListener(RecyclerView mRecyclerView) {
        if (mIndexDatas.size() == 0) {
            return;
        }
        if(mRecyclerView==null)return;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int pos = mLayoutManager.findFirstVisibleItemPosition();
                String type = mSourceDatas.get(pos).getBeanType();
                int temp = mIndexDatas.indexOf(type);
                setSelectedIndexBarPosition(temp);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //取出宽高的MeasureSpec  Mode 和Size
LogUtils.e("onMeasure");
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = 30, measureHeight =mGapHeight;//默认宽高

        //得到合适宽度：
        Rect indexBounds = new Rect();//存放每个绘制的index的Rect区域
        String index;//每个要绘制的index内容
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            mPaint.getTextBounds(index, 0, index.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
            measureWidth = Math.max(indexBounds.width(), measureWidth);//循环结束后，得到index的最大宽度
            // TODO: 2019/11/29 这里吧mGapHeight加上，可以改变高度，控制台打印高度也是变化了的，但是，需要点击indexbar或者，刷新页面，才能看到效果，
            measureHeight = Math.max(indexBounds.height()/*+mGapHeight*/, measureHeight);//循环结束后，得到index的最大高度，然后*size
        }
        measureHeight *= mIndexDatas.size();
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                measureWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = Math.min(measureWidth, wSize);//wSize此时是父控件能给子View分配的最大空间
                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }

        //得到合适的高度：
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                measureHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                measureHeight = Math.min(measureHeight, hSize);//hSize此时是父控件能给子View分配的最大空间
                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }
        if(measureHeight==0){
            if(mIndexDatas.size()==0){
                measureHeight= DensityUtils.dp2px(200);//默认高度
                LogUtils.e("哈哈1："+measureHeight);
            }
        }
        if (measureWidth==0){
            measureWidth=30;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.e("onDraw");
        int t = getPaddingTop();//top的基准点(支持padding)
        Rect indexBounds = new Rect();//存放每个绘制的index的Rect区域
        String index;//每个要绘制的index内容
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            mPaint.getTextBounds(index, 0, index.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();//获得画笔的FontMetrics，用来计算baseLine。因为drawText的y坐标，代表的是绘制的文字的baseLine的位置
            int baseline = (int) ((mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2);//计算出在每格index区域，竖直居中的baseLine值
            if (selectedIndexBarPosition == i) {
                mPaint.setColor(selectedTextColor);
            }
            canvas.drawText(index, mWidth / 2 - indexBounds.width() / 2, t + mGapHeight * i + baseline, mPaint);//调用drawText，居中显示绘制index
            mPaint.setColor(textColor);
        }

    }


    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setSizeChangedListener(new SizeChangedListener() {
            @Override
            public void onSizeChanged(List<String> mIndexDatas) {

                LogUtils.e("onSizeChanged");
                mWidth = w;
                mHeight = h;
                if (mIndexDatas.size() != 0) {
                    mGapHeight = (mHeight - getPaddingTop() - getPaddingBottom()) / mIndexDatas.size();
                } else {
                    mGapHeight = DensityUtils.dp2px(55);
                }
                IndexBar.this.invalidate();
            }
        });
        this.sizeChangedListener.onSizeChanged(mIndexDatas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIndexDatas.size() == 0) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPressedDrawable == null) {
                    setBackgroundColor(mPressedBackground);//手指按下时背景变色
                } else {
                    setBackground(mPressedDrawable);
                }
                //注意这里没有break，因为down时，也要计算落点 回调监听器
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //通过计算判断落点在哪个区域：
                int pressI = (int) ((y - getPaddingTop()) / mGapHeight);
                //边界处理（在手指move时，有可能已经移出边界，防止越界）
                if (pressI < 0) {
                    pressI = 0;
                } else if (pressI >= mIndexDatas.size()) {
                    pressI = mIndexDatas.size() - 1;
                }
                //回调监听器
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onIndexPressed(pressI, mIndexDatas.get(pressI));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                if (mUnPressedDrawable != null)
                    setBackground(mUnPressedDrawable);
                else
                    setBackgroundColor(mUnPressedBackgroud);//手指抬起时背景恢复透明
                //回调监听器
                if (null != mOnIndexPressedListener) {
                    mOnIndexPressedListener.onMotionEventEnd();
                }
                break;
        }
        return true;
    }

    /**
     * 当前被按下的index的监听器
     */
    public interface onIndexPressedListener {
        void onIndexPressed(int index, String text);//当某个Index被按下

        void onMotionEventEnd();//当触摸事件结束（UP CANCEL）
    }

    private onIndexPressedListener mOnIndexPressedListener;

    public void setmOnIndexPressedListener(onIndexPressedListener mOnIndexPressedListener) {
        this.mOnIndexPressedListener = mOnIndexPressedListener;
    }


    public IndexBar setmPressedShowTextView(TextView mPressedShowTextView) {
        this.mPressedShowTextView = mPressedShowTextView;
        return this;
    }

//    public IndexBar setmLayoutManager(LinearLayoutManager mLayoutManager) {
//        this.mLayoutManager = mLayoutManager;
//        return this;
//    }

    /**
     * 根据传入的pos返回tag
     *
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }

        for (int i = 0; i < mSourceDatas.size(); i++) {
            if (tag.equals(mSourceDatas.get(i).getBeanType())) {
                return i;
            }
        }
        return -1;
    }

    private SizeChangedListener sizeChangedListener;
    private void setSizeChangedListener(SizeChangedListener listener){
        sizeChangedListener=listener;
    }

    private interface SizeChangedListener{

        void onSizeChanged(List<String> mIndexDatas);
    }
}
