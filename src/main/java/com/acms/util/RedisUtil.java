package com.acms.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.acms.config.VVConfig;
import com.acms.config.WebConfigLoader;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	//private JedisPoolConfig jedisPoolConfig = null;
	
	//private static volatile JedisSentinelPool jedisSentinelPool;
	
	private static volatile JedisPool jedisPool;
	
	/*public static JedisSentinelPool getSentinelPool(){
		if(jedisSentinelPool==null){
			synchronized (RedisUtil.class){
				if(jedisSentinelPool==null){
					VVConfig config = WebConfigLoader.getConfig();
					String maxTotal = config.getConfig("redis.pool.maxTotal");
					String maxIdle = config.getConfig("redis.pool.maxIdle");
					String maxWaitMillis = config.getConfig("redis.pool.maxWaitMillis");
					String testOnBorrow = config.getConfig("redis.pool.testOnBorrow");
					String sentinelpoolhost = config.getConfig("redis.sentinelpool.host");
					String sentinelpoolport = config.getConfig("redis.sentinelpool.port");

					JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
					
					jedisPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
					jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
					jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
					jedisPoolConfig.setTestOnBorrow(Boolean.getBoolean(testOnBorrow));


					jedisPoolConfig = new JedisPoolConfig();
					Set<String> sentinelpoolset = new HashSet<String>();
					sentinelpoolset.add(sentinelpoolhost+":"+sentinelpoolport);
					try {
						jedisSentinelPool = new JedisSentinelPool("mymaster",sentinelpoolset , jedisPoolConfig);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
						throw e;
					}
				}
			}
		}
		return jedisSentinelPool;
	}*/
	
	public static JedisPool getPool(){
		if(jedisPool==null){
			synchronized (RedisUtil.class){
				if(jedisPool==null){
					VVConfig config = WebConfigLoader.getConfig();
					String maxTotal = config.getConfig("redis.pool.maxTotal");
					String maxIdle = config.getConfig("redis.pool.maxIdle");
					String maxWaitMillis = config.getConfig("redis.pool.maxWaitMillis");
					String testOnBorrow = config.getConfig("redis.pool.testOnBorrow");
					String redishost = config.getConfig("redis.host");
					String redisport = config.getConfig("redis.port");
					String redispassword = config.getConfig("redis.password");
					JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
					
					jedisPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
					jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
					jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
					jedisPoolConfig.setTestOnBorrow(Boolean.getBoolean(testOnBorrow));

					try {
						jedisPool = new JedisPool(jedisPoolConfig, redishost,Integer.valueOf(redisport),2000,redispassword);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
						throw e;
					}
				}
			}
		}
		return jedisPool;
	}
	/*private static volatile RedisUtil redisUtil = null;
	private RedisUtil(){
		
	}
	public static RedisUtil getInstance(){
		if(redisUtil == null){
			synchronized (RedisUtil.class){
				if(redisUtil == null){
					redisUtil = new RedisUtil();
				}
			}
		}
		return redisUtil;
	}*/
}
