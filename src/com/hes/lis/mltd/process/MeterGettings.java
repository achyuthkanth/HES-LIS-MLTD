package com.hes.lis.mltd.process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hes.lis.mltd.utils.CommunicationUtils;
import com.hes.lis.mltd.utils.FrameUtils;
import com.hes.lis.mltd.utils.LogUtils;
import com.hes.lis.mltd.utils.Utils;

/**
 * 
 * @author psvr
 *
 */
public class MeterGettings {
	
	private static CommunicationUtils tcpCommObj =new CommunicationUtils();

	public String readMeterNumber(DataInputStream in,DataOutputStream toClient,Integer sleepSeconds,LogUtils logger){
		String meterNumber="";
		
		try{
			try{
				String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 00 FF 02 00";
				tcpCommObj.sendBytes(toClient,  sleepSeconds, command,logger);
				String response=tcpCommObj.receiveBytes(in, sleepSeconds,meterNumber,logger);
				String[] responseArr=response.split("\\ ");

				if(responseArr[11].equalsIgnoreCase("00")){
					meterNumber=FrameUtils.fetchStringFromResponse(response);
					LogUtils.appendLogger(logger,"(METER NUMBER) : "+response+" ");
				}else{
					LogUtils.appendLogger(logger,"(FETCHING METER NUMBER FAILED)");
				}
			}catch (Exception e) {
				LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN FETCHING EXCEPTION)  "+e.getMessage());
				meterNumber=null;
//				e.printStackTrace();
			}finally{
				LogUtils.appendLogger(logger,"(METER NUMBER) : "+meterNumber);
			}

		}catch (Exception e) {
			throw e;
		}
		
		return meterNumber;
	}
	
	public Date fetchRTCFromMeter(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		Date meterDate=null;
		try{   													   
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 08 00 00 01 00 00 FF 02 00";//00.00.01.00.00.255
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			LogUtils.appendLogger(logger,"(FETCH DATE TIME FROM METER) : "+responseData);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				//	09 0c 08 1c 05 03 02 0f 1e 38 00 fe a7 00			
				Integer year=Integer.parseInt(responseArr[14]+responseArr[15],16);
				Integer month=Integer.parseInt(responseArr[16],16);
				Integer day=Integer.parseInt(responseArr[17],16);
				
				Integer hour=Integer.parseInt(responseArr[19],16);
				Integer minutes=Integer.parseInt(responseArr[20],16);
				
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
				
				meterDate=dateFormat.parse(StringUtils.leftPad(year+"", 4, "0")+
											StringUtils.leftPad((month)+"", 2, "0")+
											StringUtils.leftPad((day)+"", 2, "0")+
											StringUtils.leftPad(hour+"", 2, "0")+
											StringUtils.leftPad(minutes+"", 2, "0"));
				System.out.println(meterDate);
				LogUtils.appendLogger(logger,"(DATE TIME FROM METER) : "+meterDate);
				
				Utils.updateLoggerDB(meterNumber, "(DATE TIME)", meterDate+"");
				
			}else{
				LogUtils.appendLogger(logger,"(FETCH DATE TIME FROM METER FAILED) ");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(FETCH DATE TIME FROM METER EXCEPTION CAUGHT) "+e.getMessage());
			throw e;
		}
		return meterDate;
	}
	
	public String getSignalStrength(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		String response=null;
		
		try{   													   
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 81 19 00 00 FF 02 00";//00.129.25.00.00.255
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseArr[13];
				response=Integer.parseInt(response,16)+"";
//				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(SIGNAL STRENGTH FROM METER) : "+response);
				Utils.updateLoggerDB(meterNumber, "SIGNAL STRENGTH", response+"");
			}else{
				LogUtils.appendLogger(logger,"(FAILED TO FETCH SIGNALSTRENGTH FROM METER)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION IN FETCHING SIGNAL STRENGTH) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String getMeterType(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger
			) throws Exception{
		String response=null;
		try{
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 89 FF 02 00";
			tcpCommObj.sendBytes(toClient,  sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,  sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(METER TYPE) : "+response);
			}else{
				LogUtils.appendLogger(logger,"(METER TYPEGETTING FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING METER TYPE) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String getMeterFirmWareVersion(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger
			) throws Exception{
		String response=null;
		
		try{
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 92 FF 02 00";
			tcpCommObj.sendBytes(toClient,  sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,  sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(METER FIRMWAREVERSION) :  "+response);
				Utils.updateLoggerDB(meterNumber, "METER FIRMWAREVERSION", response+"");
			}else{
				LogUtils.appendLogger(logger,"(GETTING METER FIRMWARE VERSION FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN METER FIRWARE VERSION GETTING) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String getMeterProgramFirmWareVersion(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger
			) throws Exception{
		String response=null;
		
		try{
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 01 01 00 02 00 FF 02 00";
			tcpCommObj.sendBytes(toClient,  sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in, sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(METER PROGRAM VERSION) :  "+response);
				Utils.updateLoggerDB(meterNumber, "METER PROGRAM VERSION", response+"");
			}else{
				LogUtils.appendLogger(logger,"(GETTING METER PROGRAM VERSION FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING METER PROGRAM VERSION) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String getModemFirmWareVersion(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger
			) throws Exception{
		String response=null;
		
		try{
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 8A FF 02 00";
			tcpCommObj.sendBytes(toClient,  sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,  sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(MODEM FIRMWARE VERSION) : "+response);
				Utils.updateLoggerDB(meterNumber, "MODEM FIRMWARE VERSION", response+"");
			}else{
				LogUtils.appendLogger(logger,"(GETTING MODEM FIRMWARE VERSION FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING MODEM FIRMWARE VERSION) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	
	public String getManufactureName(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		String response=null;
		
		try{
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 01 00 00 00 00 FF 02 00";
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(MANUFACTURE NAME) : "+response);
			}else{
				LogUtils.appendLogger(logger,"(GETTING MANUFACTURE NAME FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING MANUFACTURE NAME) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	
	
	public String getSimSerialNumber(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		String response=null;
		
		try{   
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 ED FF 02 00";//00.00.96.01.237.255
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(SIM SERIAL NUMBER) : "+response);
			}else{
				LogUtils.appendLogger(logger,"(GETTING SIM SERIAL NUMBER FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING SIM SERIAL NUMBER) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String getSimDialNumber(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		String response=null;
		
		try{   														
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 F8 FF 02 00";//00.00.96.01.248.255
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(SIM DAIL NUMBER) : "+response);
			}else{
				LogUtils.appendLogger(logger,"(GETTING SIM DAIL NUMBER FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING SIM DAIL NUMBER) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String getModemImeiNumber(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		String response=null;
		
		try{   														
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 01 00 00 60 01 98 FF 02 00";//00.00.96.01.152.255
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			String[] responseArr=responseData.split("\\ ");
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseData;
				response=FrameUtils.fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(MODEM IMEI NUMBER) : "+response);
				Utils.updateLoggerDB(meterNumber, "(MODEM IMEI NUMBER)", response+"");
			}else{
				LogUtils.appendLogger(logger,"(GETTING MODEM IMEI NUMBER FAILED)");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN GETTING MODEM IMEI NUMBER) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String fetchRelayStatus(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		String response=null;
		
		try{   													   
			String command="00 01 00 01 00 01 00 0D C0 01 C1 00 46 00 00 60 03 0A FF 02 00";//00.00.96.03.10.255
			tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
			String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
			LogUtils.appendLogger(logger, "(RELAY STATUS RESPONSE IS)  : "+responseData);
			String[] responseArr=responseData.split("\\ ");
			
			if(responseArr[11].equalsIgnoreCase("00")){
				response=responseArr[13];
//				response=fetchStringFromResponse(response);
				LogUtils.appendLogger(logger,"(RELAY STATUS IS ON)");
				Utils.updateLoggerDB(meterNumber, "RELAY STATUS", "ON");
			}else{
				LogUtils.appendLogger(logger,"(RELAY STATUS IS OFF)");
				Utils.updateLoggerDB(meterNumber, "RELAY STATUS", "OFF");
			}
			
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"(EXCEPTION CAUGHT IN FETCHING RELAY STATUS) : "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	
	public static void main(String[] arg){
		try{
			
			String dateStr="08 1e 06 06 ff 0b 00 00 00 fe a7 00";
			String[] responseArr=dateStr.split(" ");
			Integer year=Integer.parseInt(responseArr[0]+responseArr[1],16);
			Integer month=Integer.parseInt(responseArr[2],16);
			Integer day=Integer.parseInt(responseArr[3],16);
			
			Integer hour=Integer.parseInt(responseArr[5],16);
			Integer minutes=Integer.parseInt(responseArr[6],16);
			
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
			
			Date meterDate=dateFormat.parse(StringUtils.leftPad(year+"", 4, "0")+
										StringUtils.leftPad((month)+"", 2, "0")+
										StringUtils.leftPad((day)+"", 2, "0")+
										StringUtils.leftPad(hour+"", 2, "0")+
										StringUtils.leftPad(minutes+"", 2, "0"));
			System.out.println(meterDate);
			
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
