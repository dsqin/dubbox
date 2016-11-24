package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsListNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 */
public class ParamsListNotNullValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsListNotNull, Object> {
    private ParamsListNotNull annotation;

    @Override
    public void initialize(ParamsListNotNull constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        int notNullCount = 0;
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);
                if (fieldValue != null){
                    notNullCount++;
                }
            }catch (Exception e){
                return false;
            }
        }
        return notNullCount > 0;
    }
}
