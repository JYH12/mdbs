package com.mdbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetNow {
	public static String getNowDate() {
		java.util.Date date=new java.util.Date();
		//System.out.println(date.getTime());
			java.sql.Date date2=new java.sql.Date(date.getTime());
		return date2.toString();
	}
	public static Date getNowTime() {
		Date date=new Date();
		return date;
	}
	public static Date strToDate(String string) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); //加上时间
        java.util.Date date1=null;
        //必须捕获异常
        
        try {
           date1=simpleDateFormat.parse(string);
           // System.out.println(date1);
        } catch(ParseException px) {
        	px.printStackTrace();
        }
        return date1;
	}
	public static String getNowTimestring() {
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}
	public static void main(String[] args) {
		System.out.println(getNowDate());
	}
}
