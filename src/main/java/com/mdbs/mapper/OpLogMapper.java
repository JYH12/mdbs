package com.mdbs.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mdbs.pojo.OpLog;
@Repository
public interface OpLogMapper {
	//操作日志列表
	public List<OpLog> getLogListByBid(int bid);
	//插入一条新的操作日志
	public int insertOpLog(OpLog opLog);
}
