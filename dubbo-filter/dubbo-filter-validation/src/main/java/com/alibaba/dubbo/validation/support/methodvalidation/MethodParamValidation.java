package com.alibaba.dubbo.validation.support.methodvalidation;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.validation.Validation;
import com.alibaba.dubbo.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * validation for method param
 *
 * Created by dsqin on 2016/11/22.
 */
public class MethodParamValidation implements Validation {

    @Override
    public Validator getValidator(URL url) {
        return new MethodParamValidator(url);
    }
}
