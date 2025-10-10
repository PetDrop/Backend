package config;

import org.springframework.context.annotation.Bean;

import com.example.petdrop.model.DatabaseNotification;
import com.example.petdrop.model.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(
                DatabaseNotification.class,
                NotificationRequest.class);
        return mapper;
    }
}
