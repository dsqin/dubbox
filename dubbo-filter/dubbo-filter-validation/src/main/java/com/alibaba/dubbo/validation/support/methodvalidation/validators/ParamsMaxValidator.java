package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsMax;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by sswang on 2016/8/25.
 */
public class ParamsMaxValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsMax, Object> {
    private ParamsMax annotation;
    private long maxValue;
    private BigDecimal maxValueBD;

    @Override
    public void initialize(ParamsMax constraintAnnotation) {
        this.annotation = constraintAnnotation;
        this.maxValue = constraintAnnotation.value();
        this.maxValueBD = BigDecimal.valueOf(maxValue);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);
                if (fieldValue != null){
                    if (fieldValue instanceof Number){
                        if (!isNumberValid((Number) fieldValue, context)){
                            return false;
                        }
                    }else if (fieldValue instanceof CharSequence){
                        if (!isCharSequenceValid((CharSequence) fieldValue, context)){
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

    public boolean isNumberValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        // null values are valid
        if ( value == null ) {
            return true;
        }

        // handling of NaN, positive infinity and negative infinity
        else if ( value instanceof Double) {
            if ( (Double) value == Double.NEGATIVE_INFINITY ) {
                return true;
            }
            else if ( Double.isNaN( (Double) value ) || (Double) value == Double.POSITIVE_INFINITY ) {
                return false;
            }
        }
        else if ( value instanceof Float) {
            if ( (Float) value == Float.NEGATIVE_INFINITY ) {
                return true;
            }
            else if ( Float.isNaN( (Float) value ) || (Float) value == Float.POSITIVE_INFINITY ) {
                return false;
            }
        }
        if ( value instanceof BigDecimal) {
            return ( (BigDecimal) value ).compareTo( BigDecimal.valueOf( maxValue ) ) != 1;
        }
        else if ( value instanceof BigInteger) {
            return ( (BigInteger) value ).compareTo( BigInteger.valueOf( maxValue ) ) != 1;
        }
        else {
            long longValue = value.longValue();
            return longValue <= maxValue;
        }
    }

    public boolean isCharSequenceValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        //null values are valid
        if ( value == null ) {
            return true;
        }
        try {
            return new BigDecimal( value.toString() ).compareTo( maxValueBD ) != 1;
        }
        catch ( NumberFormatException nfe ) {
            return false;
        }
    }
}
