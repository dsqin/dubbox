package com.alibaba.dubbo.validation.support.methodvalidation.validators;

import com.alibaba.dubbo.validation.annotations.method.ParamsPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamsPatternValidator extends ParamsAbstractValidator implements ConstraintValidator<ParamsPattern, Object> {
    private ParamsPattern annotation;
    private Pattern pattern;

    @Override
    public void initialize(ParamsPattern constraintAnnotation) {
        this.annotation = constraintAnnotation;
        ParamsPattern.Flag[] flags = constraintAnnotation.flags();
        int intFlag = 0;
        for ( ParamsPattern.Flag flag : flags ) {
            intFlag = intFlag | flag.getValue();
        }
        pattern = Pattern.compile( constraintAnnotation.regexp(), intFlag );

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Class valueClass = value.getClass();
        for (String fieldName : annotation.field()) {
            try{
                Object fieldValue = getFieldValue(valueClass, fieldName, value);
                if (fieldValue != null){
                    if (fieldValue instanceof CharSequence) {
                        Matcher m = pattern.matcher((CharSequence) fieldValue);
                        if (!m.matches()){
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
