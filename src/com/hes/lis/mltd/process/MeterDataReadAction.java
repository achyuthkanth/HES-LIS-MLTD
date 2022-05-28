package com.hes.lis.mltd.process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.hes.lis.mltd.utils.CommunicationUtils;
import com.hes.lis.mltd.utils.LogUtils;
import com.hes.lis.mltd.utils.Utils;


/**
 * 
 * @author psvr
 *
 */
public class MeterDataReadAction {

	private static CommunicationUtils tcpCommObj =new CommunicationUtils();
	
	public boolean instantDataReading(DataInputStream in,DataOutputStream toClient,Integer sleepSeconds,
			String meterNumber,LogUtils logger,Date fromDate,Date toDate,
			String requestType,String requestId,File instantDir){
		
		boolean isInstantCompleted=false;

		try{
			LogUtils.appendLogger(logger,"(INSTANT DATA READING STARTED).");
			LogUtils.appendLogger(logger,"(FROM DATE : "+fromDate+" TO DATE : "+toDate+").");
			
			String scalarHeader="00 01 00 01 00 01 01 F9 C4 01 C1 01 00 00 00 01 00 82 01 e8 01 1B 02 04 12 00 03 09 06 01 00 20 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 34 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 48 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 1F 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 33 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 47 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0D 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 21 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 35 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 49 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 51 07 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 51 07 0F FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 51 07 1A FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0E 07 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 00 00 60 07 88 FF 0F 02 12 00 00 02 04 12 00 03 FF FF";
			String scalarData="00 01 00 01 00 01 00 B0 C4 02 C1 01 00 00 00 01 00 82 00 e8 01 1B 02 02 0F FF 16 23 02 02 0F FF 16 23 02 02 0F FF 16 23 02 02 0F FE 16 21 02 02 0F FE 16 21 02 02 0F FE 16 21 02 02 0F 00 16 1B 02 02 0F 00 16 1B 02 02 0F 00 16 1C 02 02 0F 00 16 1C 02 02 0F 00 16 1D 02 02 0F 00 16 1D 02 02 0F FD 16 FF 02 02 0F FD 16 FF 02 02 0F FD 16 FF 02 02 0F FD 16 FF 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 00 16 08 02 02 0F 00 16 08 02 02 0F 00 16 08 02 02 0F FE 16 2C 02 02 0F 00 16 07 FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 80 00 FF 03 00";//Profile 1
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			
    		String obisDataCommand="00 01 00 01 00 01 00 40 C0 01 C1 00 07 01 00 63 80 00 FF 02 01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 FF 0F 02 12 00 00 ";
    		
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(fromDate);
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(toDate);
			obisDataCommand=obisDataCommand+" 01 00";
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			StringBuilder fileStrb=new StringBuilder();
			
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");
			
			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			String fileName=meterNumber+"_INSTANT_"+requestId+".MD";
			
			File inputFile=new File(instantDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);
			
			isInstantCompleted=true;
			if(isInstantCompleted){
				LogUtils.appendLogger(logger,"INSTANT DATA FILE IS :: "+inputFile.getAbsolutePath()+" ");
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				Utils.updateLoggerDB(meterNumber, "INSTANT DATA READING", "SUCCESS file :"+inputFile.getAbsolutePath());
			}
			
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"INSTANT DATA REAING EXCEPTION :: "+e.getMessage()+" ");
			e.printStackTrace();
		}
		
		return isInstantCompleted;
	}
	
	
	
	public boolean fetchBillingData(DataInputStream in,
			DataOutputStream toClient, Integer sleepSeconds,
			String meterNumber, LogUtils logger, String requestType,
			String requestId, File billingDir) {
		boolean isDataCompleted=false;
		try{
			LogUtils.appendLogger(logger,"BILLING DATA STARTED READING");
			
			String scalarHeader="00 01 00 01 00 01 03 80 C4 02 C1 01 00 00 00 01 00 82 01 e8 01 31 02 04 12 00 08 09 06 01 00 01 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 06 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 15 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 16 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 17 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 18 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 29 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 2A 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 2B 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 2C 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3D 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3E 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3F 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 40 08 00 FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 01 34 C4 02 C1 01 00 00 00 01 00 82 00 e8 01 31 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 00 16 1B 02 02 0F 00 16 1B 02 02 0F 00 16 1B 02 02 0F 00 16 1B 02 02 0F 00 16 1D 02 02 0F 00 16 1D 02 02 0F 00 16 1D 02 02 0F 00 16 1D 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 00 00 62 01 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			String obisDataCommand="00 01 00 01 00 01 00 0D C0 01 C9 00 07 00 00 62 01 00 FF 02 00";
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			StringBuilder fileStrb=new StringBuilder();

			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");
			
			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			String fileName=meterNumber+"_BILL_"+requestId+".MD";
			
			File inputFile=new File(billingDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);
			
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"BILLING DATA FILE IS  :: "+inputFile.getAbsolutePath()+" ");
				Utils.updateLoggerDB(meterNumber, "BILLING DATA READING", "SUCCESS file :"+inputFile.getAbsolutePath());
			}
			
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"BILLING DATA EXCEPTION :: "+e.getMessage()+" ");
			e.printStackTrace();
		}finally{
			LogUtils.appendLogger(logger,"BILLING DATA STATUS :: "+isDataCompleted+" ");
		}
		
		return isDataCompleted;
	}
	
	
	public boolean readFullLoadData1(DataInputStream in,
			DataOutputStream toClient, Integer sleepSeconds,
			String meterNumber, LogUtils logger, String requestType,
			String requestId,File loadDir) {
		boolean isDataCompleted=false;
		try{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 STARTED READING .");

			String scalarHeader="00 01 00 01 00 01 01 68 C4 01 C1 00 00 00 00 01 00 82 01 14 02 04 12 00 03 09 06 01 00 01 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 15 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 16 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 29 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 2A 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3D 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3E 08 00 FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 00 78 C4 01 C1 00 00 00 00 01 00 82 01 14 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 01 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			
			String obisDataCommand="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 01 00 FF 02 00";
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			StringBuilder fileStrb=new StringBuilder();
			
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");

			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			String fileName=meterNumber+"_LOAD1_"+requestId+".MD";
			
			File inputFile=new File(loadDir,fileName);
			
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);	
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 FILE IS :: "+inputFile.getAbsolutePath()+" ");
				Utils.updateLoggerDB(meterNumber, "LOAD 1 DATA READING", "SUCCESS file :"+inputFile.getAbsolutePath());
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 CAUGHT EXCEPTION :: "+e.getMessage()+" ");
			e.printStackTrace();
		}finally{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 READING STATUS : "+isDataCompleted+" ");
		}
		
		return isDataCompleted;
	}
	
	
	public boolean readFullLoadData7(DataInputStream in,DataOutputStream toClient,Integer sleepSeconds,String meterNumber,LogUtils logger
			,String requestType,String requestId,File loadDir){
		boolean isDataCompleted=false;
		try{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 STARTED READING .");
			
			String scalarHeader="00 01 00 01 00 01 00 78 C4 01 C1 00 00 00 00 01 00 82 01 06 02 04 12 00 03 09 06 01 00 20 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 34 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 48 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 1F 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 33 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 47 18 7C FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 00 30 C4 01 C1 00 00 00 00 01 00 82 01 06 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 88 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			
			String obisDataCommand="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 88 00 FF 02 00";
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			StringBuilder fileStrb=new StringBuilder();
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");

			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			String fileName=meterNumber+"_LOAD7_"+requestId+".MD";
			
			File inputFile=new File(loadDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);	
			
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 FILE IS : "+inputFile.getAbsolutePath()+" ");
				Utils.updateLoggerDB(meterNumber, "LOAD 7 DATA READING", "SUCCESS file :"+inputFile.getAbsolutePath());
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 CAUGHT EXCEPTION :: "+e.getMessage()+" ");
			e.printStackTrace();
		}finally{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 STATUS : "+isDataCompleted+" ");
		}
		
		return isDataCompleted;
	}
	
	
	public boolean readFullLoadData9(DataInputStream in,DataOutputStream toClient,Integer sleepSeconds,String meterNumber,LogUtils logger
			,String requestType,String requestId,File midnightDir){
		boolean isDataCompleted=false;
		try{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 STARTED READING .");
			
			String scalarHeader="00 01 00 01 00 01 00 78 C4 01 C1 00 00 00 00 01 00 82 01 06 02 04 12 00 03 09 06 01 00 01 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 00 FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 00 30 C4 01 C1 00 00 00 00 01 00 82 01 06 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1F FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 02 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			String obisDataCommand="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 02 00 FF 02 00";
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			StringBuilder fileStrb=new StringBuilder();
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");

			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			String fileName=meterNumber+"_LOAD9_"+requestId+".MD";
			
			File inputFile=new File(midnightDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);	
			
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 FILE IS : "+inputFile.getAbsolutePath()+" ");
				Utils.updateLoggerDB(meterNumber, "LOAD 9 DATA READING", "SUCCESS file :"+inputFile.getAbsolutePath());
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 CAUGHT EXCEPTION :: "+e.getMessage()+" ");
			e.printStackTrace();
		}finally{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 STATUS : "+isDataCompleted+" ");
		}
		
		return isDataCompleted;
	}
	
	public boolean fetchLoadDataFromMeterFromTo1(DataInputStream in,
			DataOutputStream toClient, Integer sleepSeconds,
			String meterNumber, Date fromDate, Date toDate,LogUtils logger,String requestType,String requestId,File loadDir) throws Exception {
		boolean isDataCompleted=false;

		try{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 FROM : "+fromDate+" TO : "+toDate+" STARTED READING.");
			
			String scalarHeader="00 01 00 01 00 01 01 68 C4 01 C1 00 00 00 00 01 00 82 01 14 02 04 12 00 03 09 06 01 00 01 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 01 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 01 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 02 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 03 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 04 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 15 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 16 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 29 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 2A 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3D 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 3E 08 00 FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 00 78 C4 01 C1 00 00 00 00 01 00 82 01 14 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1F 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 1E FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 01 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			
			String obisDataCommand="00 01 00 01 00 01 00 40 C0 01 C1 00 07 01 00 63 01 00 FF 02 01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 FF 0F 02 12 00 00";
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(fromDate);
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(toDate);
			obisDataCommand=obisDataCommand+" 01 00";
			
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			StringBuilder fileStrb=new StringBuilder();
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");

			String fileName=meterNumber+"_LOAD1_"+requestId+"_"+fromDate+"_"+toDate+".MD";
			File inputFile=new File(loadDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);		
			
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 FROM : "+fromDate+" TO : "+toDate+" FILE IS :: "+inputFile.getAbsolutePath()+" ");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 FROM : "+fromDate+" TO : "+toDate+" CAUGHT EXCEPTION ,METER : "+meterNumber+" error :: "+e.getMessage());
			throw e;
		}finally{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 1 FROM : "+fromDate+" TO : "+toDate+" METER :: "+meterNumber+" status :: "+isDataCompleted);
		}

		return isDataCompleted;
	}
	
	public boolean fetchLoadDataFromMeterFromTo7(DataInputStream in,
			DataOutputStream toClient, Integer sleepSeconds,
			String meterNumber, Date fromDate, Date toDate,LogUtils logger,String requestType,String requestId,File loadDir) throws Exception {
		boolean isDataCompleted=false;

		try{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 FROM : "+fromDate+" TO : "+toDate+" STARTED READING.");
			
			String scalarHeader="00 01 00 01 00 01 00 78 C4 01 C1 00 00 00 00 01 00 82 01 06 02 04 12 00 03 09 06 01 00 20 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 34 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 48 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 1F 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 33 18 7C FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 47 18 7C FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 00 30 C4 01 C1 00 00 00 00 01 00 82 01 06 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 02 02 0F FF 16 38 FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 88 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			
			String obisDataCommand="00 01 00 01 00 01 00 40 C0 01 C1 00 07 01 00 63 88 00 FF 02 01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 FF 0F 02 12 00 00";
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(fromDate);
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(toDate);
			obisDataCommand=obisDataCommand+" 01 00";
			
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			StringBuilder fileStrb=new StringBuilder();
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");
			
			String fileName=meterNumber+"_LOAD7_"+requestId+"_"+fromDate+"_"+toDate+".MD";
			File inputFile=new File(loadDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);		
			
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 FROM : "+fromDate+" TO : "+toDate+" FILE IS :: "+inputFile.getAbsolutePath()+" ");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 FROM : "+fromDate+" TO : "+toDate+" CAUGHT EXCEPTION ,METER : "+meterNumber+" error :: "+e.getMessage());
			throw e;
		}finally{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 7 FROM : "+fromDate+" TO : "+toDate+" METER :: "+meterNumber+" status :: "+isDataCompleted);
		}

		return isDataCompleted;
	}
	
	public boolean fetchLoadDataFromMeterFromTo9(DataInputStream in,
			DataOutputStream toClient, Integer sleepSeconds,
			String meterNumber, Date fromDate, Date toDate,LogUtils logger,String requestType,String requestId,File midnightDir) throws Exception {
		boolean isDataCompleted=false;

		try{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 FROM : "+fromDate+" TO : "+toDate+" STARTED READING.");
			
			String scalarHeader="00 01 00 01 00 01 00 78 C4 01 C1 00 00 00 00 01 00 82 01 06 02 04 12 00 03 09 06 01 00 01 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 02 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 03 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 04 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 09 08 00 FF 0F 02 12 00 00 02 04 12 00 03 09 06 01 00 0A 08 00 FF 0F 02 12 00 00 FF FF";
			String scalarData="00 01 00 01 00 01 00 30 C4 01 C1 00 00 00 00 01 00 82 01 06 02 02 0F 01 16 1E 02 02 0F 01 16 1E 02 02 0F 01 16 20 02 02 0F 01 16 20 02 02 0F 01 16 1F 02 02 0F 01 16 1F FF FF";
			
			String obisheader="00 01 00 01 00 01 00 0D C0 01 C1 00 07 01 00 63 02 00 FF 03 00";
			List<String> obisheaderList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisheader, meterNumber,logger);
			
			String obisDataCommand="00 01 00 01 00 01 00 40 C0 01 C1 00 07 01 00 63 02 00 FF 02 01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 FF 0F 02 12 00 00";
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(fromDate);
			obisDataCommand=obisDataCommand+Utils.dateTimeHexBytesByDate(toDate);
			obisDataCommand=obisDataCommand+" 01 00";
			List<String> obisDataList=tcpCommObj.receiveCompleteData(in, toClient,  sleepSeconds, obisDataCommand, meterNumber,logger);
			
			if(requestType.equalsIgnoreCase("ON DEMAND")){
				requestId=requestId+"_P";
			}else{
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("ddMMyyyyHHmmssSS");
				requestId=meterNumber+format.format(date)+"_S";
			}
			
			StringBuilder fileStrb=new StringBuilder();
			fileStrb.append("<METERDATA>\n");
			fileStrb.append("<OBISCODES>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisheaderList)+"\n");
			fileStrb.append("</OBISCODES>\n");
			fileStrb.append("<OBISDATA>\n");
			fileStrb.append(Utils.frameDataInRAWFile(obisDataList)+"\n");
			fileStrb.append("</OBISDATA>\n");
			fileStrb.append("<SCALAROBISCODES>\n");
			fileStrb.append(scalarHeader+"\n");
			fileStrb.append("</SCALAROBISCODES>\n");
			fileStrb.append("<SCALAROBISDATA>\n");
			fileStrb.append(scalarData+"\n");
			fileStrb.append("</SCALAROBISDATA>\n");
			fileStrb.append("</METERDATA>");

			String fileName=meterNumber+"_LOAD7_"+requestId+"_"+fromDate+"_"+toDate+".MD";
			File inputFile=new File(midnightDir,fileName);
			FileUtils.writeStringToFile(inputFile, fileStrb.toString(), true);		
			
			isDataCompleted=true;
			if(isDataCompleted){
				File eofFile=new File(inputFile.getAbsolutePath()+".MDE");
				eofFile.createNewFile();
				LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 FROM : "+fromDate+" TO : "+toDate+" FILE IS :: "+inputFile.getAbsolutePath()+" ");
			}
		}catch (Exception e) {
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 FROM : "+fromDate+" TO : "+toDate+" CAUGHT EXCEPTION ,METER : "+meterNumber+" error :: "+e.getMessage());
			throw e;
		}finally{
			LogUtils.appendLogger(logger,"LOAD DATA PROFILE 9 FROM : "+fromDate+" TO : "+toDate+" METER :: "+meterNumber+" status :: "+isDataCompleted);
		}

		return isDataCompleted;
	}
	
}
