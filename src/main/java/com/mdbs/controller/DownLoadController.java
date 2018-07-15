package com.mdbs.controller;

import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdbs.pojo.BackupFile;
import com.mdbs.service.BackupFileService;
//import com.mdbs.service.BackupPlanService;

@Controller
@RequestMapping("/")
/*
 * 下载备份文件
 */
public class DownLoadController {

	@Autowired
	BackupFileService backupFileService;
	//备份文件下载页
	//备份文件名和url列表
	//用户点击文件名进行下载
	@RequestMapping("/download.do")
	@ResponseBody
	public List<BackupFile> download(int bid) {
		//Map<String, String>filemap=backupPlanService.getBpFile(bid);
		//model.addAttribute("fileMap",filemap);
		//int bid=1;
		List<BackupFile>fileList=backupFileService.getFileListByTime(bid);
		for (BackupFile backupFile : fileList) {
			//返回前台可以下载的URL地址
			String url="http://172.18.107.146"+backupFile.getFileUrl();
			backupFile.setFileUrl(url);
		}
		//model.addAttribute("fileList",fileList);
		return fileList;
	}
}
