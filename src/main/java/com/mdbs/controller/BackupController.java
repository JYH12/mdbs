package com.mdbs.controller;

import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdbs.pojo.BackupPlan;
import com.mdbs.schedule.DoSchedule;
import com.mdbs.service.BackupPlanService;
@Controller
@RequestMapping("/")
/*
 * 
 */
public class BackupController {
	@Autowired
	BackupPlanService backupPlanService;
	
	
	/*
	 * 去创建备份计划页面
	 *//*
	 @RequestMapping("/createbp.do")
	    public String toPublish(Model model){  
	        return "createbp";
	    }*/
	/*
	 * 添加一个新的备份计划
	 * 并跳转到备份计划详情页
	 */
	@RequestMapping("/create.do")
	@ResponseBody
	public Map<String, Object> insertplan(@RequestBody BackupPlan backupPlan) {
		
		int bid=backupPlanService.insertBplan(backupPlan);
		Map<String, Object>map=new HashMap<>();
		if (bid!=0) {
			//创建成功
			map.put("status",1);
		}
		else {
			//创建失败
			map.put("status", 0);
		}
		return map;
	}
	
	/*
	 *去备份计划详情页
	 */
	@RequestMapping("/bplan.do")
	@ResponseBody
	public BackupPlan bplan(int bid) {
		System.err.println(bid);
		BackupPlan backupPlan=backupPlanService.getBplanById(bid);
		/*model.addAttribute(backupPlan);*/
		return backupPlan;
	}
	/*
	 * 删除备份计划
	 
	@RequestMapping("/delete.do")
	public String deleteplan(){
		return "";
	}
	/
	/*
	 * 更新备份计划
	 */
	@RequestMapping("/update.do")
	@ResponseBody
	public Map<String,Object> updateplan(@RequestBody BackupPlan backupPlan) {
		int success=backupPlanService.updateBplan(backupPlan);
		Map<String, Object>map=new HashMap<>();
		if (success!=0) {
			//更新成功
			map.put("status",1);
		}
		else {
			//更新失败
			map.put("status", 0);
		}
		return map;
	}

}
