package com.anusien.padfriender.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "")
@Controller
public class IndexController {

    @RequestMapping(value={"", "/welcome"}, method= RequestMethod.GET)
    public ModelAndView doGet() {
        return new ModelAndView("index");
    }
}
