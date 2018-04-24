package com.ata.promo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }
}
