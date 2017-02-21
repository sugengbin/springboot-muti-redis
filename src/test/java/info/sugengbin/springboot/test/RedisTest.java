package info.sugengbin.springboot.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.reflect.TypeToken;

import info.sugengbin.springboot.Application;
import info.sugengbin.springboot.redis.first.IFirstRedisCache;
import info.sugengbin.springboot.redis.second.ISecondRedisCache;

/**
 *
 * Date: 2017年2月21日<br/>
 * 
 * @author sugengbin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RedisTest {

	@Autowired
	private IFirstRedisCache firstRedisCache;

	@Autowired
	private ISecondRedisCache secondRedisCache;

	@Test
	public void test() {
		// test set
		Assert.assertTrue(firstRedisCache.setObject("test:first_redis1", "f1"));
		Assert.assertTrue(firstRedisCache.setObject("test:first_redis2", "f2", 1, TimeUnit.MINUTES));
		
		Assert.assertTrue(secondRedisCache.setObject("test:second_redis1", "s1"));
		Assert.assertTrue(secondRedisCache.setObject("test:second_redis2", "s2", 1, TimeUnit.MINUTES));
		// test get
		Assert.assertEquals("f1", firstRedisCache.getObject("test:first_redis1", String.class));
		Assert.assertEquals("f2", firstRedisCache.getObject("test:first_redis2", String.class));
		
		Assert.assertEquals("s1", secondRedisCache.getObject("test:second_redis1", String.class));
		Assert.assertEquals("s2", secondRedisCache.getObject("test:second_redis2", String.class));
		
		// test get list
		List<Node> nodes = new ArrayList<>();
		nodes.add(new Node(1L, "node1"));
		nodes.add(new Node(2L, "node2"));
		Assert.assertTrue(firstRedisCache.setObject("test:list", nodes));
		List<Node> list = firstRedisCache.getListObject("test:list", new TypeToken<ArrayList<Node>>() {}.getType());
		Assert.assertTrue(list.size() == 2);
		
		// test delete
		Assert.assertTrue(firstRedisCache.deleteObject("test:first_redis1"));
		Assert.assertTrue(firstRedisCache.getObject("test:first_redis1", String.class) == null);
	}
	
	class Node implements Serializable {
		private static final long serialVersionUID = -9155137649254788171L;
		private Long id;
		private String name;
		public Node(){}
		public Node(Long id, String name){
			this.id = id;
			this.name = name;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

}
