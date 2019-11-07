package com.levine.utils.app.data;

import androidx.annotation.NonNull;

public class ItemStatus {

    public static final int ITEM_TYPE_PARENT=0;
    public static final int ITEM_TYPE_CHILD=1;

    private int viewType;
    private int parentItemIndex=0;
    private int childItemIndex=-1;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getParentItemIndex() {
        return parentItemIndex;
    }

    public void setParentItemIndex(int parentItemIndex) {
        this.parentItemIndex = parentItemIndex;
    }

    public int getChildItemIndex() {
        return childItemIndex;
    }

    public void setChildItemIndex(int childItemIndex) {
        this.childItemIndex = childItemIndex;
    }

    @NonNull
    @Override
    public String toString() {
        String isParent=viewType==0?"父节点":"子节点";
        return "("+parentItemIndex+","+childItemIndex+")"+"是"+isParent;
    }
}
