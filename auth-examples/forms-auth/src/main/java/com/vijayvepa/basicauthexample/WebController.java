package com.vijayvepa.basicauthexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @GetMapping("/")
    public String index(){
        return "Welcome Home!";
    }
}
