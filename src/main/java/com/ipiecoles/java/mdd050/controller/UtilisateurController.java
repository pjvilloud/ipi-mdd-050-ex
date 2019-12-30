package com.ipiecoles.java.mdd050.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UtilisateurController {
//    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('USER')")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping("/me")
    public UserDetails getMe(Authentication authentication){
        return (UserDetails) authentication.getPrincipal();
    }
}
