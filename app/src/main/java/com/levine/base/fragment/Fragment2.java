package com.levine.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.levine.base.R;
import com.levine.utils.app.fragment.TargetFragmentTag;
import com.levine.utils.base.LevineAnnotationUtils;
import com.levine.utils.base.LevineBindView;
import com.levine.utils.base.LevineOnClick;
import com.levine.utils.base.LogUtils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@TargetFragmentTag(FragmentTag.FRAGMENT2)
public class Fragment2 extends Fragment implements View.OnClickListener{
    @LevineBindView(R.id.mHahaTV)
    @LevineOnClick
    private TextView mHahaTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        LevineAnnotationUtils.bind(this,view);
        mHahaTV.setText("嘿嘿");

        return view;
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
