package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsNotEmpty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by sswang on 2016/8/25.
 */
public class ParamsNotEmptyValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsNotEmpty, Object> {
    private ParamsNotEmpty annotation;

    @Override
    public void initialize(ParamsNotEmpty constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);
                if (fieldValue instanceof CharSequence){
                    if(StringUtils.isEmpty((CharSequence) fieldValue)){
                        return false;
                    }
                }else{
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
