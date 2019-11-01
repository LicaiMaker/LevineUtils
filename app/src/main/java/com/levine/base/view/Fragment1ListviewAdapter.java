package com.levine.base.view;

import android.content.Context;

import com.levine.base.R;
import com.levine.base.data.bean.CustomerBean;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
import com.levine.utils.app.view.adapter.BaseViewHolder;
import com.levine.utils.base.LogUtils;

import java.util.List;



public class Fragment1ListviewAdapter extends BaseRecyclerViewAdapter<CustomerBean> {
    public Fragment1ListviewAdapter(List list, Context mContext, int layId) {
        super(list, mContext, layId);
    }


    @Override
    public void convert(BaseViewHolder holder, CustomerBean bean) {
        String text= bean.getName();
        LogUtils.e("正在绑定数据："+text);
        holder.setText(R.id.mListViewTV,text);
    }
}
