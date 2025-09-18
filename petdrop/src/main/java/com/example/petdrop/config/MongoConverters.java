package com.example.petdrop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class MongoConverters {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
            new Converter<ZonedDateTime, Date>() {
                @Override
                public Date convert(ZonedDateTime source) {
                    return Date.from(source.toInstant());
                }
            },
            new Converter<Date, ZonedDateTime>() {
                @Override
                public ZonedDateTime convert(Date source) {
                    return source.toInstant().atZone(ZoneId.systemDefault());
                }
            }
        ));
    }
}