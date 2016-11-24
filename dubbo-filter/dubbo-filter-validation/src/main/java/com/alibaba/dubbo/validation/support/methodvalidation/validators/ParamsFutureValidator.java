package com.alibaba.dubbo.validation.support.methodvalidation.validators;


import com.alibaba.dubbo.validation.annotations.method.ParamsFuture;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

/**
 * Calendar Date验证
 * Created by sswang on 2016/8/25.
 */
public class ParamsFutureValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsFuture, Object> {
    private ParamsFuture annotation;

    @Override
    public void initialize(ParamsFuture constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        long currentTime = System.currentTimeMillis();
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);
                if (fieldValue != null){
                    if (fieldValue instanceof Date){
                        if(((Date) fieldValue).getTime() <= currentTime){
                            return false;
                        }
                    }else if (fieldValue instanceof Calendar){
                        if (((Calendar) fieldValue).getTimeInMillis() <= currentTime){
                            return false;
                        }
                    }
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
