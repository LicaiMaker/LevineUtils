package com.levine.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.levine.base.Main2Activity;
import com.levine.base.R;
import com.levine.base.data.bean.GridBean;
import com.levine.utils.app.fragment.FragmentFactory;
import com.levine.utils.app.fragment.TargetFragmentTag;
import com.levine.utils.app.view.GridRecyclerView;
import com.levine.utils.app.view.adapter.BaseRecyclerViewAdapter;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;

@TargetFragmentTag(FragmentTag.FRAGMENT3)
public class Fragment3 extends Fragment implements View.OnClickListener{
    @LevineBindView(R.id.mGridView)
    private GridRecyclerView mGridView;

    @LevineBindView(R.id.mEditTextET)
    private EditText mEditTextET;

    @LevineOnClick
    @LevineBindView(R.id.mConfirmBTN)
    private Button mConfirmBTN;


    @LevineOnClick
    @LevineBindView(R.id.mNextBTN)
    private Button mNextBTN;
    FragmentFactory mFactory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment3,container,false);
        LevineAnnotationUtils.bind(this,view);
        mFactory=FragmentFactory.getInstance();
        init();
        return view;
    }

    private void init(){
        List<GridBean> list=new ArrayList<>();
        list.add(new GridBean(R.mipmap.ic_launcher_round,"A"));
        list.add(new GridBean(R.mipmap.ic_launcher_round,"B"));
        list.add(new GridBean(R.mipmap.ic_launcher_round,"C"));
        list.add(new GridBean(R.mipmap.ic_launcher_round,"D"));
        list.add(new GridBean(R.mipmap.ic_launcher_round,"E"));
        list.add(new GridBean(R.mipmap.ic_launcher_round,"F"));
        BaseRecyclerViewAdapter adapter=new BaseRecyclerViewAdapter<GridBean>(list,getContext(),R.layout
        .fragment3_grid_item) {
            @Override
            public void convert(BaseViewHolder holder, GridBean itemData) {
                holder.setImageFromRes(R.id.mFragmentGridItemViewAvatarIV,itemData.getAvatar());
                holder.setText(R.id.mFragmentGridItemViewNameTV,itemData.getName());
            }
        };
        mGridView.setAdapter(adapter);
        mGridView.addItemDecoration(new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL));
        mGridView.addItemDecoration(new DividerItemDecoration(this.getContext(),DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mConfirmBTN:
                if(TextUtils.isEmpty(mEditTextET.getText())){
                    return;
                }
                int spanCount=Integer.parseInt(mEditTextET.getText().toString());
                if(spanCount<=0){
                    spanCount=1;
                }
                mGridView.setSpanCount(spanCount);
                break;
            case R.id.mNextBTN:
//                mFactory.showFragment(FragmentTag.FRAGMENT_NEXT);
                getActivity().startActivity(new Intent(getActivity(), Main2Activity.class));
                break;
        }
    }
}
