package com.pporzuczek.web.rooms.app.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

import com.pporzuczek.web.rooms.app.service.AccountService;

@Configuration
public class LoginAdapter implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    @Autowired
    private AccountService accountService;
    
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        accountService.updateLastLogin(event.getAuthentication().getName());
    }

}
