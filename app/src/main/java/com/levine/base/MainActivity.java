package com.levine.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SectionIndexer;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.levine.base.fragment.FragmentTag;
import com.levine.utils.app.fragment.FragmentFactory;
import com.levine.utils.base.LevineAnnotationUtils;
import com.levine.utils.base.LevineBindView;
import com.levine.utils.base.LogUtils;

public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    private FragmentFactory mFactory;
    @LevineBindView(R.id.mTabLayout)
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LevineAnnotationUtils.bind(this);
//        mTabLayout = findViewById(R.id.mTabLayout);
        mTabLayout.addOnTabSelectedListener(this);
        mFactory = FragmentFactory.getInstance().init(this, R.id.mContentFl);

        if (savedInstanceState != null) {
            LogUtils.e("onCreate savedInstanceState");
            mFactory.restoreCurrentFragmentInfo(savedInstanceState);
        } else {
            LogUtils.e("onCreate ");
            mFactory.showFragment(FragmentTag.FRAGMENT1);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        LogUtils.e("onTabSelected:" + tab.getPosition());
        switch (tab.getPosition()) {
            case 0:
                mFactory.showFragment(FragmentTag.FRAGMENT1);
                break;
            case 1:
                mFactory.showFragment(FragmentTag.FRAGMENT2);
                break;
            case 2:
                mFactory.showFragment(FragmentTag.FRAGMENT3);
                break;
            case 3:
                mFactory.showFragment(FragmentTag.FRAGMENT4);
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

        LogUtils.e("onSaveInstanceState");
        //保存我的tab选择状态
        int currentTab = mTabLayout.getSelectedTabPosition();
        outState.putInt("currentTab", currentTab);
        mFactory.saveCurrentFragmentInfo(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LogUtils.e("onRestoreInstanceState");
        //回复我的tab选择状态
        int currentTab = savedInstanceState.getInt("currentTab");
        mTabLayout.getTabAt(currentTab).select();
        if (savedInstanceState != null) {
            mFactory.restoreCurrentFragmentInfo(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFactory.onResume(this, R.id.mContentFl);
    }




    @Override
    public void onBackPressed() {
        backOperation1();

    }

    public void backOperation1() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }

    private long lastPressTime = 0l;
    private int timeExpired = 1500;

    public void backOperation2() {
        if (System.currentTimeMillis() - lastPressTime > timeExpired) {
            Toast.makeText(MainActivity.this, "再按一次退出Ucall", Toast.LENGTH_SHORT).show();
            lastPressTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
