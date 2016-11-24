package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsDigits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Created by sswang on 2016/8/25.
 */
public class ParamsDigitsValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsDigits, Object> {
    private ParamsDigits annotation;
    private int maxIntegerLength;
    private int maxFractionLength;

    @Override
    public void initialize(ParamsDigits constraintAnnotation) {
        this.annotation = constraintAnnotation;
        this.maxIntegerLength = constraintAnnotation.integer();
        this.maxFractionLength = constraintAnnotation.fraction();
        validateParameters();
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
                    BigDecimal bigNum = getBigDecimalValue(fieldValue);
                    if ( bigNum == null ) {
                        return false;
                    }

                    int integerPartLength = bigNum.precision() - bigNum.scale();
                    int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();

                    if ( maxIntegerLength < integerPartLength || maxFractionLength < fractionPartLength ){
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private BigDecimal getBigDecimalValue(Object value) {
        BigDecimal bd;
        try {
            bd = new BigDecimal( value.toString() );
        }
        catch ( NumberFormatException nfe ) {
            return null;
        }
        return bd;
    }

    private void validateParameters() {
        if ( maxIntegerLength < 0 ) {
            throw new RuntimeException("maxIntegerLength less then 0");
        }
        if ( maxFractionLength < 0 ) {
            throw new RuntimeException("maxFractionLength less then 0");
        }
    }
}
