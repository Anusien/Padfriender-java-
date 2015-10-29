package com.anusien.padfriender.config;

import org.springframework.beans.factory.annotation.Value;

public class AppConfig {

    @Value("${database.server}")
    String databaseServer;

    @Value("${database.username}")
    String databaseUsername;

    @Value("${database.password}")
    String databasePassword;
}
