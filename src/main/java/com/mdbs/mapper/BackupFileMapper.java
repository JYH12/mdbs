package com.mdbs.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mdbs.pojo.BackupFile;
@Repository
public interface BackupFileMapper {
	//按fid查询文件的地址
	public String getFilePath(int fid);
	//按照bid按照时间顺序查询该备份计划的备份文件
	public List<BackupFile> getFileListByTime(int bid);
	//找出文件名
	public String getFileName(int fid);
	
	public Date getLastTime(int bid);
	
	
	//找出恢复时间点之前的最近一次全量备份
	public String getFilePathByTime(@Param("bid")int bid,@Param("coveryTime")String coveryTime);
	/*
	 * select fileurl
    -> from backupfile
    -> where bid=#{bid} and filecreatetime<str_to_date(coveryTime,
    -> '%Y-%m-%d %T') order by filecreatetime limited 1;
	 */
	//插入一条备份文件记录
	public void insertFile(BackupFile backupFile);
}
