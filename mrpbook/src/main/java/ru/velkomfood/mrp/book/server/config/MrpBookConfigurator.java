package ru.velkomfood.mrp.book.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MrpBookConfigurator {

    @Bean
    public ParametersHolder parametersHolder() {
        return new ParametersHolder();
    }

    @Bean
    public MrpLoggerFactory mrpLoggerFactory() {
        return new MrpLoggerFactory();
    }

}
