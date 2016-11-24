package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsAssertFalse;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * TODO 暂不实现
 * Created by sswang on 2016/8/25.
 */
public class ParamsAssertFalseValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsAssertFalse, Object> {
    private ParamsAssertFalse annotation;
    @Override
    public void initialize(ParamsAssertFalse paramsAssertFalse) {
        this.annotation = paramsAssertFalse;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
