package com.alibaba.dubbo.validation.support.methodvalidation.validators;


import com.alibaba.dubbo.validation.annotations.method.ParamsNotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ParamsNotNullValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsNotNull, Object> {

    private static final Logger logger = LoggerFactory.getLogger(ParamsNotNullValidator.class);

    private ParamsNotNull annotation;

    @Override
    public void initialize(ParamsNotNull constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        logger.info(value.toString());

        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);

                if (fieldValue == null || "".equals(fieldValue.toString())){
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
