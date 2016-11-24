package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 提供对验证帮助方法定义
 * Created by sswang on 2016/8/26.
 */
public abstract class ParamsAbstractValidator {
    /**
     * 对请求对象的属性进行缓存
     */
    private final static Logger logger = LoggerFactory.getLogger(ParamsAbstractValidator.class);

    protected Object getFieldValue(Class<?> clazz, String name, Object obj) throws NoSuchFieldException {

        try{
            PropertyDescriptor property = BeanUtils.getPropertyDescriptor(clazz, name);
            return property.getReadMethod().invoke(obj);
        }catch (BeansException e) {
            logger.error("field not exists. name:"+name, e);
        } catch (InvocationTargetException e) {
            logger.error("field value cant get. name:"+name, e);
        } catch (IllegalAccessException e) {
            logger.error("field cant access. name:"+name, e);
        }
        return null;
    }

    protected Class<?> getFieldType(Class<?> clazz, String name) {
        PropertyDescriptor property = BeanUtils.getPropertyDescriptor(clazz, name);
        return property.getPropertyType();
    }
}
