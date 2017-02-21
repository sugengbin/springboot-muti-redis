package info.sugengbin.springboot.redis.second;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 *
 * Date: 2017年2月21日<br/>
 * 
 * @author sugengbin
 */
public interface ISecondRedisCache {

	/**
	 * set object & timeout
	 * 
	 * @param key
	 * @param object
	 * @param paramLong
	 * @param paramTimeUnit
	 * @return
	 */
	public boolean setObject(String key, Object object, long paramLong, TimeUnit paramTimeUnit);

	/**
	 * set object
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public boolean setObject(String key, Object object);
	
	/**
	 * get object
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getObject(String key, Class<T> clazz);
	
	/**
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> T getListObject(String key, Type type);
	
	/**
	 * delete by key
	 * 
	 * @param key
	 * @return
	 */
	public boolean deleteObject(String key);

}
