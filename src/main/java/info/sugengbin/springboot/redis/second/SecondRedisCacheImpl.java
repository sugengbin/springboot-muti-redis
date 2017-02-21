package info.sugengbin.springboot.redis.second;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import info.sugengbin.springboot.utils.JsonUtil;

/**
 *
 * Date: 2017年2月21日<br/>
 * 
 * @author sugengbin
 */
@Component
public class SecondRedisCacheImpl implements ISecondRedisCache {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("secondStringRedisTemplate")
	private StringRedisTemplate template;

	@Override
	public boolean setObject(String key, Object object, long paramLong, TimeUnit paramTimeUnit) {
		try {
			String value = JsonUtil.toJson(object);
			template.opsForValue().set(key, value, paramLong, paramTimeUnit);
			return true;
		} catch (Exception e) {
			logger.error("Error when setObject to redis. error:{}", e);
			return false;
		}
	}

	@Override
	public boolean setObject(String key, Object object) {
		try {
			String value = JsonUtil.toJson(object);
			template.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("Error when setObject to redis. error:{}", e);
			return false;
		}
	}

	@Override
	public <T> T getObject(String key, Class<T> clazz) {
		try {
			String value = template.opsForValue().get(key);
			if (value == null) {
				return null;
			} else {
				return JsonUtil.fromJson(value, clazz);
			}
		} catch (Exception e) {
			logger.error("Error when getObject to redis.key:{} error:{}", key, e);
			return null;
		}
	}

	@Override
	public <T> T getListObject(String key, Type type) {
		try {
			String value = template.opsForValue().get(key);
			if (value == null) {
				return null;
			} else {
				return JsonUtil.fromJson(value, type);
			}
		} catch (Exception e) {
			logger.error("Error when getListObject to redis.key:{} error:{}", key, e);
			return null;
		}
	}

	@Override
	public boolean deleteObject(String key) {
		try {
			template.delete(key);
			return true;
		} catch (Exception e) {
			logger.error("Error when delete key from redis. error:{}", e);
			return false;
		}
	}
}
