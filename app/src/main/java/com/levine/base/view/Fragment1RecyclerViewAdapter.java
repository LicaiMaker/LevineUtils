package com.levine.base.view;

import android.content.Context;

import com.levine.base.R;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
import com.levine.utils.app.view.adapter.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment1RecyclerViewAdapter extends BaseRecyclerViewAdapter<HashMap<String,Object>> {
    public Fragment1RecyclerViewAdapter(List list, Context mContext, int layId) {
        super(list, mContext, layId);
    }



    @Override
    public void convert(BaseViewHolder holder, HashMap<String, Object> itemData) {
        //绑定数据，因为在adapter中支持了多布局，所以要判断一下其数据中的类型，来判断使用的是哪一个布局文件
        String type= (String) itemData.get("type");
        switch (type){
            case "ad":
                holder.setText(R.id.mFragment1TV, (CharSequence) itemData.get("mFragment1TV"));
                int imgId= (int) itemData.get("mFragment1IV");
                holder.setImageFromRes(R.id.mFragment1IV,imgId);
                break;
            case "normal":
                holder.setText(R.id.mFragment1TV, (CharSequence) itemData.get("mFragment1TV"));
                break;
        }

    }
}
