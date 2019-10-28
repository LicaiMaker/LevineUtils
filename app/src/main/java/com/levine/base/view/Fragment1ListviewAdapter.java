package com.levine.base.view;

import android.content.Context;

import com.levine.base.R;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
import com.levine.utils.app.view.adapter.BaseViewHolder;
import com.levine.utils.base.LogUtils;

import java.util.HashMap;
import java.util.List;



public class Fragment1ListviewAdapter extends BaseRecyclerViewAdapter<HashMap<String,Object>> {
    public Fragment1ListviewAdapter(List list, Context mContext, int layId) {
        super(list, mContext, layId);
    }


    @Override
    public void convert(BaseViewHolder holder, HashMap<String, Object> itemData) {
        String text= (String) itemData.get("text");
        LogUtils.e("正在绑定数据："+text);
        holder.setText(R.id.mListViewTV,text);
    }
}
