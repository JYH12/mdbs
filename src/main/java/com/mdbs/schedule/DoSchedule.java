package com.mdbs.schedule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mdbs.mapper.BackupFileMapper;
import com.mdbs.mapper.BackupPlanMapper;
import com.mdbs.pojo.BackupPlan;

import com.mdbs.util.GetNow;
import com.mdbs.util.StringToDate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Component
public class DoSchedule {

	@Autowired
	JedisPool jedisPool;
	//每天0点定时从redis中找出当天需要备份的任务
	//
	//final BlockingQueue<BackupPlan> backupPlans = new LinkedBlockingQueue<BackupPlan>();
	@Scheduled(cron="0 0 0 * * ? ")
	public void taskQueue() throws InterruptedException {
	final BlockingQueue<BackupPlan> backupPlans = new LinkedBlockingQueue<BackupPlan>();
	Set<String>set=new HashSet<>();
	Map<String, String>map=new HashMap<>();
	try {
	Jedis jedis=jedisPool.getResource();
	String nowTime=GetNow.getNowDate();//获取当前日期
	set=jedis.smembers(nowTime);
	System.out.println(set);
	for (String bid: set){
		//获取bid对应的备份计划各字段
		map=jedis.hgetAll(bid);
		BackupPlan backupPlan=new BackupPlan();
		//backupPlan.setBpInstance(map.get("instance"));
		//封装当前bid对应的信息
		backupPlan.setBid(Integer.valueOf(bid));
		backupPlan.setDbAddress(map.get("Address"));
		backupPlan.setPort(Integer.valueOf(map.get("port")));
		backupPlan.setDbPassword(map.get("password"));
		backupPlan.setDbUser(map.get("user"));
		backupPlan.setBpcycle(Integer.valueOf(map.get("cycle")));
		backupPlan.setBpInstance(map.get("instance"));
		//backupPlan.setBpInstance(map.get("instance"));
		System.out.println(backupPlan);
		/*for(int i=0;i<20;i++)*/
		backupPlans.put(backupPlan);
		String nextBpTime=StringToDate.strDateAdd(nowTime, backupPlan.getBpcycle());
		jedis.srem(nowTime,bid);//删除当天备份记录
		jedis.sadd(nextBpTime, bid);//添加下一次备份记录
	}
		if (jedis!=null) {
			jedis.close();
		}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		System.err.println("获取连接失败");
		//System.out.println(jedisPool);
	}
		Task tasks[]=new Task[20];
		for(int i=0;i<20;i++)
		tasks[i]=new Task(backupPlans,"线程"+i);
		//多线程竞争同一个阻塞队列
		for(int i=0;i<20;i++) {
		tasks[i].start();
		
		}
		
	}
	
}