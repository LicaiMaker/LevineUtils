package com.levine.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.levine.base.R;
import com.levine.utils.app.data.BaseBean;
import com.levine.utils.app.data.BaseExpandBean;
import com.levine.utils.app.fragment.TargetFragmentTag;
import com.levine.utils.app.view.RecyclerListView;
import com.levine.utils.app.view.RoundImageViewByXfermode;
import com.levine.utils.app.view.adapter.BaseExpandableRecyclerViewAdapter;
import com.levine.utils.app.view.adapter.BaseViewHolder;
import com.levine.utils.base.LevineAnnotationUtils;
import com.levine.utils.base.LevineBindView;
import com.levine.utils.base.LevineOnClick;
import com.levine.utils.base.LogUtils;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

@TargetFragmentTag(FragmentTag.FRAGMENT2)
public class Fragment2 extends Fragment implements View.OnClickListener{
    @LevineBindView(R.id.mHahaTV)
    @LevineOnClick
    private TextView mHahaTV;

    @LevineBindView(R.id.mFragment2RLV)
    private RecyclerListView mFragment2RLV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        LevineAnnotationUtils.bind(this,view);
        mHahaTV.setText("嘿嘿");
        init();
        return view;
    }

    private void init(){
        List<BaseExpandBean<String,String>> list=new ArrayList<>();
        List<String> A=new ArrayList<>();
        A.add("a1");
        A.add("a2");
        BaseExpandBean baseExpandBean1=new BaseExpandBean("A",A);

        List<String> B=new ArrayList<>();
        B.add("b1");
        BaseExpandBean baseExpandBean2=new BaseExpandBean("B",B);
        list.add(baseExpandBean1);
        list.add(baseExpandBean2);

        BaseExpandableRecyclerViewAdapter adapter=new BaseExpandableRecyclerViewAdapter<String,String>(this.getContext(),list){
            @Override
            public int getParentLayId() {
                return R.layout.parent_item;
            }

            @Override
            public int getChildLayId() {
                return R.layout.child_item;
            }

            @Override
            public void convertParentView(BaseViewHolder holder, String itemData) {
                holder.setText(R.id.parentTV,itemData);
                holder.setImageFromUrl(R.id.parentIV,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1295982982,3250387184&fm=26&gp=0.jpg");
            }

            @Override
            public void convertChildView(BaseViewHolder holder, String itemData) {
                holder.setText(R.id.childTV,itemData);
                holder.setImageFromUrl(R.id.childIV,"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2188301108,2208747323&fm=26&gp=0.jpg");
            }
        };

        adapter.setmOnExpandListener(new BaseExpandableRecyclerViewAdapter.OnExpandItemListener() {
            @Override
            public void onExpanded(View view, int pos) {
                LogUtils.e("pos:"+pos+" 展开了");
                ImageView view1=view.findViewById(R.id.parentIV);
                view1.setImageResource(R.drawable.icon_list_unexpand);
            }

            @Override
            public void onCollapsed(View view, int pos) {
                LogUtils.e("pos:"+pos+" 收缩了");
                ImageView view1=view.findViewById(R.id.parentIV);
                view1.setImageResource(R.drawable.icon_list_expand);
            }

            @Override
            public void onChildClick(View view, int parentItemIndex, int childItemIndex) {
                LogUtils.e("pos:（p，c）:"+"("+parentItemIndex+","+childItemIndex+")被点击了");
            }
        });
//        adapter.setOnlyOneItemExpanded(true);
        mFragment2RLV.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mHahaTV:
                LogUtils.e("正在点击按钮");
                break;
        }
    }
}
