package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsDecimalMin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Created by sswang on 2016/8/25.
 */
public class ParamsDecimalMinValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsDecimalMin, Object> {
    private ParamsDecimalMin annotation;
    private BigDecimal minValue;
    private boolean inclusive;

    @Override
    public void initialize(ParamsDecimalMin constraintAnnotation) {
        this.annotation = constraintAnnotation;
        this.inclusive = constraintAnnotation.inclusive();
        this.minValue = new BigDecimal( constraintAnnotation.value() );
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try {
                Object fieldValue = getFieldValue(valueClass, fieldName, value);
                if (fieldValue != null) {
                    int comparisonResult = new BigDecimal(fieldValue.toString()).compareTo(minValue);
                    if (inclusive ? comparisonResult < 0 : comparisonResult <= 0) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
