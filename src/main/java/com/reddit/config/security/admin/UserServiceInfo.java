package com.reddit.config.security.admin;

import com.reddit.exception.UnAuthorizeException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceInfo {
    public UserDetails authenticatedUserInfo(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null){
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null){
                return (UserDetails) authentication.getPrincipal();
            }
        }
        return null;
    }

    public UserInfo authenticateUser(){
        UserDetails userDetails = this.authenticatedUserInfo();
        if (userDetails instanceof UserInfo){
            return (UserInfo) userDetails;
        }
        throw new UnAuthorizeException("Authenticated user info not found");
    }



}
