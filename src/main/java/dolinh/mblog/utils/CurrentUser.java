package dolinh.mblog.utils;

import dolinh.mblog.security.CustomUserDetails;
import dolinh.mblog.user.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CurrentUser {
    public AppUser getUser(){
        CustomUserDetails userDetails =
                (CustomUserDetails) Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getPrincipal();
        return userDetails.user();
    }
}
