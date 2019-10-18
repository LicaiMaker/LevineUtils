package com.levine.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.levine.base.fragment.FragmentTag;
import com.levine.utils.app.fragment.FragmentFactory;

public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {
    private FragmentFactory mFactory;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.mTabLayout);
        mTabLayout.addOnTabSelectedListener(this);
        mFactory = FragmentFactory.getInstance().init(this, R.id.mContentFl);

        if (savedInstanceState != null) {
            mFactory.restoreCurrentFragmentInfo(savedInstanceState);
        } else {

            mFactory.showFragment(FragmentTag.FRAGMENT1);
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mFactory.showFragment(FragmentTag.FRAGMENT1);
                break;
            case 1:
                mFactory.showFragment(FragmentTag.FRAGMENT2);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //保存我的tab选择状态
        int currentTab = mTabLayout.getSelectedTabPosition();
        outState.putInt("currentTab", currentTab);
        mFactory.saveCurrentFragmentInfo(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //回复我的tab选择状态
        int currentTab = savedInstanceState.getInt("currentTab");
        mTabLayout.getTabAt(currentTab).select();
    }
}
