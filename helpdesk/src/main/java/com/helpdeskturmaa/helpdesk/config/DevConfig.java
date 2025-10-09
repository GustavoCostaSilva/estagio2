package com.helpdeskturmaa.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.helpdeskturmaa.helpdesk.service.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

   
    @Bean
    public boolean instanciaDB() {
        this.dbService.instanciaDB();
        return true;
    }
}
