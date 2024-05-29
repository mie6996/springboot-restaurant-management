package com.restaurant.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(method.getName());

        for (Object param : params) {
            keyBuilder.append("_").append(param.toString());
        }
        log.info("Generated key: {}", keyBuilder.toString());
        return keyBuilder.toString();
    }
}
