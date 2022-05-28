package com.hes.lis.mltd.process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.Timestamp;
import java.util.Date;

import com.hes.lis.mltd.dao.MeterRuleEngineDAO;
import com.hes.lis.mltd.utils.LogUtils;
import com.hes.lis.mltd.vo.MeterReplica;
import com.hes.lis.mltd.vo.VeryFirstCommunication;


/**
 * 
 * @author psvr
 *
 */
public class MeterInitialCheckings {
	
	public void initialCheckings(MeterMaster meterMaster,Date commStartDateTime){
		MeterRuleEngineDAO daoObj=new MeterRuleEngineDAO();

		try{
			
			VeryFirstCommunication firstComm=daoObj.fetchMeterVeryFirstComm(meterSerialNumber);
			if(firstComm==null){
				
				
				
				
			}else {
				
			}
			
			
			
			
			
			VeryFirstCommunication firstComm=checkForFirstCommunication(meterSerialNumber,commStartDateTime);
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkForFirstCommunication(String meterSerialNumber,Date commStartDateTime){
		try{
			VeryFirstCommunication firstComm=daoObj.fetchMeterVeryFirstComm(meterSerialNumber);
			if(firstComm==null){
				firstComm=new VeryFirstCommunication();
				firstComm.setCommDatetime(new Timestamp(commStartDateTime.getTime()));
				firstComm.setMeterSerialNumber(meterSerialNumber);
				boolean status=daoObj.saveObject(firstComm);
				if(!status) {//IF NOT SAVED then it should recheck in next communication
					firstComm=null;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public MeterReplica checkConfigParams(String meterSerialNumber,DataInputStream in,DataOutputStream toClient,Integer sleepSeconds,  String meterNumber,LogUtils logger){
		MeterReplica meterReplica=null;
		MeterRuleEngineDAO dao=new MeterRuleEngineDAO();
		MeterGettings meterGetObj=new MeterGettings();

		try{
			meterReplica=dao.fetchMeterReplica(meterSerialNumber);
			if(meterReplica==null){
				meterReplica=new MeterReplica();
				meterReplica.setMeterSerialNumber(meterSerialNumber);
			}
			
			String manufactureName=meterGetObj.getManufactureName(in, toClient, sleepSeconds, meterNumber, logger);
			if(manufactureName!=null){
				meterReplica.setManufactureName(manufactureName);
			}
			String meterFirmwareVersion=meterGetObj.getMeterFirmWareVersion(in, toClient, sleepSeconds, meterNumber, logger);
			if(meterFirmwareVersion!=null){
				meterReplica.setMeterFirmwareVersion(meterFirmwareVersion);
			}
			String meterProgramFirmwareVersion=meterGetObj.getMeterProgramFirmWareVersion(in, toClient, sleepSeconds, meterNumber, logger);
			if(meterProgramFirmwareVersion!=null){
				meterReplica.setMeterProgramFirmWareVersion(meterProgramFirmwareVersion);
			}
			
			String meterType=meterGetObj.getMeterType(in, toClient, sleepSeconds, meterNumber, logger);
			if(meterType!=null){
				meterReplica.setMeterType(meterType);
			}
			
			String modemFirmwareVersion=meterGetObj.getModemFirmWareVersion(in, toClient, sleepSeconds, meterNumber, logger);
			if(modemFirmwareVersion!=null){
				meterReplica.setModemFirmwareVersion(modemFirmwareVersion);
			}
			String modemIMEINumber=meterGetObj.getModemImeiNumber(in, toClient, sleepSeconds, meterNumber, logger);
			if(modemIMEINumber!=null){
				meterReplica.setModemImeiNumber(modemIMEINumber);
			}
			
			String signalStrength=meterGetObj.getSignalStrength(in, toClient, sleepSeconds, meterNumber, logger);
			if(signalStrength!=null){
				meterReplica.setSignalStrength(signalStrength);
			}
			
			String simDialNumber=meterGetObj.getSimDialNumber(in, toClient, sleepSeconds, meterNumber, logger);
			if(simDialNumber!=null){
				meterReplica.setSimDialNumber(simDialNumber);
			}
			
			String simSerialNumber=meterGetObj.getSimSerialNumber(in, toClient, sleepSeconds, meterNumber, logger);
			if(simSerialNumber!=null){
				meterReplica.setSimSerialNumber(simSerialNumber);
			}
			
			String relayStatus=meterGetObj.fetchRelayStatus(in, toClient, sleepSeconds, meterNumber, logger);
			if(relayStatus!=null){
				meterReplica.setRelayStatus(relayStatus);
			}
			
			Date meterDate=meterGetObj.fetchRTCFromMeter(in, toClient, sleepSeconds, meterNumber, logger);
			if(meterDate!=null){
				meterReplica.setLastMeterDateTime(new Timestamp(meterDate.getTime()));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return meterReplica;
	}
	

}
