package com.hes.lis.mltd.process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;

import com.hes.lis.mltd.utils.CommunicationUtils;
import com.hes.lis.mltd.utils.HexConvertionClassUtils;
import com.hes.lis.mltd.utils.LogUtils;
import com.hes.lis.mltd.utils.Utils;


/**
 * 
 * @author psvr
 * 
 */
public class MeterSettings {
	
	private static CommunicationUtils tcpCommObj=new CommunicationUtils();
	
	public boolean relayDisconnect(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		boolean isRelayDisconnected=false;
		
		try{   													   
			String command="00 01 00 01 00 01 00 0F C3 01 C1 00 46 00 00 60 03 0A FF 01 01 0F 00";//00.00.96.03.10.255

			Integer trailCount = 0;
			for(Integer i=0;i<5;i++){
				try {
					tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
					String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
					LogUtils.appendLogger(logger, "RELAY DISCONNECT RESPONSE  : "+responseData);
					String[] responseArr=responseData.split("\\ ");
					if(responseArr[11].equalsIgnoreCase("00")){
						isRelayDisconnected=true;
						LogUtils.appendLogger(logger,"RELAY DISCONNECT SUCCESS");
						Utils.updateLoggerDB(meterNumber, "RELAY DISCONNECT", "SUCCESS");
						break;
					}else{
						trailCount++;
						LogUtils.appendLogger(logger,"RELAY DISCONNECT FAILED");
						Utils.updateLoggerDB(meterNumber, "RELAY DISCONNECT", "FAILED");
					}
					
					if (trailCount > 4) {
						break;
					}
				} catch (Exception e) {
					trailCount++;
				}
			}
			
			
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"EXCEPTION CAUGHT IN RELAY DISCONNECT"+e.getMessage());
			throw e;
		}
		return isRelayDisconnected;
	}
	
	public boolean relayReConnect(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds,  String meterNumber,LogUtils logger) throws Exception{
		boolean isRelayConnected=false;;
		
		try{   													   
			String command="00 01 00 01 00 01 00 0F C3 01 C1 00 46 00 00 60 03 0A FF 02 01 0F 00";//00.00.96.03.10.255
			
			Integer trailCount = 0;
			for(Integer i=0;i<5;i++){
				try {
					tcpCommObj.sendBytes(toClient,sleepSeconds, command,logger);
					String responseData=tcpCommObj.receiveBytes(in,sleepSeconds,meterNumber,logger);
					LogUtils.appendLogger(logger, "RELAY CONNECT RESPONSE  : "+responseData);
					String[] responseArr=responseData.split("\\ ");
					if(responseArr[11].equalsIgnoreCase("00")){
						isRelayConnected=true;
						LogUtils.appendLogger(logger,"RELAY CONNECT SUCCESS");
						Utils.updateLoggerDB(meterNumber, "RELAY CONNECT", "SUCCESS");
						break;
					}else{
						trailCount++;
						LogUtils.appendLogger(logger,"RELAY CONNECT FAILED");
						Utils.updateLoggerDB(meterNumber, "RELAY CONNECT", "FAILED");
					}
					
					if (trailCount > 4) {
						break;
					}
				} catch (Exception e) {
					trailCount++;
				}
			}
			
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"EXCEPTION CAUGHT IN RELAY CONNECT"+e.getMessage());
			throw e;
		}
		return isRelayConnected;
	}
	
	
	public boolean setServerDateTimeToMeter(DataInputStream in,
			DataOutputStream toClient, Integer sleepSeconds, LogUtils logger,
			String meterNumber, Date nepaliDate,Integer dayOfweek) {
		boolean setStatus=false;

		try{
			
			String dateCmdStr=HexConvertionClassUtils.dateTimeHexBytesByDate(nepaliDate,dayOfweek);
			String command="00 01 00 01 00 01 00 1B C1 01 C1 00 08 00 00 01 00 00 FF 02 00 "+dateCmdStr;
			
			Integer trailCount = 0;
			for(Integer i=0;i<5;i++){
				try {
					tcpCommObj.sendBytes(toClient,  sleepSeconds, command,logger);
					String response=tcpCommObj.receiveBytes(in, sleepSeconds,meterNumber,logger);
					LogUtils.appendLogger(logger,"setServerDateTimeToMeter :: "+response+" ");
					String[] responseArr=response.split("\\ ");

					if(responseArr[11].equalsIgnoreCase("00")){
						setStatus=true;
						LogUtils.appendLogger(logger,"setServerDateTimeToMeter :: "+response+" ");
						break;
					}else{
						trailCount++;
						LogUtils.appendLogger(logger,"setServerDateTimeToMeter NCK");
					}
					
					if (trailCount > 4) {
						break;
					}
				} catch (Exception e) {
					trailCount++;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return setStatus;
	}

}
