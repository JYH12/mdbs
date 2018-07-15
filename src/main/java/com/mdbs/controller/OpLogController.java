package com.mdbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdbs.pojo.OpLog;
import com.mdbs.service.OpLogService;

@Controller
@RequestMapping("/")
public class OpLogController {

	@Autowired
	OpLogService opLogService;
	//去操作日志页面
	@RequestMapping("/oplog.do")
	public@ResponseBody List<OpLog> oplog(int bid) {
		
		List<OpLog>opLogsList=opLogService.getOplogByBid(bid);
		
		return opLogsList;
	}
}
