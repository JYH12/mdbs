package com.mdbs.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

//import com.mdbs.pojo.BackupFile;
import com.mdbs.pojo.BackupPlan;
@Repository
public interface BackupPlanMapper {
	//public List<BackupPlan> listBplanByUidAndTime(int uid,int offfset,int limit) ;//分页显示用户备份计划列表
	public List<BackupPlan> listBplanByUid(int uid) ;//备份计划列表
	
	public BackupPlan getBplanByBid(int pid);//单个备份计划详情
	
	public int insterBplan(BackupPlan backupPlan);//插入一条新的备份计划
	
	public void deleteBplan(int bid);//删除一条备份计划
	
	public int getBplanCountByUid(int uid);//查找该用户的备份计划个数
	
	public int updateBplan(BackupPlan backupPlan);//更新备份计划
	
	//public List<BackupFile> getBpFileList(int bid);//查询备份计划的备份文件
	public int  updateTime(@Param("nextTime")Date nextTime,@Param("lastTime")java.util.Date lastTime,@Param("bid")int bid) ;
}
