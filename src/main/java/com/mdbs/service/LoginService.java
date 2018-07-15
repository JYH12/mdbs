package com.mdbs.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdbs.mapper.UserMapper;
import com.mdbs.pojo.User;

@Service
public class LoginService {
	@Autowired
	private UserMapper userMapper;
	public Map<String, Object> login(User user) {
		Map<String,Object> map = new HashMap<>();
		 Integer uid = userMapper.selectUidByEmailAndPassword(user);
	        if(uid==null){
	            map.put("status",0);
	            map.put("msga","用户名或密码错误~");
	            return map;
	        }
	        //String headUrl = userMapper.selectHeadUrl(uid);
	        map.put("status",1);
	        map.put("uid",uid);
	        //map.put("headurl",headUrl);
		return map;
}
	
}
