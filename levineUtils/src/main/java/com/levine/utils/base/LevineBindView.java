package com.levine.utils.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.Nullable;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LevineBindView {

    /**
     * int name of widget,e.g,R.id.mPhoneBtn,used in both Android Application and Android Library.
     * @return
     */
    int value() default -1;

    /**
     * string name of  widget,e.g,R.id.mPhoneBtn,"mPhoneBtn" is needed .Usually，it is used in a Android Library.
     * @return
     */
    String strValue() default "";
}
