package com.qijiabin.guavaCache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.qijiabin.entity.Person;

public class CacheDemo {
	
	/**
	 * 设定了缓存大小为100
	 * 
	 * 写后24小时过期
	 * 
	 * 通过recordStats()函数，还可开启缓存的访问统计，通过调用status()方法，返回包含统计信息的CacheStats对象，
	 * 可以获取缓存的很多统计信息：hitCount（命中成功次数），missCount（命中失败次数），loadSuccessCount（载入成功次数），
	 * loadExceptionCount（载入失败次数），totalLoadTime（总载入时间），evictionCount（移除次数），
	 * requestCount() （访问次数），hitRate()（命中成功率），missRate()（命中失败率），loadCount()（载入次数）等.
	 */
    private static Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .recordStats()
            .build();

    public static Object get(Object key) throws ExecutionException {
        Object var = cache.get(key, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("如果没有值,就执行其他方式去获取值");
                String var = "Google.com.sg";
                return var;
            }
        });
        return var;
    }

    public static void put(Object key, Object value) {
        cache.put(key, value);
    }


    @Test
    public void CacheTest() throws ExecutionException {
        System.out.println(CacheDemo.get("man"));
        CacheDemo.put("man", new Person("hopg", 123));
        System.out.println(CacheDemo.get("man"));
        System.out.println();

        Person person = new Person();
        person.setAge(11);
        person.setName("tSun");
        System.out.println(CacheDemo.get("person").toString());
        CacheDemo.put("person", person);
        System.out.println(CacheDemo.get("person").toString());
        System.out.println();

        System.out.println(CacheDemo.get("woman"));
        CacheDemo.put("women", new Person("google", 666));
        System.out.println(CacheDemo.get("woman"));
        System.out.println();
        
        System.out.println(cache.stats());
    }
    
}

