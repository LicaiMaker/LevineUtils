package com.levine.utils.app.view.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 自定义ViewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewArray;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViewArray = new SparseArray<>();
    }

    /**
     * 通过id得到view
     *
     * @param viewId
     * @param <V>
     * @return
     */
    public <V extends View> V getViewAtId(int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (V) view;
    }

    /**
     * 设置文字
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getViewAtId(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 通过网址设置图片
     *
     * @param viewId
     * @param imgUrl
     * @return
     */
    public BaseViewHolder setImageFromUrl(int viewId, String imgUrl) {
        ImageView imageView = getViewAtId(viewId);
//            Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
        return this;
    }

    /**
     * 通过资源来设置图片资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    public BaseViewHolder setImageFromRes(int viewId, int resId) {
        ImageView imageView = getViewAtId(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置控件透明度
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public BaseViewHolder setViewVisibility(int viewId, int visibility) {
        View view = getViewAtId(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 设置recyclerview的适配器，用于recyclerview嵌套时使用
     * @param viewId recyclerview的id
     * @param adapter recyclerview的adapter
     * @return
     */
    public BaseViewHolder setRecyclerViewAdapter(int viewId,BaseRecyclerViewAdapter adapter){
        RecyclerView recyclerView=getViewAtId(viewId);
        recyclerView.setAdapter(adapter);
        return this;
    }

    /**
     * 设置view的点击事件
     * @param viewId view的id
     * @param listener View.OnClickListener
     * @return
     */
    public BaseViewHolder setOnViewClickListener(int viewId,View.OnClickListener listener){
        View view=getViewAtId(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置view的长按点击事件
     * @param viewId view的id
     * @param listener View.OnLongClickListener
     * @return
     */
    public BaseViewHolder setOnViewLongClickListener(int viewId,View.OnLongClickListener listener){
        View view=getViewAtId(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 设置textview的字体颜色
     * @param viewId TextView的id
     * @param color 颜色值
     * @return
     */
    public BaseViewHolder setTextColor(int viewId,int color){
        TextView textView=getViewAtId(viewId);
        textView.setTextColor(color);
        return this;
    }

}

