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
import com.levine.utils.base.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@TargetFragmentTag(FragmentTag.FRAGMENT1)
public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
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
        final RecyclerViewPager recyclerViewPager = view.findViewById(R.id.mRVPager);
        Fragment1RecyclerViewAdapter adapter = new Fragment1RecyclerViewAdapter(pageDatas, this.getActivity(), R.layout.horizontal_viewpager_page_view);
        final TabLayout tabLayout=view.findViewById(R.id.mFragment1TL);
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

}
