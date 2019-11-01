package com.levine.utils.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;


public class LevineAnnotationUtils {



    public static void bind(Object currentClass,View sourceView){
        Class targetClass = currentClass.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            LevineBindView bindView = field.getAnnotation(LevineBindView.class);
            LevineOnClick click = field.getAnnotation(LevineOnClick.class);
            if(bindView!=null){
                try{
                    field.set(currentClass,sourceView.findViewById(bindView.value()));
                } catch(IllegalAccessException ex){
                    ex.printStackTrace();
                }
            }
            if(bindView!=null&&click!=null){
                sourceView.findViewById(bindView.value()).setOnClickListener((View.OnClickListener) currentClass);
            }
        }
    }

    public static void bind(Activity aty) {
        bind(aty, aty.getWindow().getDecorView());
    }

    public static void bind(View view) {
        Context cxt = view.getContext();
        if(cxt instanceof Activity) {
            bind((Activity)cxt);
        } else {
            Log.d("LevineAnnotateUtil.java", "the view don\'t have root view");
        }
    }

    @TargetApi(11)
    public static void bind(Fragment frag) {
        bind(frag, frag.getActivity().getWindow().getDecorView());
    }

}
