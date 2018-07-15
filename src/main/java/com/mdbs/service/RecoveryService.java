package com.mdbs.service;

//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdbs.mapper.BackupFileMapper;
import com.mdbs.mapper.BackupPlanMapper;
import com.mdbs.mapper.OpLogMapper;
import com.mdbs.pojo.BackupFile;
import com.mdbs.pojo.BackupPlan;
import com.mdbs.pojo.Dbrecovery;
import com.mdbs.pojo.OpLog;
import com.mdbs.util.BackUP;

@Service
public class RecoveryService {
	/*
	 * 首先要根据恢复的时间点找到上一次全量备份的备份文件
	 * 查询出恢复版本的.sql文件path
	 */
	@Autowired
	BackupFileMapper backupFileMapper;
	@Autowired
	BackupPlanMapper backupPlanMapper;
	@Autowired
	OpLogMapper opLogMapper;
	//恢复对象dbrecovery 备份计划bid 
	//先选出sql文件的path
	public Map<String, Object> recovery(Dbrecovery dbrecovery)  {
		//通过选择的还原版找出备份文件本地路径
		//String sqlPath=backupFileMapper.getFilePath(Integer.valueOf(dbrecovery.getRecoveryVersion()).intValue());
		//String fileName=backupFileMapper.getFileName(Integer.valueOf(dbrecovery.getRecoveryVersion()).intValue());
		int bid= dbrecovery.getBid();
		String savePath=backupFileMapper.getFilePathByTime(bid,dbrecovery.getRecoveryTime());
		BackupPlan backupPlan=backupPlanMapper.getBplanByBid(bid);
		Map<String, Object>map=new HashMap<>();
		Date date=new Date();
		try {
		BackUP.dbRecover(dbrecovery.getDbUser(),dbrecovery.getDbPassword(), dbrecovery.getDbAddress(),dbrecovery.getPort(), /*"dbname",*/savePath,dbrecovery.getRecoveryTime(),backupPlan.getDbUser(),backupPlan.getDbPassword(),backupPlan.getDbAddress(),String.valueOf(backupPlan.getPort()));
		//添加操作消息
		OpLog opLog=new OpLog();
		//操作的备份计划id
		opLog.setBpid(dbrecovery.getBid());
		//操作的类型
		opLog.setOperation("在线恢复到时间点："+dbrecovery.getRecoveryTime()+"成功!");
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//操作时间
		opLog.setOpTime(date);
		//文件名
		//opLog.setOpFile("恢复到时间点"+dbrecovery.getRecoveryTime());
		//添加一条新的操作记录
		opLogMapper.insertOpLog(opLog);
		map.put("status", 1);//成功
		}catch (Exception e) {
			map.put("status",0);//失败
			OpLog opLog=new OpLog();
			opLog.setBpid(dbrecovery.getBid());
			//操作的类型
			opLog.setOperation("在线恢复到时间点："+dbrecovery.getRecoveryTime()+"失败!");
			opLog.setOpTime(date);
			opLogMapper.insertOpLog(opLog);
			e.printStackTrace();
		}
		return map;
	}
	
	//查找备份文件列表
	public List<BackupFile>getFileList(int bid){
		return backupFileMapper.getFileListByTime(bid);
	}
}
