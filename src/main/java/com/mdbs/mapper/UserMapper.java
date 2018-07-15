package com.mdbs.mapper;

import org.springframework.stereotype.Repository;

import com.mdbs.pojo.User;

@Repository
public interface UserMapper {
	public Integer selectUidByEmailAndPassword(User user);
	public String selectHeadUrl(Integer uid);
}
