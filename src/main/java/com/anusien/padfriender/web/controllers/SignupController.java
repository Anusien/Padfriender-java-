package com.anusien.padfriender.web.controllers;

import com.anusien.padfriender.model.user.UserId;
import com.anusien.padfriender.persistence.user.UserDao;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/signup")
@Controller
public class SignupController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    private final UserDao userDao;

    @Autowired
    public SignupController(@Nonnull final UserDao userDao) {
        this.userDao = Preconditions.checkNotNull(userDao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView signup(@Nonnull final HttpServletRequest request) {
        final String email = request.getParameter("email");
        final String id = request.getParameter("id");
        final String name = request.getParameter("name");
        final String password = request.getParameter("password");
        final String confirm = request.getParameter("password_confirm");

        final StringBuilder error = new StringBuilder();

        if(email == null) {
            error.append("Invalid e-mail address");
        }

        if(id == null || id.length() < 9) {
            error.append("Invalid id");
        }

        if(name == null) {
            error.append("Invalid in-game name");
        }

        if(password == null || confirm == null || !password.equals(confirm)) {
            error.append("Password and Password Confirmation don't match.").append(System.lineSeparator());
        }

        if(error.length() > 0) {
            final Map<String, Object> model = new HashMap<>();
            model.put("errors", error.toString());
            return new ModelAndView("signup", model);
        }

        final UserId userId = UserId.getUserIdFromString(id);

        try {
            userDao.createUser(email, name, userId, password);
        } catch (IOException e) {
            LOGGER.error("Tried to create user", e);
            return new ModelAndView("signup");
        }

        try {
            request.login(email, password);
        } catch (ServletException e) {
            LOGGER.error("Tried to log in after creating new user", e);
            return new ModelAndView("signup");
        }
        return new ModelAndView("index");
    }
}
