package com.chakans.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chakans.core.web.rest.errors.ExceptionTranslator;

@Configuration
public class ExceptionConfiguration {

    @Bean
    public ExceptionTranslator exceptionTranslator() {
        return new ExceptionTranslator();
    }
}