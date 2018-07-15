package com.mdbs.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class StringToDate {
	/*
	 * 字符串格式日期加减
	 */
	public static String strDateAdd(String strDate,long cycle) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); //加上时间
        java.util.Date date1=null;
        //必须捕获异常
        
        try {
           date1=simpleDateFormat.parse(strDate);
           // System.out.println(date1);
        } catch(ParseException px) {
        	px.printStackTrace();
        }
        Date date2=new Date(date1.getTime()+cycle*60*60*24*1000);
		return date2.toString();
	}
	public static void main(String[] args) {
	java.util.Date date=new java.util.Date();
	/*
		 SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		 System.out.println(sDateFormat.format(date));
		System.out.println(strDateAdd(sDateFormat.format(date), 4));*/
		 Date date1=new Date(date.getTime()+24*60*60*1000);
		 String key=date1.toString();
		 System.out.println(key);
	}
}
