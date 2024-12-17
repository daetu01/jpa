package com.example.jpa.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/index"})
    public String home() {
        System.out.println(1);
        return "main/index";
    }
}
