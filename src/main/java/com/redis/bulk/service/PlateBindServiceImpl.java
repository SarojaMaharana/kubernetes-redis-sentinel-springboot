package com.redis.bulk.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlateBindServiceImpl  {
	
	@Autowired
	private RedisTemplate<String, Object> mRedisTemplate;

	public Object checkPlateStatus(String version, String plateno) {
		return null;
	}
	
	public String saveReadData() {
		mRedisTemplate.opsForValue().set("verification code for number 13240084014","8888");
		String code = (String) mRedisTemplate.opsForValue().get("verification code for number 13240084014");
		return code;
	}
	
	public void uploadDataToRedis() {
		
		HashMap<String, Object> values = prepareCacheData();
		mRedisTemplate.executePipelined(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
					//StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
					long startTime = System.currentTimeMillis(); 
					Iterator<Entry<String, Object>> iter = values.entrySet().iterator();
					while(iter.hasNext()){
			            Entry<String, Object> entry = iter.next();
			            connection.set(entry.getKey().getBytes(), entry.getValue().toString().getBytes());
			        }
					long endTime = System.currentTimeMillis(); 
					System.out.println("Batch 程序运行时间：" + (endTime - startTime) + "ms");
					return null;
			}
		});
	}
	
	private HashMap<String, Object> prepareCacheData() {
		HashMap<String, Object> values = new HashMap<>();
		for (int i=0;i<10000;i++) {
			String key = "parking:parkid:" + i + "sp:etcp";
			values.put(key, i);
		}
		return values;
	}
	

	public void uploadDataBySequence() {
		HashMap<String, Object> values = prepareCacheData();
		
		long startTime = System.currentTimeMillis(); 
		Iterator<Entry<String, Object>> iter = values.entrySet().iterator();
		while(iter.hasNext()){
            Entry<String, Object> entry = iter.next();
            mRedisTemplate.opsForValue().set(entry.getKey(), entry.getValue().toString());
        }
		long endTime = System.currentTimeMillis(); 
		System.out.println("依次单条写入程序运行时间：" + (endTime - startTime) + "ms");
		
	}
}