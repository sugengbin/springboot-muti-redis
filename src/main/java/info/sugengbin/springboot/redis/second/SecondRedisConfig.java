package info.sugengbin.springboot.redis.second;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/** 
 *
 * Date:     2017年2月21日<br/> 
 * @author   449632 
 */
@Configuration
public class SecondRedisConfig {

	@Value("${spring.redis.second.database}")
	private int database;

	@Value("${spring.redis.second.password}")
	private String password;

	@Value("${spring.redis.second.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.second.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.second.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.second.pool.max-wait}")
	private int maxWait;

	@Value("${spring.redis.second.sentinel.master}")
	private String master;

	@Value("${spring.redis.second.sentinel.nodes}")
	private String nodes;

	@Value("${spring.redis.second.timeout}")
	private int timeout;

	@Bean(name = "secondRedisConnectionFactory")
	public RedisConnectionFactory secondRedisConnectionFactory() {
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

	@Bean(name = "secondStringRedisTemplate")
	public StringRedisTemplate secondStringRedisTemplate(
			@Qualifier("secondRedisConnectionFactory") RedisConnectionFactory cf) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(cf);
		return stringRedisTemplate;
	}
}
