package com.hes.lis.mltd.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;


/**
 * 
 * @author psvr
 *
 */
public class LogUtils {
	
	private File logFile;

	public File getLogFile() {
		return logFile;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public LogUtils(File logFile) {
		super();
		this.logFile = logFile;
	}
	
	public static void appendLogger(LogUtils logger,String logStr){
		try{
//			logger.info(new Date()+" "+logStr);
//			logger.info(logStr);
			String dateStr="("+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:S").format(new Date())+")==>";
			System.out.println(dateStr+" "+logStr);
			FileUtils.writeStringToFile(logger.getLogFile(), dateStr+"   "+logStr+"\n",true);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
