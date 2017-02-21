package info.sugengbin.springboot.redis.first;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * Date: 2017年2月21日<br/>
 * 
 * @author sugengbin
 */
@Configuration
public class FirstRedisConfig {

	@Value("${spring.redis.first.database}")
	private int database;

	@Value("${spring.redis.first.password}")
	private String password;

	@Value("${spring.redis.first.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.first.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.first.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.first.pool.max-wait}")
	private int maxWait;

	@Value("${spring.redis.first.sentinel.master}")
	private String master;

	@Value("${spring.redis.first.sentinel.nodes}")
	private String nodes;

	@Value("${spring.redis.first.timeout}")
	private int timeout;

	@Primary
	@Bean(name = "firstRedisConnectionFactory")
	public RedisConnectionFactory firstRedisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxTotal(maxActive);
		poolConfig.setMaxWaitMillis(maxWait);
		Set<String> nodeSet = new HashSet<>();
		for (String node : nodes.split(",")) {
			nodeSet.add(node);
		}
		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration(master, nodeSet);
		JedisConnectionFactory factory = new JedisConnectionFactory(sentinelConfig, poolConfig);
		factory.setDatabase(database);
		factory.setPassword(password);
		factory.setTimeout(timeout);
		return factory;
	}

	@Bean(name = "firstStringRedisTemplate")
	public StringRedisTemplate ocsStringRedisTemplate(
			@Qualifier("firstRedisConnectionFactory") RedisConnectionFactory cf) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(cf);
		return stringRedisTemplate;
	}
}
