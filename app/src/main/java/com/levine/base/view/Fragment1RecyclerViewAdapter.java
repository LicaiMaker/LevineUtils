package com.levine.base.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.levine.base.R;
import com.levine.base.data.bean.CustomerBean;
import com.levine.utils.app.data.BaseBean;
import com.levine.utils.app.view.IndexBar;
import com.levine.utils.app.view.LevineItemDecoration;
import com.levine.utils.app.view.RecyclerListView;
import com.levine.utils.app.view.TitleItemDecoration;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
import com.levine.utils.app.view.adapter.BaseViewHolder;
import com.levine.utils.base.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Fragment1RecyclerViewAdapter extends BaseRecyclerViewAdapter<HashMap<String, Object>> {
    public Fragment1RecyclerViewAdapter(List list, Context mContext, int layId) {
        super(list, mContext, layId);
    }


    @Override
    public void convert(BaseViewHolder holder, HashMap<String, Object> itemData) {
        //绑定数据，因为在adapter中支持了多布局，所以要判断一下其数据中的类型，来判断使用的是哪一个布局文件
        String type = (String) itemData.get("type");
        switch (type) {
            case "ad":
                holder.setText(R.id.mFragment1TV, (CharSequence) itemData.get("mFragment1TV"));
                int imgId = (int) itemData.get("mFragment1IV");
                holder.setImageFromRes(R.id.mFragment1IV, imgId);

                break;
            case "normal":
//                holder.setText(R.id.mFragment1TV, (CharSequence) itemData.get("mFragment1TV"));
                final List<BaseBean> list = getCustomerData();
                Fragment1ListviewAdapter adapter = new Fragment1ListviewAdapter(list, mContext, R.layout.list_item_view);
                final RecyclerListView recyclerListView = holder.getViewAtId(R.id.mFragment1RV);

//                PaddingItemDecoration variableItemDecoration=new PaddingItemDecoration(1);
//                variableItemDecoration.setmPaddingStart(30);
//                variableItemDecoration.setmPaddingEnd(30);

//                recyclerListView.addItemDecoration(variableItemDecoration);

                LevineItemDecoration levineItemDecoration = new LevineItemDecoration(mContext, LinearLayout.VERTICAL);
                levineItemDecoration.setmPaddingStart(30);
                levineItemDecoration.setmPaddingEnd(30);
                levineItemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_divider));
                levineItemDecoration.setDataList(list);
                recyclerListView.addItemDecoration(levineItemDecoration);
//                PaddingItemDecoration variableItemDecoration=new PaddingItemDecoration(LinearLayout.VERTICAL);
//                variableItemDecoration.setDataList(list);
//                recyclerListView.addItemDecoration(variableItemDecoration);
                TitleItemDecoration titleItemDecoration = new TitleItemDecoration(list, 80);
//                titleItemDecoration.setHeaderViewLayoutId(R.layout.complex_header_item_decoration);
                recyclerListView.addItemDecoration(titleItemDecoration);

                final IndexBar mIndexBar = holder.getViewAtId(R.id.mIndexBar);
                TextView mIndexTV = holder.getViewAtId(R.id.mIndexTV);
                String a[] = {"B", "C", "D", "E", "F", "G"};
                final List<String> indexBarList=Arrays.asList(a);
                mIndexBar.setmIndexDatas(indexBarList)
                        .setmPressedShowTextView(mIndexTV)
                        .setmLayoutManager((LinearLayoutManager) recyclerListView.getLayoutManager())
                        .setIndexTextColor(Color.GRAY)
                        .setSelectedTextColor(Color.BLACK);
                //和indexbar联动
                recyclerListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        LinearLayoutManager mLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                        int pos=mLayoutManager.findFirstVisibleItemPosition();
                        String type=list.get(pos).getBeanType();
                        int temp=indexBarList.indexOf(type.toUpperCase());
                        mIndexBar.setSelectedIndexBarPosition(temp);
                        LogUtils.e("type&pos:"+type+","+temp);

                    }
                });
                holder.setRecyclerViewAdapter(R.id.mFragment1RV, adapter);
                LogUtils.e("setting Fragment1ListviewAdapter：");
                adapter.notifyDataSetChanged();
                break;
        }

    }

    public List<BaseBean> getCustomerData() {
        List<BaseBean> list = new ArrayList<>();
        BaseBean b1 = new CustomerBean("b");
        BaseBean b2 = new CustomerBean("c");
        BaseBean b3 = new CustomerBean("c");
        BaseBean b4 = new CustomerBean("d");
        BaseBean b5 = new CustomerBean("d");
        BaseBean b6 = new CustomerBean("e");
        BaseBean b7 = new CustomerBean("e");
        BaseBean b8 = new CustomerBean("e");
        BaseBean b9 = new CustomerBean("f");
        BaseBean b10 = new CustomerBean("g");
        BaseBean b11 = new CustomerBean("g");
        BaseBean b12 = new CustomerBean("h");
        list.add(b1);
        list.add(b2);
        list.add(b3);
        list.add(b4);
        list.add(b5);
        list.add(b6);
        list.add(b7);
        list.add(b8);
        list.add(b9);
        list.add(b10);
        list.add(b11);
        list.add(b12);
        return list;
    }
}
