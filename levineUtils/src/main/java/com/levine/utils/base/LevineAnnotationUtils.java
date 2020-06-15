package com.levine.utils.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Field;


public class LevineAnnotationUtils {



    public static void bind(Object currentClass,View sourceView){
        Context context=null;
        if(currentClass instanceof Activity){
            context=((Activity) currentClass).getApplicationContext();
        }else if(currentClass instanceof androidx.fragment.app.Fragment) {
            context= ((androidx.fragment.app.Fragment)currentClass).getActivity().getApplicationContext();
        }
        Class targetClass = currentClass.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            LevineBindView bindView = field.getAnnotation(LevineBindView.class);

            LevineOnClick click = field.getAnnotation(LevineOnClick.class);
            if(bindView!=null){
                try{
                    if(bindView.strValue()!=null&&bindView.strValue()!=""){
                        int value = context.getResources().getIdentifier(bindView.strValue(),"id",context.getPackageName());
                        field.set(currentClass, sourceView.findViewById(value));
                        if(click!=null)
                            sourceView.findViewById(value).setOnClickListener((View.OnClickListener) currentClass);
                    }else if(bindView.value()!=-1){
                        field.set(currentClass, sourceView.findViewById(bindView.value()));
                        if(click!=null)
                            sourceView.findViewById(bindView.value()).setOnClickListener((View.OnClickListener) currentClass);
                    }else {
                        throw new AnnotationFormatError("参数格式不正确，请使用value或者strValue");
                    }
                } catch(IllegalAccessException ex){
                    ex.printStackTrace();
                }
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

