package com.restaurant.util;

import io.lettuce.core.support.caching.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@Slf4j
public class CustomCacheManager {
    private CacheManager cacheManager;
    private RedisTemplate<String, Object> redisTemplate;

    public CustomCacheManager(CacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {
        this.cacheManager = cacheManager;
        this.redisTemplate = redisTemplate;
    }

    public void clearCache(String table, String key) {
        var cache = cacheManager.getCache(table);
        log.info("Clearing cache...");
        if (cache instanceof RedisCache) {
            Set<String> keys = redisTemplate.keys(table + ":" + key + "*");
            for (String keyToDelete : keys) {
                redisTemplate.delete(keyToDelete);
            }
        }
    }

}
