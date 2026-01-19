package com.michaelverdon.pokemonteambuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(){
        System.out.println("Loading index./html");
        return "index.html";
    }
}
