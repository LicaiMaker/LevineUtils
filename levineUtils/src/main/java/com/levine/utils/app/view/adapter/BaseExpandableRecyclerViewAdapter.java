/**
 * author: made by levine 2019/11/7
 */

package com.levine.utils.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levine.utils.app.data.BaseExpandBean;
import com.levine.utils.app.data.ItemStatus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @param <T>data type of the group item
 * @param <V>data type of the child item
 */
public abstract class BaseExpandableRecyclerViewAdapter<T, V> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<BaseExpandBean<T, V>> mDataList;
    protected List<Boolean> mParentItemStatus;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected boolean OnlyOneItemExpanded=false;


    private OnBaseItemClickListener mOnItemClickListener;
    private OnExpandItemListener mOnExpandListener;


    public BaseExpandableRecyclerViewAdapter(Context mContext, List<BaseExpandBean<T, V>> parentList) {
        this.mDataList = parentList;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        initmParentItemsStatus();
        setOnItemClickListener();
    }

    /**
     * set all the parent items status  not expanded
     */
    private void initmParentItemsStatus() {
        mParentItemStatus = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {
            mParentItemStatus.add(false);
        }

    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layId = viewType;
        View view = mLayoutInflater.inflate(layId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (0 == mParentItemStatus.size()) {
            return itemCount;
        }
        for (int i = 0; i < mDataList.size(); i++) {
            itemCount++;
            if (mParentItemStatus.get(i)) {
                itemCount += mDataList.get(i).getChild().size();
            }
        }
        return itemCount;
    }


    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        final ItemStatus currentItemStatus = getItemStatus(position);
        int viewType = currentItemStatus.getViewType();
        if (viewType == ItemStatus.ITEM_TYPE_PARENT) {
            int parentPos = currentItemStatus.getParentItemIndex();
            convertParentView(holder, mDataList.get(parentPos).getParent());
        } else {
            int parentPos = currentItemStatus.getParentItemIndex();
            int childPos = currentItemStatus.getChildItemIndex();
            convertChildView(holder, mDataList.get(parentPos).getChild().get(childPos));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    //collapse or expand the list
                    mOnItemClickListener.onItemClick(holder,view, position);
                }
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        int viewType = getItemStatus(position).getViewType();

        if (viewType == ItemStatus.ITEM_TYPE_PARENT) {
            return getParentLayId();
        } else if (viewType == ItemStatus.ITEM_TYPE_CHILD) {
            return getChildLayId();
        }
        return super.getItemViewType(position);
    }

    public abstract int getParentLayId();

    public abstract int getChildLayId();

    /**
     * @param position within size of the parent items and size of the child items
     * @return the status of current item
     */
    private ItemStatus getItemStatus(int position) {
        ItemStatus itemStatus = new ItemStatus();
        int itemCount = 0;
        int i;
        for (i = 0; i < mParentItemStatus.size(); i++) {
            if (itemCount == position) {
                //parent
                itemStatus.setViewType(ItemStatus.ITEM_TYPE_PARENT);
                itemStatus.setParentItemIndex(i);
                break;
            } else if (itemCount > position) {
                //child of one of parents
                itemStatus.setViewType(ItemStatus.ITEM_TYPE_CHILD);
                itemStatus.setParentItemIndex(i - 1);
                int temp = itemCount - mDataList.get(i - 1).getChild().size();
                itemStatus.setChildItemIndex(position - temp);
                break;
            }
            itemCount++;
            if (mParentItemStatus.get(i)) {
                itemCount += mDataList.get(i).getChild().size();
            }
        }

        if (i >= mParentItemStatus.size()) {
            itemStatus.setViewType(ItemStatus.ITEM_TYPE_CHILD);
            itemStatus.setParentItemIndex(i - 1);
            itemStatus.setChildItemIndex(position - (itemCount - mDataList.get(i - 1).getChild().size()));
        }
        return itemStatus;
    }


    private void setOnItemClickListener() {
        this.mOnItemClickListener = new OnBaseItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder,View view, int position) {
                ItemStatus itemStatus = getItemStatus(position);
                int type = itemStatus.getViewType();
                if (type == ItemStatus.ITEM_TYPE_PARENT) {
                    //collapse or expand the list
                    if(mParentItemStatus.get(itemStatus.getParentItemIndex())){
                        //parent is expanded now,then it comes to be collapsed
                        collapseParentItem(holder,itemStatus.getParentItemIndex());
                        if(mOnExpandListener!=null){
                            mOnExpandListener.onCollapsed(view,itemStatus.getParentItemIndex());
                        }
                    }else{
                        //parent is collapsed now,then it comes to be expanded
                        expandParentItem(holder,itemStatus.getParentItemIndex());
                        if(mOnExpandListener!=null){
                            mOnExpandListener.onExpanded(view,itemStatus.getParentItemIndex());
                        }
                    }
                } else {
                    //child item is clicked,send two parameters out:parent position and child position
                    if(mOnExpandListener!=null){
                        mOnExpandListener.onChildClick(view,itemStatus.getParentItemIndex(),itemStatus.getChildItemIndex());
                    }
                }
            }
        };

    }

    private void collapseParentItem(BaseViewHolder holder,int parentItemIndex) {
        mParentItemStatus.set(parentItemIndex,false);
        notifyItemRangeRemoved(holder.getAdapterPosition()+1,mDataList.get(parentItemIndex).getChild().size());
        notifyItemRangeChanged(0,getItemCount());
    }

    private void expandParentItem(BaseViewHolder holder,int parentItemIndex) {
        if(OnlyOneItemExpanded) {
            initmParentItemsStatus();
            notifyDataSetChanged();
        }
        mParentItemStatus.set(parentItemIndex,true);
        notifyItemRangeInserted(holder.getAdapterPosition()+1,mDataList.get(parentItemIndex).getChild().size());
        notifyItemRangeChanged(0,getItemCount());
    }


    public void setOnlyOneItemExpanded(boolean onlyOneItemExpanded) {
        OnlyOneItemExpanded = onlyOneItemExpanded;
    }

    public void setmOnExpandListener(OnExpandItemListener mOnExpandListener) {
        this.mOnExpandListener = mOnExpandListener;
    }

    public abstract void convertParentView(BaseViewHolder holder, T itemData);

    public abstract void convertChildView(BaseViewHolder holder, V itemData);

    /**
     * item click listener
     */
    public interface OnBaseItemClickListener {
        void onItemClick(BaseViewHolder holder,View view, int pos);
    }

    public interface OnExpandItemListener {
        void onExpanded(View view, int pos);

        void onCollapsed(View view, int pos);

        void onChildClick(View view, int parentItemIndex, int childItemIndex);
    }


}
