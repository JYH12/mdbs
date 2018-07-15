package com.mdbs.pojo;

import java.util.Date;

import org.springframework.stereotype.Component;

/*
 * 操作日志信息
 */


@Component
public class OpLog {
	private int lid;//日志id
	private int bpid;//备份计划id
	private String operation;//操作类型
	private Date opTime;//操作时间
	//private String opFile;
	
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getBpid() {
		return bpid;
	}
	public void setBpid(int bpid) {
		this.bpid = bpid;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	
	
	
}
