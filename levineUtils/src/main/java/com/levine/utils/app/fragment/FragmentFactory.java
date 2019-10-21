package com.levine.utils.app.fragment;


import android.os.Bundle;
import android.text.TextUtils;

import com.levine.utils.base.ClassUtils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentFactory {

    private static FragmentFactory mFactory;
    private HashMap<String, Fragment> mHashMap = new HashMap<>();
    private int containerViewId = -1;
    private ArrayList<Class> fragmentClasses;
    private FragmentActivity activity;
    private String mCurrentTag = null;
    private boolean isCallingRestoreStateMethod = false;
    FragmentManager fragmentManager = null;

    private FragmentFactory() {

    }

    public static FragmentFactory getInstance() {
        if (mFactory == null) {
            synchronized (FragmentFactory.class) {
                if (mFactory == null) {
                    mFactory = new FragmentFactory();
                }
            }
        }
        return mFactory;
    }


    /**
     * @param activity        需要FragmentActivity或者AppCompatActivity或者两者的子类
     * @param containerViewId 展示fragment的容器的id
     * @return Fragment object
     */
    public FragmentFactory init(FragmentActivity activity, int containerViewId) {

        this.fragmentClasses = ClassUtils.getClassesByAnnotation(TargetFragmentTag.class, activity.getPackageCodePath(), activity.getPackageName());
        this.activity = activity;
        this.fragmentManager =activity.getSupportFragmentManager();
        this.containerViewId = containerViewId;
        return this;
    }


    /**
     * 此方法适用于判断str是否是一个FragmentTag接口中的元素
     *
     * @param tag fragment的tag
     * @return 返回TargetFragment中value=tag的Fragment.Class
     */
    private Class isFragmentTag(String tag) {
        for (Class c : fragmentClasses) {
            TargetFragmentTag annotation = (TargetFragmentTag) c.getAnnotation(TargetFragmentTag.class);
            String fragmentTag = annotation.value();
            if (tag.equals(fragmentTag)) {
                return c;
            }
        }
        return null;
    }

    private Fragment getFragmentByTag(String tag) {
        if (!mHashMap.containsKey(tag)) {
            try {
                Class c = isFragmentTag(tag);
                if (c != null) {
                    mHashMap.put(tag, (Fragment) c.newInstance());
                }

            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        return mHashMap.get(tag);
    }


    /**
     * 这是很好用的fragment重用技术
     *
     * @param tag 表示将要创建的fragment的tag
     * @return 返回最新展示的fragment的tag
     */
    public String showFragment(String tag) {
        //根据tag获取fragment对象
        Fragment baseFragment = getFragmentByTag(tag);
        if (baseFragment == null) {
            throw new IllegalAccessError("tag is error!");
        }
        //如果选择的是本页面
        if (tag.equals(mCurrentTag) && !isCallingRestoreStateMethod) {
            return mCurrentTag;
        } else {
            //如果准备切换页面(fragment)
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!TextUtils.isEmpty(mCurrentTag)&& !isCallingRestoreStateMethod) {
                //已经有fragment被初始化了(因为mCurrentTag初始值为null)，则隐藏当前的fragment
                transaction.hide(getFragmentByTag(mCurrentTag));
            }
            if (!baseFragment.isAdded() && fragmentManager.findFragmentByTag(tag)==null) {
                //如果这个fragment还没有被添加到容器里，说明是第一次加载,没有使用replace方法(fragment会被销毁)，则可以重用fragment
                transaction.add(this.containerViewId, baseFragment, tag);
            } else {
                //显示fragment
                transaction.show(baseFragment);
            }
            transaction.commitAllowingStateLoss();
        }
        mCurrentTag = tag;
        return mCurrentTag;
    }

    public Bundle saveCurrentFragmentInfo(Bundle bundle) {
        if (bundle != null){
            bundle.putString("currentTag", mCurrentTag);
            isCallingRestoreStateMethod = true;
        }
        return bundle;
    }

    public void restoreCurrentFragmentInfo(Bundle bundle) {
        if (bundle != null) {
            String tag = bundle.getString("currentTag");
            showFragment(tag);
            isCallingRestoreStateMethod = false;
        }
    }


}
