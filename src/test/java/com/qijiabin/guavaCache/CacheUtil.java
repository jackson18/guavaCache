package com.qijiabin.guavaCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.qijiabin.entity.Person;

public class CacheUtil {

	/**
	 * 设定了缓存大小为10
	 * 
	 * 最后一次访问24小时后移出
	 * 
	 * 通过recordStats()函数，还可开启缓存的访问统计，通过调用status()方法，返回包含统计信息的CacheStats对象，
	 * 可以获取缓存的很多统计信息：hitCount（命中成功次数），missCount（命中失败次数），loadSuccessCount（载入成功次数），
	 * loadExceptionCount（载入失败次数），totalLoadTime（总载入时间），evictionCount（移除次数），
	 * requestCount() （访问次数），hitRate()（命中成功率），missRate()（命中失败率），loadCount()（载入次数）等.
	 * 
	 * 命中失败时会通过load方法将查询键值加入缓存
	 */
    private static LoadingCache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterAccess(24, TimeUnit.HOURS)
            .recordStats()
            .build(new CacheLoader<Object, Object>() {
                @Override
                public Object load(Object key) throws Exception {
                    return key;
                }
            });

    public static Object get(Object key) throws ExecutionException {
        Object var = cache.get(key);

        if (var.equals(key)) {
            System.out.println("执行其他操作,查询该值");
            /**执行其他操作，获取值**/
            Object object = "Google.com.hk";
            put(key, object);
        } else {
            System.out.println("从Cache中取值....");
        }
        return cache.get(key);
    }

    public static void put(Object key, Object value) {
        cache.put(key, value);
    }

    @Test
    public void TestCache() throws ExecutionException {
        System.out.println(CacheUtil.get("man"));
        CacheUtil.put("man", new Person("hopg", 123));
        System.out.println(CacheUtil.get("man"));
        System.out.println();

        System.out.println(CacheUtil.get("person").toString());
        Person person = new Person();
        person.setAge(11);
        person.setName("tSun");
        CacheUtil.put("person", person);
        System.out.println(CacheUtil.get("person").toString());
        System.out.println();

        System.out.println(CacheUtil.get("woman"));
        CacheUtil.put("woman", new Person("google", 666));
        System.out.println(CacheUtil.get("woman"));
        System.out.println();
        
        System.out.println(cache.stats());
    }
}