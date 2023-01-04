package com.pafolder.graduation.validator;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetails;
import com.pafolder.graduation.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserToValidator implements Validator {
    private static String DUPLICATING_EMAIL = "Email is already in use";
    private Logger log = LoggerFactory.getLogger(UserToValidator.class);
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    public UserToValidator(UserRepository userRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserTo.class;
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserTo userTo = (UserTo) object;
        Optional<User> dbUser = userRepository.findByEmail(userTo.getEmail());
        if (!dbUser.isPresent()) return;
        int dbId = dbUser.get().getId();
        if (request.getMethod().equals("PUT")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int authUserId = ((UserDetails) authentication.getPrincipal()).getUser().getId();
            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/" + dbId) || (dbId == authUserId && requestURI.contains("/profile")))
                return;
        }
        errors.reject("", DUPLICATING_EMAIL);
    }
}
