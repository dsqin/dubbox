package com.alibaba.dubbo.validation.support.methodvalidation;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.validation.Validator;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * validator for method param
 *
 * Created by dsqin on 2016/11/22.
 */
public class MethodParamValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(MethodParamValidator.class);

    private static final String ANNOTATION_MEMBER_MESSAGE = "message";

    private final Class<?> clazz;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MethodParamValidator(URL url) {
        this.clazz = ReflectUtils.forName(url.getServiceInterface());
    }

    @Override
    public void validate(String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Exception {

        // 1.参数判断
        if (ArrayUtils.isEmpty(parameterTypes) || ArrayUtils.isEmpty(arguments)) {
            return;
        }

        // 2.获取方法annotation
        Method method = clazz.getMethod(methodName, parameterTypes);
        Annotation[] annotations = method.getAnnotations();

        if (ArrayUtils.isEmpty(annotations)) {
            return;
        }

        // 3.循环annotation进行参数验证
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();

            if (!annotationType.isAnnotationPresent(Constraint.class)) {
                continue;
            }

            Constraint constraint = annotationType.getAnnotation(Constraint.class);

            Class<? extends ConstraintValidator<?, ?>>[] validatorClzs = constraint.validatedBy();

            if (ArrayUtils.isEmpty(validatorClzs)) {
                continue;
            }

            Class<? extends ConstraintValidator<?, ?>> validatorClz = validatorClzs[0];

            ConstraintValidator validator = null;
            try {
                validator = validatorClz.newInstance();

            } catch (InstantiationException ex) {

            } catch (IllegalAccessException ex) {

            }
            if (null == validator) {
                continue;
            }

            Object message = AnnotationUtils.getAnnotationMemberValue(annotation, ANNOTATION_MEMBER_MESSAGE);

            if (null != validator) {
                validator.initialize(annotation);
                if (!validator.isValid(arguments[0], null)) {
                    throw new RpcException(RpcException.INVALID_PARAM, null == message ? "" : message.toString());
                }
            }
        }
    }

}
