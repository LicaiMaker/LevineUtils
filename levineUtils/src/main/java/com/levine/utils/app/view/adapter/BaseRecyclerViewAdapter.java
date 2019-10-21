package com.levine.utils.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> tList;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected int layId;

    private OnBaseItemClickListener mOnItemClickListener;
    private MultiTypeSupport multiTypeSupport;


    public BaseRecyclerViewAdapter(List<T> tList, Context mContext, int layId) {
        this.tList = tList;
        this.mContext = mContext;
        this.layId = layId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 多布局支持
        if (multiTypeSupport != null) {
            layId = viewType;//这个就是getItemViewType方法的返回值，我们直接使用布局Id来做返回值，可以省略设置静态int值。
        }
        View view = mLayoutInflater.inflate(layId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        convert(holder, tList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        if (multiTypeSupport!=null){
            return multiTypeSupport.getLayoutId(tList.get(position));
        }
        return super.getItemViewType(position);
    }

    public void setMultiTypeSupport(MultiTypeSupport<T> multiTypeSupport){
        this.multiTypeSupport=multiTypeSupport;
    }

    public void setOnItemClickListener(OnBaseItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }

    public abstract void convert(BaseViewHolder holder, T itemData);

    /**
     * 点击事件接口
     */
    public interface OnBaseItemClickListener {
        void onItemClick(int position);
    }

    /**
     * 多布局接口
     * @param <T>
     */
    public interface  MultiTypeSupport<T>{
        int getLayoutId(T item);
    }


}
