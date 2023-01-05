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
        if (menuTo.getRestaurantId() == null &&
                (menuTo.getRestaurantName() == null || menuTo.getRestaurantAddress() == null)) {
            errors.rejectValue("restaurantId", "",
                    "Either restaurantId must be nonnull or (restaurantName and restaurantAddress) should be specified");
        }
        if (menuTo.getRestaurantId() != null &&
                (menuTo.getRestaurantName() != null || menuTo.getRestaurantAddress() != null)) {
            errors.rejectValue("restaurantId", "",
                    "restaurantId and (restaurantName or restaurantAddress) can't be nonnull");
        }
    }
}
