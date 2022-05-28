package com.hes.lis.mltd.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author psvr
 *
 */
public class DateUtils {
	
	public static Integer diffInMinutes(Date toDateTime,Date fromDatetime) {
		Integer minutes=0;
		
		try {
			long diff = fromDatetime.getTime() - toDateTime.getTime();//as given
			minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diff);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return minutes;
	}

	public static void main(String[] args) {

		try {
			Timestamp fromDatetime=new Timestamp(new Date().getTime());
			Timestamp toDatetime=new Timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("04-11-21 08:02:57").getTime());

			System.out.println(new DateUtils().diffInMinutes(toDatetime, fromDatetime));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
