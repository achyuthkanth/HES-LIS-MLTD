package com.hes.lis.mltd.vo;

import java.sql.Timestamp;

/**
 * MeterReplica entity. @author MyEclipse Persistence Tools
 */

public class MeterReplica implements java.io.Serializable {

	private static final long serialVersionUID = 8182862221581260255L;
	
	private String meterSerialNumber;
	private String signalStrength;
	private String manufactureName;
	private String meterFirmwareVersion;
	private String meterProgramFirmWareVersion;
	private String meterType;
	private String modemFirmwareVersion;
	private String modemImeiNumber;
	private String simDialNumber;
	private String simSerialNumber;
	private Timestamp lastMeterDateTime;
	private String relayStatus;
	private Timestamp lastInstantDatetime;
	private Timestamp lastBillingDatetime;
	private Timestamp lastLoadsurveyDatetime;
	private Timestamp lastMidnightSnapDatetime;
	private Timestamp lastCommDatetime;

	// Constructors

	/** default constructor */
	public MeterReplica() {
	}

	/** minimal constructor */
	public MeterReplica(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}

	/** full constructor */
	public MeterReplica(String meterSerialNumber, String signalStrength,
			String manufactureName, String meterFirmwareVersion,
			String meterProgramFirmWareVersion, String meterType,
			String modemFirmwareVersion, String modemImeiNumber,
			String simDialNumber, String simSerialNumber,
			Timestamp lastMeterDateTime, String relayStatus,
			Timestamp lastInstantDatetime, Timestamp lastBillingDatetime,
			Timestamp lastLoadsurveyDatetime,
			Timestamp lastMidnightSnapDatetime, Timestamp lastCommDatetime) {
		this.meterSerialNumber = meterSerialNumber;
		this.signalStrength = signalStrength;
		this.manufactureName = manufactureName;
		this.meterFirmwareVersion = meterFirmwareVersion;
		this.meterProgramFirmWareVersion = meterProgramFirmWareVersion;
		this.meterType = meterType;
		this.modemFirmwareVersion = modemFirmwareVersion;
		this.modemImeiNumber = modemImeiNumber;
		this.simDialNumber = simDialNumber;
		this.simSerialNumber = simSerialNumber;
		this.lastMeterDateTime = lastMeterDateTime;
		this.relayStatus = relayStatus;
		this.lastInstantDatetime = lastInstantDatetime;
		this.lastBillingDatetime = lastBillingDatetime;
		this.lastLoadsurveyDatetime = lastLoadsurveyDatetime;
		this.lastMidnightSnapDatetime = lastMidnightSnapDatetime;
		this.lastCommDatetime = lastCommDatetime;
	}

	// Property accessors

	public String getMeterSerialNumber() {
		return this.meterSerialNumber;
	}

	public void setMeterSerialNumber(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}

	public String getSignalStrength() {
		return this.signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getManufactureName() {
		return this.manufactureName;
	}

	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}

	public String getMeterFirmwareVersion() {
		return this.meterFirmwareVersion;
	}

	public void setMeterFirmwareVersion(String meterFirmwareVersion) {
		this.meterFirmwareVersion = meterFirmwareVersion;
	}

	public String getMeterProgramFirmWareVersion() {
		return this.meterProgramFirmWareVersion;
	}

	public void setMeterProgramFirmWareVersion(
			String meterProgramFirmWareVersion) {
		this.meterProgramFirmWareVersion = meterProgramFirmWareVersion;
	}

	public String getMeterType() {
		return this.meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getModemFirmwareVersion() {
		return this.modemFirmwareVersion;
	}

	public void setModemFirmwareVersion(String modemFirmwareVersion) {
		this.modemFirmwareVersion = modemFirmwareVersion;
	}

	public String getModemImeiNumber() {
		return this.modemImeiNumber;
	}

	public void setModemImeiNumber(String modemImeiNumber) {
		this.modemImeiNumber = modemImeiNumber;
	}

	public String getSimDialNumber() {
		return this.simDialNumber;
	}

	public void setSimDialNumber(String simDialNumber) {
		this.simDialNumber = simDialNumber;
	}

	public String getSimSerialNumber() {
		return this.simSerialNumber;
	}

	public void setSimSerialNumber(String simSerialNumber) {
		this.simSerialNumber = simSerialNumber;
	}

	public Timestamp getLastMeterDateTime() {
		return this.lastMeterDateTime;
	}

	public void setLastMeterDateTime(Timestamp lastMeterDateTime) {
		this.lastMeterDateTime = lastMeterDateTime;
	}

	public String getRelayStatus() {
		return this.relayStatus;
	}

	public void setRelayStatus(String relayStatus) {
		this.relayStatus = relayStatus;
	}

	public Timestamp getLastInstantDatetime() {
		return this.lastInstantDatetime;
	}

	public void setLastInstantDatetime(Timestamp lastInstantDatetime) {
		this.lastInstantDatetime = lastInstantDatetime;
	}

	public Timestamp getLastBillingDatetime() {
		return this.lastBillingDatetime;
	}

	public void setLastBillingDatetime(Timestamp lastBillingDatetime) {
		this.lastBillingDatetime = lastBillingDatetime;
	}

	public Timestamp getLastLoadsurveyDatetime() {
		return this.lastLoadsurveyDatetime;
	}

	public void setLastLoadsurveyDatetime(Timestamp lastLoadsurveyDatetime) {
		this.lastLoadsurveyDatetime = lastLoadsurveyDatetime;
	}

	public Timestamp getLastMidnightSnapDatetime() {
		return this.lastMidnightSnapDatetime;
	}

	public void setLastMidnightSnapDatetime(Timestamp lastMidnightSnapDatetime) {
		this.lastMidnightSnapDatetime = lastMidnightSnapDatetime;
	}

	public Timestamp getLastCommDatetime() {
		return this.lastCommDatetime;
	}

	public void setLastCommDatetime(Timestamp lastCommDatetime) {
		this.lastCommDatetime = lastCommDatetime;
	}

}