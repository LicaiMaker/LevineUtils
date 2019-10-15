package com.levine.utils.base;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 查找指定路径下面实现指定接口的全部类
 */
public class ClassUtils {

    /**
     * @param clazz       接口
     * @param packageName 实现类所在的包的包名
     */

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ArrayList<Class> getAllClassByInterface(Class clazz, String packagePath, String packageName) {
        ArrayList<Class> list = new ArrayList<>();

        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                ArrayList<Class> allClass = getAllClass(packagePath, packageName);
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                for (int i = 0; i < allClass.size(); i++) {
                    /**
                     * 判断是不是同一个接口
                     */
                    if (clazz.isAssignableFrom(allClass.get(i))) {
                        if (!clazz.equals(allClass.get(i))) {
                            // 自身并不加进去
                            list.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常:" + e.getMessage());
            }
        }
        return list;
    }

    /**
     * 从一个指定路径下查找所有的类
     *
     * @param packageName
     * @param packagePath
     */
    private static ArrayList<Class> getAllClass(String packagePath, String packageName) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            DexFile df = new DexFile(packagePath);//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();
            while (enumeration.hasMoreElements()) {
                String className = enumeration.nextElement();
                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    Class clazz = Class.forName(className);
                    list.add(clazz);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param clazz       注解.class
     * @param packagePath
     * @param packageName
     * @return
     */
    public static ArrayList<Class> getClassesByAnnotation(Class clazz, String packagePath, String packageName) {
        ArrayList<Class> list = new ArrayList<>();

        // 判断是否是一个注解
        if (clazz.isAnnotation()) {
            try {
                ArrayList<Class> allClass = getAllClass(packagePath, packageName);
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                for (int i = 0; i < allClass.size(); i++) {
                    /**
                     * 判断是否拥有注解clazz
                     */
                        if (hasAnnotation(allClass.get(i),clazz)) {
                            if (!clazz.equals(allClass.get(i))) {
                                // 自身并不加进去
                                list.add(allClass.get(i));
                            }
                        }
                }
            } catch (Exception e) {
                System.out.println("出现异常:" + e.getMessage());
            }
        }
        return list;
    }

    public static boolean hasAnnotation(AnnotatedElement element, Class annotationType) {
        if (element.isAnnotationPresent(annotationType)) {
            return true;
        }
        return false;
    }

    /**
     * 此方法是根据传入的类或者接口的Class文件，获取里面的所有属性
     * @param fragmentTagClass 传入一个接口或者类Class
     * @return
     */
    public  List<String> getTagsFromFragmentTagClass(Class fragmentTagClass) {
        List<String> list=new ArrayList<String>();
        Class a=fragmentTagClass;
        Field[] fields = a.getFields();
        for(Field field :fields){
            list.add(field.getName());
        }
        return list;
    }
}

