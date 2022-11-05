package com.vijayvepa.basicauthexample;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

    @GetMapping("/")
    public String index(){
        return "You made it!";
    }


    @GetMapping("/attributes")
    public String attributes(
            @AuthenticationPrincipal OidcUser oidcUser){
        return oidcUser.getAttributes().toString();
    }

    @GetMapping("/authorities")
    public String authorities(
            @AuthenticationPrincipal OidcUser oidcUser){
        return oidcUser.getAuthorities().toString();
    }
}
