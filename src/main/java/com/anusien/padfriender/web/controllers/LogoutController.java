package com.anusien.padfriender.web.controllers;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.annotation.*;
import javax.servlet.http.*;

@RequestMapping("/logout")
@Controller
public class LogoutController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView doGet(@Nonnull final HttpServletRequest request, @Nonnull final HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("login");
    }
}