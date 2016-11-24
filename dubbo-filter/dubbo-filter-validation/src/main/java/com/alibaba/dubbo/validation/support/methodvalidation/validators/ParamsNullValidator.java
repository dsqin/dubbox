package com.alibaba.dubbo.validation.support.methodvalidation.validators;


import com.alibaba.dubbo.validation.annotations.method.ParamsNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * TODO 暂不实现
 * Created by sswang on 2016/8/25.
 */
public class ParamsNullValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsNull, Object> {
    private ParamsNull annotation;

    @Override
    public void initialize(ParamsNull constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}
