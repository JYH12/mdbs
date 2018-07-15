package com.mdbs.pojo;

import java.util.Date;

import org.springframework.stereotype.Component;
@Component
public class BackupFile {
	private Integer fid;//文件ID
	private Integer bid;//备份计划ID
	private String fileName;//备份文件名
	private String fileUrl;//备份文件url
	private Date fileCreateTime;//备份文件的创建时间
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}

	
	public Date getFileCreateTime() {
		return fileCreateTime;
	}
	public void setFileCreateTime(Date fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
