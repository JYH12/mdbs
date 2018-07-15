package com.mdbs.pojo;

import org.springframework.stereotype.Component;

@Component
public class Dbrecovery {
	//恢复的数据库信息
	private int bid;//备份计划id
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	private String dbAddress;
	private String port;
	private String dbUser;
	private String dbPassword;
	private String recoveryTime;//选择恢复到哪一个时间点
	public String getDbAddress() {
		return dbAddress;
	}
	public void setDbAddress(String dbAddress) {
		this.dbAddress = dbAddress;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
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

	public String getRecoveryTime() {
		return recoveryTime;
	}
	public void setRecoveryTime(String recoveryTime) {
		this.recoveryTime = recoveryTime;
	}

}
