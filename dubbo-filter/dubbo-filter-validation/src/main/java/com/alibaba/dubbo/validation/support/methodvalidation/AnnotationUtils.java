package com.alibaba.dubbo.validation.support.methodvalidation;

import com.alibaba.dubbo.validation.annotations.method.ParamsNotNull;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * utils for annotation
 *
 * Created by dsqin on 2016/11/22.
 */
public class AnnotationUtils {

    /**
     * 获取annotation某一个member的值
     * @param annotation 注解
     * @param member member
     * @return 值
     */
    public static Object getAnnotationMemberValue(Annotation annotation, String member) {

        if (annotation == null) {
            return null;
        }

        try {
            Method method = annotation.annotationType().getDeclaredMethod(member, new Class[0]);
            ReflectionUtils.makeAccessible(method);
            return method.invoke(annotation, new Object[0]);
        } catch (Exception var3) {
            return null;
        }
    }
}
