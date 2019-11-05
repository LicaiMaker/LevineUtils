package com.levine.utils.base;

import com.github.promeg.pinyinhelper.Pinyin;

public class PinYinUtils {

    public static char getStringFirstChar(String s) {
        char firstChar = s.charAt(0);
        char temp = Pinyin.toPinyin(firstChar).charAt(0);
        if (Character.isDigit(temp)||!Character.isLetter(temp)) {
            return '#';
        }

        if (Character.isLowerCase(temp)) {
            temp = Character.toUpperCase(temp);
        }
        return temp;
    }
}
