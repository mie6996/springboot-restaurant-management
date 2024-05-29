package com.restaurant.redis;

import com.micro.healthcheck.api.DhpHealthIndicator;
import com.micro.healthcheck.api.HealthStatus;
import com.restaurant.config.RedisConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForLocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;

@Slf4j
@Component
public class RedisCache implements DhpHealthIndicator {

  @Value("")
  private String host;
  @Value("6379")
  private int port;
  @Value("")
  private String password;

  private final RedisConfig redisConfig;

  public RedisCache(RedisConfig redisConfig) {
    this.redisConfig = redisConfig;
  }

  @PostConstruct
  public void init() {
    host = redisConfig.getRedisHost();
    port = redisConfig.getRedisPort();
    password = redisConfig.getRedisPassword();
  }

  @Override
  public HealthStatus checkHealth() {
    URI uri = URI.create("http://" + host + ":" + port);

    JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
        .useSsl()
        .build();

    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

    boolean healthy;
    Exception exception = null;
    try (JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 2000, password); var jedis = jedisPool.getResource()) {
      jedis.connect();
      healthy = true;
      log.info("Connected to Redis");
    } catch (Exception e) {
      healthy = false;
      exception = e;
      log.error("Failed to connect to Redis", e);
    }
    return HealthStatus.builder()
        .healthy(healthy)
        .urlBase(uri.toString())
        .exception(exception)
        .build();
  }
}
