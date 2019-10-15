package com.levine.utils;

import android.app.Activity;
import android.app.FragmentManager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_class_type(){
        Activity activity=new AppCompatActivity();
//        AppCompatActivity appCompatActivity=new AppCompatActivity();
        System.out.println(activity instanceof FragmentActivity);
    }
    class Activity{}

    class AppCompatActivity extends FragmentActivity{

    }
    class FragmentActivity extends Activity{

    }


}