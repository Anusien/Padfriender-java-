package com.anusien.padfriender.web.controllers;

import com.anusien.padfriender.model.monster.*;
import com.anusien.padfriender.model.user.*;
import com.anusien.padfriender.persistence.monster.*;
import com.anusien.padfriender.persistence.user.UserMonsterDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import java.security.Principal;
import java.util.*;

@RequestMapping(value = "/view")
@Controller
public class ViewController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Nonnull private final UserMonsterDao userMonsterDao;
    @Nonnull private final MonsterListProvider monsterListProvider;

    @Autowired
    public ViewController(@Nonnull final UserMonsterDao userMonsterDao,
                          @Nonnull final MonsterListProvider monsterListProvider) {
        this.userMonsterDao = userMonsterDao;
        this.monsterListProvider = monsterListProvider;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public ModelAndView doGet(@Nonnull final Principal user) {
        final String email = user.getName();
        final Map<String, Set<UserMonster>> friendMonsters = userMonsterDao.getGroupedFriendMonsters(email);

        return new ModelAndView("view");
    }

    @RequestMapping(value="addFriend", method = RequestMethod.PUT)
    public ModelAndView addMonster(@RequestParam("userid") final String userId,
                              @RequestParam("monsterid") final int monsterId,
                              @RequestParam(value = "awakenings", defaultValue = "0") final int awakenings,
                              @RequestParam(value = "plusHp", defaultValue = "0") final int plusHp,
                              @RequestParam(value = "plusAtk", defaultValue = "0") final int plusAtk,
                              @RequestParam(value = "plusRcv", defaultValue = "0") final int plusRcv) {
        final StringBuilder error = new StringBuilder();

        final UserId owner = UserId.getUserIdFromString(userId);
        final Monster monster = monsterListProvider.getMonsterById(monsterId);

        if(owner == null) {
            error.append("Could not parse friendcode ").append(userId).append(System.lineSeparator());
        } if(monster == null) {
            error.append("Could not find monster ").append(monsterId).append(System.lineSeparator());
        }

        if(error.length() > 0) {
            final Map<String, Object> model = new HashMap<>();
            model.put("error", error.toString());
            return new ModelAndView("view", model);
        }
        @SuppressWarnings("ConstantConditions")
        final UserMonster fromDatabase = userMonsterDao.getMonsterByUserAndId(owner, monster.getId());

        final UserMonster fromUserInput = new UserMonster(monster, owner, awakenings, plusHp, plusAtk, plusRcv);

        final UserMonster merged = UserMonster.merge(fromDatabase, fromUserInput, false);
        userMonsterDao.saveMonster(merged);

        // TODO: Friend the user if we didn't do that already

        return new ModelAndView("view");
    }
}
