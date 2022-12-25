package com.pafolder.graduation.validator;

import com.pafolder.graduation.model.Menu;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MenuValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Menu.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        Menu menu = (Menu) object;
//        errors.rejectValue("", "Error code", "Error message");
    }
}
