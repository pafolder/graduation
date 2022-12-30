package com.pafolder.graduation.validator;

import com.pafolder.graduation.model.Menu;
import com.pafolder.graduation.to.MenuTo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MenuToValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == MenuTo.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        MenuTo menuTo = (MenuTo) object;
        errors.rejectValue("", "Error code", "Error message");
    }
}
