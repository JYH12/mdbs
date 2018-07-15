package com.mdbs.pojo;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.mdbs.util.BackUP;
@Component
public class BackupPlan {
	
	//备份计划名和id,所属用户id
	private Integer bid;
	private Integer uid;
	private String planName;
	//备份源数据库信息
	private String dbAddress;
	private int port;
	private String dbUser;
	private String dbPassword;
	//备份周期、创建时间、备份实例、下一次备份时间
	private int bpcycle;
	private Date createTime;
	//private String bpinstance;
	private Date lastTime;
	private String bpInstance;
	public String getBpInstance() {
		return bpInstance;
	}
	public void setBpInstance(String bpInstance) {
		this.bpInstance = bpInstance;
	}

	public static final String dbName = "df";
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	private java.sql.Date nextTime;
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(java.sql.Date nextTime) {
		this.nextTime = nextTime;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getDbAddress() {
		return dbAddress;
	}
	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public int getBpcycle() {
		return bpcycle;
	}
	public void setBpcycle(int bpcycle) {
		this.bpcycle = bpcycle;
	}
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String instance() {
		String[]instance=bpInstance.split("_");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<instance.length;i++)
			sb.append(" "+instance[i]);
		return sb.toString();
	}

	public void backup() throws Exception{
		try {
		BackUP.dbBackup(String.valueOf(bid),dbUser, dbPassword, dbAddress,String.valueOf(port),instance(),bpcycle);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		}
	
	public static String getDbname() {
		return dbName;
	}
	
		
}
