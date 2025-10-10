package com.example.petdrop;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addPolymorphicSubtypes() {
        return builder -> builder.modulesToInstall(new SimpleModule() {
            @Override
            public void setupModule(SetupContext context) {
                context.registerSubtypes(
                    new NamedType(com.example.petdrop.model.DatabaseNotification.class, "database"),
                    new NamedType(com.example.petdrop.model.NotificationRequest.class, "request")
                );
            }
        });
    }
}
