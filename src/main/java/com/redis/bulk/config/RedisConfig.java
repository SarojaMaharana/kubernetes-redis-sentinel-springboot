//package com.redis.bulk.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;
//
//@Configuration
//public class RedisConfig {
//	
//	@Value("${spring.redis.host}")  
//	private String redisHost;
//	
//	@Value("${spring.redis.port}")  
//	private Integer redisPort;
//
//	@Bean
//	    public JedisConnectionFactory connectionFactory() {
//	        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//	        connectionFactory.setHostName(redisHost);
//	        connectionFactory.setPort(redisPort);
//	        return connectionFactory;
//	    }
//
//
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate() {
//		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//		template.setConnectionFactory(connectionFactory());
//		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//		return template;
//	}
//}