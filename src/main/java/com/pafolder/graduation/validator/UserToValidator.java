package com.pafolder.graduation.validator;

import com.pafolder.graduation.model.User;
import com.pafolder.graduation.repository.UserRepository;
import com.pafolder.graduation.security.UserDetailsImpl;
import com.pafolder.graduation.to.UserTo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserToValidator implements Validator {
    static final String DUPLICATING_EMAIL = "Email is already used by another user";
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserTo userTo = (UserTo) object;
        Optional<User> dbUser = userRepository.findByEmail(userTo.getEmail());
        if (dbUser.isEmpty()) return;
        int dbId = dbUser.get().getId();
        if (request.getMethod().equals("PUT")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            int authUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId();
            String requestURI = request.getRequestURI();
            if (requestURI.endsWith("/" + dbId) || (dbId == authUserId && requestURI.contains("/profile")))
                return;
        }
        errors.reject("", DUPLICATING_EMAIL);
    }
}
