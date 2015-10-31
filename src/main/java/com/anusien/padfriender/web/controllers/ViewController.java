package com.anusien.padfriender.web.controllers;

import com.anusien.padfriender.model.monster.UserMonster;
import com.anusien.padfriender.persistence.user.UserMonsterDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RequestMapping(value = "/view")
@Controller
public class ViewController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Nonnull private final UserMonsterDAO userMonsterLookup;

    @Autowired
    public ViewController(@Nonnull UserMonsterDAO userMonsterLookup) {
        this.userMonsterLookup = userMonsterLookup;
    }

    @RequestMapping(value="", method= RequestMethod.GET)
    public ModelAndView doGet(final Principal user) {
        final String email = user.getName();
        final Map<String, Set<UserMonster>> friendMonsters = userMonsterLookup.getGroupedFriendMonsters(email);

        return new ModelAndView("view");

    }
}
