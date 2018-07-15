package com.mdbs.controller;

import java.util.List;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdbs.pojo.BackupPlan;
import com.mdbs.pojo.User;
//import com.mdbs.pojo.PageBean;
import com.mdbs.service.BackupPlanService;


@Controller
@RequestMapping("/")
public class MyIndexController {
	
	@Autowired
	private BackupPlanService backupPlanService;
	/*
	 * 去主页面显示该用户备份计划
	 * @parm pageBean分页类
	 *显示备份计划分页列表 
	 */
	@RequestMapping("/myindex.do")
	@ResponseBody
	public List<BackupPlan> myindex( int uid){
		/*int sessionUid=(int)httpSession.getAttribute("uid");*/
		/*PageBean<BackupPlan>pageBean=(PageBean<BackupPlan>) backupPlanService.getBplanListByTime(sessionUid,1);
		model.addAttribute(pageBean);
		*/
		//System.out.println(user.getUid());
		/*User user=new User();
		user.setUid(1);*/
		List<BackupPlan>baPlans=backupPlanService.getbplanList(uid);
		/*model.addAttribute("baPlans",baPlans);*/
		/*return "myindex";*/
		return baPlans;
	}
	
	
}
