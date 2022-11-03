package com.vijayvepa.basicauthexample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebController {

    @GetMapping("/")
    @ResponseBody
    public String index(){
        return "You made it!";
    }

    @GetMapping("/login.html")
    public String login(){
        return "login.html";
    }

    @GetMapping("/login-error.html")
    public String login(Model model){
        model.addAttribute("loginError", true);
        return "login.html";
    }
}
