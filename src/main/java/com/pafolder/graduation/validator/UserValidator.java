package com.pafolder.graduation.validator;

import com.pafolder.graduation.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        LoggerFactory.getLogger("yellow").error("User validation");
    }
}
