package com.levine.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.levine.base.R;
import com.levine.base.view.Fragment1RecyclerViewAdapter;
import com.levine.utils.app.fragment.TargetFragmentTag;
import com.levine.utils.app.view.RecyclerViewPager;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
import com.levine.utils.base.LevineAnnotationUtils;
import com.levine.utils.base.LevineBindView;
import com.levine.utils.base.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@TargetFragmentTag(FragmentTag.FRAGMENT1)
public class Fragment1 extends Fragment {
    @LevineBindView(R.id.mFragment1TL)
    TabLayout tabLayout;
    @LevineBindView(R.id.mRVPager)
    RecyclerViewPager recyclerViewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        LevineAnnotationUtils.bind(this,view);
        init(view);
        return view;
    }


    public void init(View view){

        List pageDatas = new ArrayList<HashMap<String, Object>>();
        pageDatas.add(new HashMap<String, Object>() {{
            put("mFragment1TV", "callhistory");
            put("mFragment1IV", R.mipmap.ic_launcher);
            put("type", "normal");
        }});
        pageDatas.add(new HashMap<String, Object>() {{
            put("mFragment1TV", "meetinghistory");
            put("mFragment1IV", R.mipmap.ic_launcher);
            put("type", "ad");
        }});

        Fragment1RecyclerViewAdapter adapter = new Fragment1RecyclerViewAdapter(pageDatas, this.getActivity(), R.layout.horizontal_viewpager_page_view);

        //多布局支持
        adapter.setMultiTypeSupport(new BaseRecyclerViewAdapter.MultiTypeSupport<HashMap<String, Object>>() {
            @Override
            public int getLayoutId(HashMap<String, Object> item) {
                String type = (String) item.get("type");
                if (type.equals("ad")) {
                    return R.layout.horizontal_viewpager_page_view_ad;
                } else {
                    return R.layout.horizontal_viewpager_page_view;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnBaseItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LogUtils.e("adapter.onItemClick");
            }
        });
        recyclerViewPager.setAdapter(adapter);
        recyclerViewPager.setTabLayout(tabLayout);
        recyclerViewPager.setScrollHorizontallyEnable(false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("onActivityCreated:");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // 保存fragment的状态
        LogUtils.e("position:onSaveInstanceState");
        outState.putInt("tabPosition",tabLayout.getSelectedTabPosition());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){
            //回复fragment的状态
            int position=savedInstanceState.getInt("tabPosition");
            tabLayout.getTabAt(position).select();
            LogUtils.e("position:onViewStateRestored:"+position);
        }
    }
}
