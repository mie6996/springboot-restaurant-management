package com.restaurant.config;

import com.restaurant.constant.Constants;
import com.restaurant.util.CustomCacheManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Data
@Configuration
public class RedisConfig {
  @Value("${redis.host}")
  private String redisHost;

  @Value("${redis.port}")
  private int redisPort;

  @Value("${redis.password}")
  private String redisPassword;

  @Value("${redis.ttl-menus}")
  private long ttlMenus;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(redisHost);
    redisStandaloneConfiguration.setPort(redisPort);
    redisStandaloneConfiguration.setUsername("default");
    redisStandaloneConfiguration.setPassword(redisPassword);
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
    RedisCacheManagerBuilder redisCacheManagerDefault = RedisCacheManagerBuilder
        .fromConnectionFactory(lettuceConnectionFactory)
        .cacheDefaults(RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(RedisCacheConfiguration.defaultCacheConfig().getTtl())
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer())));

    RedisCacheConfiguration cacheConfigurationMenus = RedisCacheConfiguration
        .defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(ttlMenus))
        .disableCachingNullValues()
        .serializeValuesWith(RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer()));

    return redisCacheManagerDefault
        .withCacheConfiguration(Constants.CACHE_NAME_MENUS, cacheConfigurationMenus)
        .build();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setDefaultSerializer(new StringRedisSerializer());
    return template;
  }

  @Bean
  public CustomCacheManager customCacheManager(CacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {
    return new CustomCacheManager(cacheManager, redisTemplate);
  }

}