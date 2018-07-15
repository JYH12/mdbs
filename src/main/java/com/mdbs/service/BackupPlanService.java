package com.mdbs.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdbs.mapper.BackupFileMapper;
import com.mdbs.mapper.BackupPlanMapper;
//import com.mdbs.pojo.BackupFile;
import com.mdbs.pojo.BackupPlan;
//import com.mdbs.pojo.PageBean;
//import com.mdbs.util.BackUP;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class BackupPlanService {
	@Autowired
	private BackupPlanMapper backupPlanMapper;
	@Autowired
	private BackupFileMapper backupFileMapper;
	@Autowired
    private JedisPool jedisPool;
	
	/*
	 * 按时间分页显示用户备份计划
	 */
	/*public PageBean<BackupPlan>getBplanListByTime(Integer uid,int curPage){
		int limit = 8;
        int offset = (curPage-1) * limit;
        //获得总记录数，总页数
        int allCount = backupPlanMapper.getBplanCountByUid(uid);
        int allPage = 0;
        if (allCount <= limit) {
            allPage = 1;
        } else if (allCount / limit == 0) {
            allPage = allCount / limit;
        } else {
            allPage = allCount / limit + 1;
        }
        List<BackupPlan>bPlanList=backupPlanMapper.listBplanByUidAndTime(uid,offset,limit);
        PageBean<BackupPlan>pageBean=new PageBean<>(allPage, curPage);
        pageBean.setList(bPlanList);
		return pageBean;
	}*/
	
	/*
	 * 一条备份计划的详情信息
	 */
	public BackupPlan getBplanById(Integer bid) {
		return backupPlanMapper.getBplanByBid(bid);
	}
	
	/*
	 * 插入一条新的备份计
	 * 并将备份计划下一次备份时需要的信息插入到redis缓存中
	 * 
	 */
	public int insertBplan(BackupPlan backupPlan) {
		Date createTime=new Date();
		//创建日期 /
		//新创建默认之后的0点
        //下一次备份日期毫秒数
        long addTime=(long)24*60*60*1000;
        java.sql.Date nextTime=new java.sql.Date(createTime.getTime()+addTime);
        //封装
        backupPlan.setCreateTime(createTime);
        backupPlan.setNextTime(nextTime);
		backupPlanMapper.insterBplan(backupPlan);
		int bid=backupPlan.getBid();
		try {
		Jedis jedis = jedisPool.getResource();
		Map<String, String>map=new HashMap<String, String>();
		map.put("Address", backupPlan.getDbAddress());
		map.put("port", backupPlan.getPort()+"");
		map.put("user", backupPlan.getDbUser());
		map.put("password", backupPlan.getDbPassword());
		map.put("cycle", String.valueOf(backupPlan.getBpcycle()));
		map.put("instance", backupPlan.getBpInstance());
		//添加备份计划下次备份信息到redis
		//下次备份时间为key,value为备份计划id
		String skey=nextTime.toString();
		String hkey=String.valueOf(bid);
		jedis.sadd(skey,hkey);
		//hash中存备份计划信息，以备份计划id为key
		jedis.hmset(hkey, map);
		if (jedis!=null) {
			jedis.close();
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bid;
	}
	/*
	 *删除一条备份计划
	 
	public void deleteBplan(int bid) {
		backupPlanMapper.deleteBplan(bid);
	}
	*/
	/*
	 * 修改备份计划
	 */
	//并更新redis中信息
	public int  updateBplan(BackupPlan backupPlan) {
		int stutas1=backupPlanMapper.updateBplan(backupPlan);
		Jedis jedis=jedisPool.getResource();
		int bid=backupPlan.getBid();
		Map<String, String>map=new HashMap<String, String>();
		map.put("Address", backupPlan.getDbAddress());
		map.put("port", backupPlan.getPort()+"");
		map.put("user", backupPlan.getDbUser());
		map.put("password", backupPlan.getDbPassword());
		map.put("cycle", String.valueOf(backupPlan.getBpcycle()));
		map.put("instance", backupPlan.getBpInstance());
		String stutas2=jedis.hmset(String.valueOf(bid), map);
		if (jedis!=null) {
			jedis.close();
		}
		if (stutas1==1&&stutas2!=null) {
			return 1;
		}
		else {
		return 0;
		}
	}
	
	/*
	 *查看该备份计划的备份文件 
	 */
	/*
	public Map<String, String> getBpFile(int bid){
		List<BackupFile> fileList=backupPlanMapper.getBpFileList(bid);
		Map<String, String> fileMap=new HashMap<>();
		//添加文件名和文件URL到filemap中
		for (BackupFile backupFile : fileList) {
			fileMap.put(backupFile.getFileName(), backupFile.getFileUrl());
		}
		return fileMap;
	}
	/*public void backup(BackupPlan backupPlan) {
		try {
		BackUP.dbBackup(String.valueOf(backupPlan.getBid()),backupPlan.getDbUser(), 
				backupPlan.getDbPassword(),backupPlan.getDbAddress(),String.valueOf(backupPlan.getPort()), );
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		}*/
	public List<BackupPlan>getbplanList(int uid){
		List<BackupPlan>list= backupPlanMapper.listBplanByUid(uid);
		/*for (BackupPlan backupPlan : list) {
			int bid=backupPlan.getBid();
			backupPlan.setLastTime(backupFileMapper.getLastTime(bid));
		}*/
		return list;
	}
}
