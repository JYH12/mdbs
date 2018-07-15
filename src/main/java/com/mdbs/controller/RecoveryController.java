package com.mdbs.controller;

//import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.mdbs.pojo.BackupFile;
import com.mdbs.pojo.Dbrecovery;
import com.mdbs.service.RecoveryService;

@Controller
@RequestMapping("/")
public class RecoveryController {
	@Autowired
	RecoveryService recoveryService;
	/*
	 * @parm dbrecovery封装恢复到哪个数据库
	 * @parm bid备份计划
	 * @author john
	 * 弃用HDFS 考虑采用本地磁盘管理文件
	 * 选择恢复时间点  增量备份的恢复
	 */
	@RequestMapping("recovery.do")
	@ResponseBody
	public Map<String, Object>recovery(@RequestBody Dbrecovery dbrecovery) {
		/*
		 * 在线恢复流程
		 * 1.前端传来的表单信息:数据库信息、恢复时间点
		 * 2.MySQL中查出上一次做全量备份的时间并在本地磁盘中找出该全量备份文件
		 * 3.找到之后各增量备份日志文件
		 * 4.先做全量备份恢复
		 * 5.再做增量备份恢复从上一次的全量备份时二进制日志中到恢复的时间点
		 * 
		 */
		Map<String, Object>map=recoveryService.recovery(dbrecovery);
		return map;
	}
	//在线恢复页面的备份
	//fileList按照时间顺序列出备份版本
	/*@RequestMapping("recoveryinfo.do")
	public String recoveryinfo(int bid,Model model) {
		List<BackupFile>fileList=recoveryService.getFileList(bid);
		model.addAttribute("fileList", fileList);
		model.addAttribute("bid",bid);
		return "recovery";
	}*/

}
