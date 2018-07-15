package com.mdbs.schedule;

import java.util.concurrent.BlockingQueue;

import com.mdbs.pojo.BackupPlan;

public class Task extends Thread {
	private BlockingQueue<BackupPlan>backupQueue;
	private String name;
	public Task(BlockingQueue<BackupPlan>backupPlans,String name){
		// TODO Auto-generated constructor stub
		this.backupQueue=backupPlans;
		this.name=name;
	}
	//消费者
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!(backupQueue.isEmpty())) {
		try {
			//System.out.println(name+"开始执行备份\n");
			backupQueue.take().backup();//从队列中取出备份任务执行
			System.out.println(name+"备份完成\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}
