package com.nithin.CachingWork.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;


import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheConfiguration employeeCache = RedisCacheConfiguration.defaultCacheConfig()
//                .prefixCacheNameWith("employee:")
                .entryTtl(Duration.ofSeconds(30));
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new StringRedisSerializer()
//                ))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new GenericJacksonJsonRedisSerializer(objectMapper)
//                ));

        RedisCacheConfiguration departmentCache = RedisCacheConfiguration.defaultCacheConfig()
//                .prefixCacheNameWith("department:")
                .entryTtl(Duration.ofSeconds(60));
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new StringRedisSerializer()
//                ))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new StringRedisSerializer()
//                ));

        RedisCacheConfiguration productCache = RedisCacheConfiguration.defaultCacheConfig()
//                .prefixCacheNameWith("product:")
                .entryTtl(Duration.ofSeconds(120));
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new StringRedisSerializer()
//                ))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new GenericJacksonJsonRedisSerializer(objectMapper)
//                ));

        RedisCacheConfiguration weatherCache = RedisCacheConfiguration.defaultCacheConfig()
//                .prefixCacheNameWith("product:")
                .entryTtl(Duration.ofSeconds(100));
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new StringRedisSerializer()
//                ))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
//                        new GenericJacksonJsonRedisSerializer(objectMapper)
//                ));

        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("employees",employeeCache)
                .withCacheConfiguration("departments",departmentCache)
                .withCacheConfiguration("products",productCache)
                .withCacheConfiguration("weather",weatherCache)
                .build();
    }
}
