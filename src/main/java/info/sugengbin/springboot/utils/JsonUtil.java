package info.sugengbin.springboot.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * 
 *
 * Date: 2017年2月21日<br/>
 * 
 * @author sugengbin
 */
public class JsonUtil {

	private static Gson gson = new Gson();

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * 
	 * @param json
	 * @param typeOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}

}
