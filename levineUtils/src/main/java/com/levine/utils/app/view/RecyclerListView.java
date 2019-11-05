package com.levine.utils.app.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class RecyclerListView extends RecyclerView {
    public RecyclerListView(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private int orientation = RecyclerView.VERTICAL;//默认为纵向

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
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(this);
    }

}
