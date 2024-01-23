package com.maup.network.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class UserUtils {
    public static String currentUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  ((UserDetails) auth.getPrincipal()).getUsername();
    }
}
