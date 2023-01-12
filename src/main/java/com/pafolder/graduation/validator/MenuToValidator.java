package com.pafolder.graduation.validator;

import com.pafolder.graduation.to.MenuTo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MenuToValidator implements Validator {
    public MenuToValidator() {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MenuTo.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        MenuTo menuTo = (MenuTo) object;
    }
}
