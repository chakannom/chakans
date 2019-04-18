package com.chakans.portal.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
    "com.chakans.account.security",
    "com.chakans.*.service",
    "com.chakans.*.web.rest"
})
public class ModuleConfiguration {

}
