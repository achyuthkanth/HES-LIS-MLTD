package com.hes.lis.mltd.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;


/**
 * 
 * @author psvr
 *
 */
public class CommunicationUtils {
	
	public String receiveBytesIgnoreTamperCheck(DataInputStream in,Integer sleepSeconds,String meterNumber,LogUtils logger) throws Exception{
		StringBuilder strb=null;
		String response=null;
		try{
			Integer byteCount=0;
			String lengthStr="";
			strb=new StringBuilder();
			
			Integer timeOutSeconds=0;
			
			while(true){
				if(in.available() > 0){
					timeOutSeconds=0;
					if((byteCount==8)){
						break;
					}
					
					if((byteCount==6)||(byteCount==7)){
						byte byteData = in.readByte();
						strb.append(HexToStringUtils.hexToStringByte(byteData)+" ");
						lengthStr+=HexToStringUtils.hexToStringByte(byteData);
					}else{
						byte byteData = in.readByte();
						strb.append(HexToStringUtils.hexToStringByte(byteData)+" ");
					}
					
					byteCount++;
				}else{
					Thread.sleep(500);
					if(timeOutSeconds>=40){
						lengthStr="";
						break;
					}
					timeOutSeconds=timeOutSeconds+1;
				}
			}

			boolean isSuccessfullData=false;
			
			if(!lengthStr.equalsIgnoreCase("")){
				Integer dataBytesLength=Integer.parseInt(lengthStr, 16);
				
				byteCount=0;
				timeOutSeconds=0;
				
				while(true){
					if(byteCount<dataBytesLength){
						if(in.available() > 0){
							timeOutSeconds=0;
							byte byteData = in.readByte();
							strb.append(HexToStringUtils.hexToStringByte(byteData)+" ");
							byteCount++;
						}else{
							Thread.sleep(500);
							if(timeOutSeconds>=40){
								break;
							}
							timeOutSeconds=timeOutSeconds+1;
						}
					}else{
						isSuccessfullData=true;
						break;
					}
				}
			}
			
			System.out.println("RESPONSE :: "+strb.toString());
			
			if(!isSuccessfullData){
				strb=null;
				try
		        { 
					LogUtils.appendLogger(logger, "TIME OUT.BYTES NOT RECEIVED.");
		            throw new NullPointerException("TIME OUT.BYTES NOT RECEIVED."); 
		        } 
		        catch(NullPointerException e) 
		        { 
		            throw e; // rethrowing the exception 
		        } 
				
			}else{
				response=strb.toString();
				System.out.println("RESPONSE::::======>>>>>"+response);
			}
		}catch (Exception e) {
			strb=new StringBuilder();
			throw e;
		}

		return response;
	}
	
	public byte[] readAllBytesByteArray(DataInputStream in,StringBuilder logStrb,Integer sleepSeconds) throws Exception{
		byte[] responseArr=null; 
		ByteArrayOutputStream outStream=null;
		try{
			Integer timeCount=0;
			boolean isStarted=false;
			while (true) {
				if(in.available() > 0){
					if(!isStarted){
						isStarted=true;
						outStream=new ByteArrayOutputStream();
					}
					byte byteData=in.readByte();
					System.out.println(HexToStringUtils.hexToStringByte(byteData));
					outStream.write(byteData);
				}else{
					Thread.sleep(500);
					if(timeCount>=20){
						break;
					}
					timeCount=timeCount+1;
				}
			}
			
			if((outStream!=null)&&(outStream.size()>0)){
				responseArr=outStream.toByteArray();
			}

		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally{
			if(outStream!=null){
				outStream.close();
			}
		}
		return responseArr;
	}
	
	public String readAllBytes(DataInputStream in,StringBuilder logStrb,Integer sleepSeconds) throws Exception{
		StringBuilder strb=new StringBuilder();
		try{
			Integer timeCount=0;
			while (true) {
				if(in.available() > 0){
					timeCount=0;
					byte byteData = in.readByte();
					strb.append(HexToStringUtils.hexToStringByte(byteData));
				}else{
					Thread.sleep(500);
					if(timeCount>=10){
						break;
					}
					timeCount=timeCount+1;
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			strb=new StringBuilder();
			throw e;
		} 
		return strb.toString();
	}
	
	public String receiveBytes(DataInputStream in,Integer sleepSeconds,File logFile,String meterNumber,LogUtils logger) throws Exception{
		String response="";
		try{
			response=receiveBytes(in, sleepSeconds,meterNumber,logger);
			writeLog(logFile, "RESPONSE :: "+response);
		}catch (Exception e) {
			LogUtils.appendLogger(logger, " TIME OUT.BYTES NOT RECEIVED :: "+e.getMessage());
			throw e;
		}
		return response;
	}
	
	public String receiveBytes(DataInputStream in,Integer sleepSeconds,String meterNumber,LogUtils logger) throws Exception{
		StringBuilder strb=null;
		String response=null;
		try{
			Integer byteCount=0;
			String lengthStr="";
			strb=new StringBuilder();
			
			Integer timeOutSeconds=0;
			
			while(true){
				if(in.available() > 0){
					timeOutSeconds=0;
					if((byteCount==8)){
						break;
					}
					if((byteCount==6)||(byteCount==7)){
						byte byteData = in.readByte();
						strb.append(HexToStringUtils.hexToStringByte(byteData)+" ");
						lengthStr+=HexToStringUtils.hexToStringByte(byteData);
					}else{
						byte byteData = in.readByte();
						strb.append(HexToStringUtils.hexToStringByte(byteData)+" ");
					}
					byteCount++;
				}else{
					Thread.sleep(500);
					if(timeOutSeconds>=40){
						lengthStr="";
						break;
					}
					timeOutSeconds=timeOutSeconds+1;
				}
			}

			boolean isSuccessfullData=false;
			
			if(!lengthStr.equalsIgnoreCase("")){
				Integer dataBytesLength=Integer.parseInt(lengthStr, 16);
				
				byteCount=0;
				timeOutSeconds=0;
				
				while(true){
					if(byteCount<dataBytesLength){
						if(in.available() > 0){
							timeOutSeconds=0;
							byte byteData = in.readByte();
							strb.append(HexToStringUtils.hexToStringByte(byteData)+" ");
							byteCount++;
						}else{
							Thread.sleep(500);
							if(timeOutSeconds>=40){
								break;
							}
							timeOutSeconds=timeOutSeconds+1;
						}
					}else{
						isSuccessfullData=true;
						break;
					}
				}
			}
			
			LogUtils.appendLogger(logger, "OUT CMD :: "+strb.toString());
			
			if(!isSuccessfullData){
				strb=null;
				LogUtils.appendLogger(logger, "TIMEOUT.INCOMPLETE DATA");
				
				try
		        { 
		            throw new NullPointerException("TIME OUT.BYTES NOT RECEIVED"); 
		        } 
		        catch(NullPointerException e) 
		        { 
		            throw e; // rethrowing the exception 
		        } 
				
			}else{
				response=strb.toString();
				String[] strArr=response.split(" ");
				
				if(strArr[5].equalsIgnoreCase("00")){
					//IGNORE
				}else if(!strArr[5].equalsIgnoreCase("01")){
					LogUtils.appendLogger(logger, "PUSH DATA :: "+response);
					response=receiveBytes(in,  sleepSeconds,meterNumber,logger);
					processTampersdata(response, meterNumber);
				}
			}
		}catch (Exception e) {
			strb=new StringBuilder();
			LogUtils.appendLogger(logger, " EXCEPTION CAUGHT IN FETCHING DATA :: "+e.getMessage());
			throw e;
		}

		return response;
	}
	
	private void writeLog(File logFile,String strLog){
		try{
			FileUtils.writeStringToFile(logFile,new Date()+"  :: "+ strLog+"\n", true);
		}catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	public String sendBytes(DataOutputStream toClient, 
			Integer sleepSeconds, String command,File logFile,LogUtils logger) throws Exception {
		String strb="";
		try{
			strb=sendBytes(toClient,  sleepSeconds, command,logger);
			writeLog(logFile, "------------------------------------------------------------------------------------\nREQUEST :: "+command);
		}catch (Exception e) {
			LogUtils.appendLogger(logger, "sendBytes  Error :: "+e.getMessage());
			throw e;
		} 
		return strb;
	}
	
	@SuppressWarnings("static-access")
	public String sendBytes(DataOutputStream toClient, 
			Integer sleepSeconds, String command,LogUtils logger) throws Exception {
		StringBuilder strb=new StringBuilder();
		HexToStringUtils tcpUtils=new HexToStringUtils();
		
		try{
//			toClient.flush();
//			System.out.println("REQUEST :: "+command);
			LogUtils.appendLogger(logger, "INP CMD :: "+command);
			toClient.write(tcpUtils.hexStringToByteArray(command));
		}catch (Exception e) {
			LogUtils.appendLogger(logger, "sendBytes  Error :: "+e.getMessage());
//			e.printStackTrace();
			strb=new StringBuilder();
			throw e;
		} 
		
		return strb.toString();
	}
	
	public List<String> receiveCompleteData(DataInputStream in,
			DataOutputStream toClient, 
			Integer sleepSeconds, String command, String meterNumber,LogUtils logger) throws Exception {
		List<String> completeDataList=new ArrayList<String>();
		CommunicationUtils tcpCommObj=new CommunicationUtils();
		try{
			tcpCommObj.sendBytes(toClient, sleepSeconds, command,logger);
			String response=tcpCommObj.receiveBytes(in, sleepSeconds,meterNumber,logger);
			if(response!=null){
				String[] responseArr=response.split("\\ ");
				if((responseArr[11].equalsIgnoreCase("00"))||(responseArr[11].equalsIgnoreCase("01"))){
					completeDataList.add(response);
					if((responseArr[9].equalsIgnoreCase("02"))&&(responseArr[11].equalsIgnoreCase("00"))){
						while(true){
							command="00 01 00 01 00 01 00 07 C0 02 C1 "+responseArr[12]+" "+responseArr[13]+" "+responseArr[14]+" "+responseArr[15];
							tcpCommObj.sendBytes(toClient, sleepSeconds, command,logger);
							Thread.sleep(1000);
							response=tcpCommObj.receiveBytes(in, sleepSeconds,meterNumber,logger);
							completeDataList.add(response);
							responseArr=response.split("\\ ");
							if(responseArr[11].equalsIgnoreCase("01")){
								break;
							}
						}
					}
					
				}else{
					completeDataList=null;
					LogUtils.appendLogger(logger," Error in pulling data from meter in receiveCompleteData "+meterNumber+" error :: No Proper Data received");
				}
				
			}else{
				completeDataList=null;
				LogUtils.appendLogger(logger,"Error in pulling data from meter in receiveCompleteData "+meterNumber+" error :: No Proper Data received");
			}
			
		}catch (Exception e) {
			completeDataList=null;
			LogUtils.appendLogger(logger," Error in pulling data from meter in receiveCompleteData "+meterNumber+" error :: "+e.getMessage());
			throw e;
		}finally{
		}
		
		return completeDataList;
	}
	
	public boolean processTampersdata(String tampersData,String meterNumber){
		boolean isTamperProcessed=false;
		
		try{
			File folder=new File("/smartmeter/tampers/");
			if(!folder.exists()){
				folder.mkdirs();
			}
			
			String dateSTr=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			if((meterNumber!=null)&&((meterNumber+"").trim().length()>0)){
				
				folder=new File("/smartmeter/tampers/"+meterNumber);
				if(!folder.exists()){
					folder.mkdirs();
				}
			}else{
				meterNumber="NO_METER_NUMBER";
			}
			
			File tamperLog=new File(folder,meterNumber+"_"+dateSTr+".log");
			tampersData=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())+"|"+meterNumber+"|"+tampersData+"\n";
			
			System.out.println("TAMPERS :::+++===>>> "+tampersData);
			FileUtils.writeStringToFile(tamperLog, tampersData,true);
			
			String[] responseArr=tampersData.split("\\ ");
			if(tampersData.contains("00 01 00 01 00 00 00 03 01 11")){
				/*String signalStrength=responseArr[responseArr.length-1];*/
				//IGNORE FOR SIGNAL STRENGTH
			}else{
				String obisCode=responseArr[26]+responseArr[27]+responseArr[28]+responseArr[29]+responseArr[30]+responseArr[31];
				if(obisCode.equalsIgnoreCase("0000600100FF")){
					/*Integer dataSize=Integer.parseInt(responseArr[33],16);
					String mtrNum="";
					for(Integer i=34;i<34+dataSize;i++){
						mtrNum+=responseArr[i];
					}
					String meterNum=HexToStringUtils.unHex(mtrNum);*/
					//IGNORE FOR METER NUMBER
				}else{
					
				}
			}
			
			
		}catch (Exception e) {
//			e.printStackTrace();
		}
		
		return isTamperProcessed;
	}
	
	
	
}
