package com.alibaba.dubbo.validation.support.methodvalidation.validators;


import com.alibaba.dubbo.validation.annotations.method.ParamsSize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Created by sswang on 2016/8/25.
 */
public class ParamsSizeValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsSize, Object> {
    private ParamsSize annotation;
    private int min;
    private int max;

    @Override
    public void initialize(ParamsSize constraintAnnotation) {
        this.annotation = constraintAnnotation;
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();

        validateParameters();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);

                if (fieldValue != null){
                    Class fieldClass = getFieldType(valueClass, fieldName);
                    int length = 0;
                    if (fieldClass.isArray()){
                        length = Array.getLength( fieldValue );
                    }else if (fieldValue instanceof Collection<?>){
                        length = ((Collection) fieldValue).size();
                    }else if(fieldValue instanceof Map<?,?>){
                        length = ((Map) fieldValue).size();
                    }else if (fieldValue instanceof CharSequence){
                        length = ((CharSequence) fieldValue).length();
                    }
                    if(length < min || length > max){
                        return false;
                    }
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

    private void validateParameters() {
        if ( min < 0 ) {
            throw new RuntimeException("min less than 0");
        }
        if ( max < 0 ) {
            throw new RuntimeException("max less than 0");
        }
        if ( max < min ) {
            throw new RuntimeException("max less than min");
        }
    }
}
