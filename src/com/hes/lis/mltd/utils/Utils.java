package com.hes.lis.mltd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.hes.lis.mltd.dao.MeterRuleEngineDAO;
import com.hes.lis.mltd.vo.MeterLogger;
import com.hes.lis.mltd.vo.MeterLoggerId;

/**
 * 
 * @author psvr
 * 
 */
public class Utils {
	
	public Properties readPortForTcpListener(){
		Properties prop = null;
		try {
			String workingDir = System.getProperty("user.dir");
			prop = new Properties();
			File configFilePath=new File(workingDir,"config.properties");
			if(!configFilePath.exists()){
				configFilePath=new File("/smartmeter/config.properties");
			}
			
			FileInputStream propFileInputStream = new FileInputStream(configFilePath.getAbsolutePath());
			prop.load(propFileInputStream);
			propFileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	
	public Properties readProperties(String fileName){
		Properties prop = null;
		try {
			String workingDir = System.getProperty("user.dir");
			prop = new Properties();
			File configFilePath=new File(workingDir,fileName);
			if(!configFilePath.exists()){
				configFilePath=new File("/smartmeter/"+fileName);
			}
			
			FileInputStream propFileInputStream = new FileInputStream(configFilePath.getAbsolutePath());
			prop.load(propFileInputStream);
			propFileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	
	public Integer readSleepSeconds(){
		Integer sleepSeconds=null;

		Properties prop = new Properties();
		try {
			String workingDir = System.getProperty("user.dir");
			prop = new Properties();
			File configFilePath=new File(workingDir,"config.properties");
			if(!configFilePath.exists()){
				configFilePath=new File("/smartmeter/config.properties");
			}
			
			FileInputStream propFileInputStream = new FileInputStream(configFilePath.getAbsolutePath());
			prop.load(propFileInputStream);
			propFileInputStream.close();
			String sleepTimeInSeconds = prop.getProperty("sleepInSeconds");
			sleepSeconds=Integer.parseInt(sleepTimeInSeconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sleepSeconds;
	}
	
	public Date convertEnglishToNepali(Date indianDate){
		Date nepaliDate=indianDate;
		try{
			SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");		
			
    		NepaliDateUtils toDateConverter = new NepaliDateUtils();
    		toDateConverter.setCurrentDate(indianDate);
    		String toDateStr=toDateConverter.getNepaliYear()+"-"+(StringUtils.leftPad(toDateConverter.getNepaliMonth()+"", 2, "0"))+"-"+toDateConverter.getNepaliDate()+" "+timeFormat.format(indianDate);
    		nepaliDate=dateTimeFormat.parse(toDateStr);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return nepaliDate;
	}
	
	
	public Date convertNepaliToEnglish(Date nepaliDate){
		Date istDate=nepaliDate;
		try{
			SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");		
			
    		NepaliDateUtils toDateConverter = new NepaliDateUtils();
    		toDateConverter.setCurrentDate(nepaliDate);
    		String toDateStr=toDateConverter.getNepaliYear()+"-"+(StringUtils.leftPad(toDateConverter.getNepaliMonth()+"", 2, "0"))+"-"+toDateConverter.getNepaliDate()+" "+timeFormat.format("");
    		nepaliDate=dateTimeFormat.parse(toDateStr);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return nepaliDate;
	}
	
	
	public static String dateTimeHexBytesByDate(Date date){
		String strDate="";
		Calendar currentDate = Calendar.getInstance();
    	currentDate.setTime(date);

    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.YEAR)), 4,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.MONTH)+1), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.DAY_OF_MONTH)), 2,"0")+" ";
    	strDate=strDate+"FF ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.HOUR_OF_DAY)), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.MINUTE)), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.SECOND)), 2,"0")+" ";
    	strDate=strDate+"FF 80 00 FF";
		return " 09 0c "+strDate;
	}
	
	public static String frameDataInTags(String tagName,List<String> dataList){
		StringBuilder strb=null;
		try{
			if(dataList.size()>0){
				strb=new StringBuilder();
				strb.append("<"+tagName+">");
				for(int i=0;i<dataList.size();i++){
					String strElement=dataList.get(i);
//					strElement=strElement.replace(" ", "").toLowerCase();
					
					String dataString=strElement.toLowerCase()+"ff ff ";
					String[] strArr=dataString.split(" ");
					Integer sizeOfFrame=strArr.length-8;
					String[] sizeArr=HexConvertionClassUtils.fetchSixeInHex(sizeOfFrame);
					strArr[6]=sizeArr[0];
					strArr[7]=sizeArr[1];
					
					for(String str:strArr){
						strb.append(str);
					}
					
					if(i!=dataList.size()-1){
						strb.append("\n");
					}
					
					
					/*if(i!=dataList.size()-1){
						strb.append(strElement);
					}else{
						strb.append(strElement);
					}*/
				}
				strb.append("</"+tagName+">");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strb.toString();
	}
	
	
	public static String frameDataInRAWFile(List<String> dataList){
		StringBuilder strb=null;
		try{
			if(dataList.size()>0){
				strb=new StringBuilder();
				for(int i=0;i<dataList.size();i++){
					String strElement=dataList.get(i);
//					strElement=strElement.replace(" ", "").toLowerCase();
					
					String dataString=strElement.toLowerCase()+"ff ff ";
					String[] strArr=dataString.split(" ");
					Integer sizeOfFrame=strArr.length-8;
					String[] sizeArr=HexConvertionClassUtils.fetchSixeInHex(sizeOfFrame);
					strArr[6]=sizeArr[0];
					strArr[7]=sizeArr[1];
					
					for(String str:strArr){
						strb.append(str+" ");
					}
					
					if(i!=dataList.size()-1){
						strb.append("\n");
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strb.toString();
	}
	
	public static String frameDataStringInTags(String tagName,String dataStr){
		StringBuilder strb=new StringBuilder();
		try{
			if(dataStr!=null&&dataStr.length()>0){
				strb=new StringBuilder();
				strb.append("<"+tagName+">");
				
				String dataString=dataStr.toLowerCase()+"ff ff ";
				String[] strArr=dataString.split(" ");
				Integer sizeOfFrame=strArr.length-8;
				String[] sizeArr=HexConvertionClassUtils.fetchSixeInHex(sizeOfFrame);
				strArr[6]=sizeArr[0];
				strArr[7]=sizeArr[1];
				
				for(String str:strArr){
					strb.append(str);
				}
				
//				strb.append(dataStr.toLowerCase().replace(" ", ""));
				strb.append("</"+tagName+">");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strb.toString();
	}
	
	
	
	public static  void updateLoggerDB(String meterSerialNumber,String commPurpose,String commDetails){
		
		try{
			
			MeterLogger logger=new MeterLogger();
			
			MeterLoggerId meterLogId=new MeterLoggerId();
			meterLogId.setCommDatetime(new Timestamp(new Date().getTime()));
			meterLogId.setCommPurpose(commPurpose);
			meterLogId.setMeterSerialNumber(meterSerialNumber);
			
			logger.setId(meterLogId);
			logger.setCommDetails(commDetails);
			
			new MeterRuleEngineDAO().saveObject(logger);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] arg){
		try{
			Utils.updateLoggerDB("123", "qeewqewqewqe", "4321421132132324324324324 432443244 4444 4 32 432 44 4 24 24 4 4 4 432 4 1");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


}
