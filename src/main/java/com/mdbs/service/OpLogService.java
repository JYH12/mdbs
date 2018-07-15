package com.mdbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mdbs.mapper.OpLogMapper;

import com.mdbs.pojo.OpLog;

@Service
public class OpLogService {
	@Autowired
	OpLogMapper opLogMapper;
	/*@Autowired
	RedisTemplate<String, String> redisTemplate;*/
	
	public List<OpLog>getOplogByBid(int bid){
		return opLogMapper.getLogListByBid(bid);
	}
}
