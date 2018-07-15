package com.mdbs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mdbs.mapper.BackupFileMapper;
import com.mdbs.mapper.BackupPlanMapper;
import com.mdbs.mapper.OpLogMapper;
import com.mdbs.pojo.BackupFile;
//import com.mdbs.pojo.DbInstance;
import com.mdbs.pojo.OpLog;
@Component
public class BackUP {
	 /**
     * 数据库表备份
     * 
     * @throws Exception
     */
	@Autowired
	private BackupFileMapper backupFileMapper;
	@Autowired
	private BackupPlanMapper backupPlanMapper;
	@Autowired
	private OpLogMapper opLogMapper;
	
	private static BackUP backUP;
    
	@PostConstruct
    public void init() { 
		backUP=this;
		backupFileMapper=this.backupFileMapper;
		backupPlanMapper=this.backupPlanMapper;
		opLogMapper=this.opLogMapper;
	}
	public static void tableBackup(String dbUser,String dbPass,String dbHost,String dbPort,String dbName,String savePath, String tableName)
            throws Exception {

        Runtime runtime = Runtime.getRuntime();
        // -u后面是用户名，-p是密码，-family是数据库的名字
        Process process = runtime.exec("mysqldump -h " + dbHost + " -P "
                + dbPort + " -u " + dbUser + " -p" + dbPass + " " + dbName
                + " " + tableName);
        InputStream inputStream = process.getInputStream();// 得到输入流，写成.sql文件
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        s = sb.toString();
        File file = new File(savePath);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(s.getBytes());
        fileOutputStream.close();
        br.close();
        reader.close();
        inputStream.close();
    }

    /**
     * 备份数据库
     * 
     * @param savePath
     * @throws Exception
     */
    public static void dbBackup(String bid,String dbUser, String dbPass, String dbHost,
            String dbPort, String instance,int cycle) throws Exception {

        Runtime runtime = Runtime.getRuntime();
        // -u后面是用户名，-p是密码
        try {
        Process process = runtime.exec("mysqldump --single-transaction --flush-logs --master-data=2 "
        		+ "-h " + dbHost + " -P "
                + dbPort + " -u " + dbUser + " -p" + dbPass + " --databases"+instance /*+instance*/);
        InputStream inputStream = process.getInputStream();// 得到输入流，写成.sql文件
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        s = sb.toString();
        String now=GetNow.getNowTimestring();
       // int i=0;
        String savePath="F:/backup/"+bid+"_"+now+".sql";
        File file = new File(savePath);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(s.getBytes());
        BackupFile backupFile=new BackupFile();
        backupFile.setBid(Integer.valueOf(bid));
        //backupFile.setfileCreateTime(GetNow.getNowTime());
        //备份时间
        Date date=GetNow.getNowTime();
        //下次备份时间
        java.sql.Date nextTime=new java.sql.Date(date.getTime()+cycle*24*60*60*1000);
        //修改备份计划的上次备份时间和下次备份时间
        backUP.backupPlanMapper.updateTime(nextTime, date,Integer.valueOf(bid));
        System.out.println("xiugai plan");
        backupFile.setFileCreateTime(date);
        backupFile.setFileName(bid+"_"+now+".sql");
        backupFile.setFileUrl("/backup/"+bid+"_"+now+".sql");
        //插入备份文件记录
        backUP.backupFileMapper.insertFile(backupFile);
        System.out.println("charu file");
        OpLog opLog=new OpLog();
        opLog.setBpid(Integer.valueOf(bid));
        opLog.setOperation("成功执行了一次备份");
        opLog.setOpTime(date);
        //插入操作日志
        backUP.opLogMapper.insertOpLog(opLog);
        System.out.println("charu oplog");
        fileOutputStream.close();
        br.close();
        reader.close();
        inputStream.close();  
        }catch (Exception e) {
			// TODO: handle exception 
        	System.err.println("备份失败");
        	OpLog opLog=new OpLog();
        	 opLog.setBpid(Integer.valueOf(bid));
             opLog.setOperation("执行备份失败");
             Date date=GetNow.getNowTime();
             opLog.setOpTime(date);
             backUP.opLogMapper.insertOpLog(opLog);
		}
    }

    /**
     * 执行sql文件
     * 
     * @param savePath
     * @throws Exception
     */
    public static void dbRecover(String dbUser, String dbPass, String dbHost,
            String dbPort,/*String instance,*/ String savePath,String date,String bidUser,String bidPass,String bidHost,
            String bidPort) throws Exception {
    	//先恢复最近一次全备
    	Runtime runtime1 = Runtime.getRuntime();
    	Runtime runtime2=Runtime.getRuntime();
        Process process1 = runtime1.exec("mysql -h" + dbHost + " -P " + dbPort
                + " -u" + dbUser + " -p" + dbPass);
        OutputStream outputStream = process1.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("F:"+savePath),"utf-8"));
        String str = null;
        String logName=null;
        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str + "\r\n");
            if(str.startsWith("-- CHANGE MASTER")) {
             	 logName=str.substring(str.indexOf("'")+1,str.lastIndexOf("'"));
           	 System.out.println(logName);
            }
        }
        str = sb.toString();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                "utf-8");
        writer.write(str);
        writer.flush();
        outputStream.close();
        br.close();
        writer.close();
        System.out.println("全量恢复完成");
        //恢复增量
        //mysqlbinlog -uroot -proot -h172.18.107.20 -P3306 --stop-datetime="2018-07-04 17:03:00" --read-from-remote-server binlog.000002 | mysql -uroot -proot -h 172.18.107.20
        String cmds="mysqlbinlog -u"+bidUser+" -p"+bidPass+" -h"+bidHost+" -P"+bidPort
        		+" --stop-datetime=\""+ date+"\" --read-from-remote-server "+logName+ 
        		" | mysql"+" -h" + dbHost + " -P" + dbPort
                + " -u " + dbUser + " -p" + dbPass; 
        	String[] command = { "cmd", "/c", cmds};
        	System.out.println("开始恢复增量");
        runtime2.exec(command);
        System.out.println("增量恢复完成");
    }

  /*  public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();//获取当前时间
       try {
            dbBackup("21","root", "root", "172.18.107.20","3306", " backuptest test");
            System.out.println("逻辑备份完成！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
      try {
        //  dbRecover("root", "root", "172.18.107.20","3306", "/backup/21_2018-07-04.sql","2018-7-4 22：47");
            System.out.println("还原完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
    	System.out.println("程序运行时间："+(endTime-startTime)+"ms");
    }*/
}

