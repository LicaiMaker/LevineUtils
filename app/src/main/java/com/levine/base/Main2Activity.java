package com.levine.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import com.levine.base.fragment.Fragment5;
import com.levine.base.fragment.FragmentTag;
import com.levine.utils.app.fragment.FragmentFactory;

public class Main2Activity extends AppCompatActivity implements Fragment5.OnFragmentInteractionListener {

    FragmentFactory mFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mFactory = FragmentFactory.getInstance().init(this, R.id.content2FL);
        if (savedInstanceState == null) {
            mFactory.showFragment(FragmentTag.FRAGMENT5);
        } else {
            mFactory.restoreCurrentFragmentInfo(savedInstanceState);
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
        mFactory.saveCurrentFragmentInfo(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mFactory.restoreCurrentFragmentInfo(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFactory.onResume(this, R.id.content2FL);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
