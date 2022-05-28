package com.hes.lis.mltd.vo;

import java.sql.Timestamp;

/**
 * 
 * @author psvr
 *
 */

public class HesDemandRequestDetails implements java.io.Serializable {

	private static final long serialVersionUID = -1207861525651044503L;
	
	private String requestUniId;
	private String meterSerialNumber;
	private String requestType;
	private String checkCommand;
	private String requestStatus;
	private String setValue;
	private String getValue;
	private String comments;
	private Timestamp reqSetTime;
	private Timestamp reqStartTime;
	private Timestamp reqCompTime;

	// Constructors

	/** default constructor */
	public HesDemandRequestDetails() {
	}

	/** minimal constructor */
	public HesDemandRequestDetails(String requestUniId) {
		this.requestUniId = requestUniId;
	}

	/** full constructor */
	public HesDemandRequestDetails(String requestUniId,
			String meterSerialNumber, String requestType, String checkCommand,
			String requestStatus, String setValue, String getValue,
			String comments, Timestamp reqSetTime, Timestamp reqStartTime,
			Timestamp reqCompTime) {
		this.requestUniId = requestUniId;
		this.meterSerialNumber = meterSerialNumber;
		this.requestType = requestType;
		this.checkCommand = checkCommand;
		this.requestStatus = requestStatus;
		this.setValue = setValue;
		this.getValue = getValue;
		this.comments = comments;
		this.reqSetTime = reqSetTime;
		this.reqStartTime = reqStartTime;
		this.reqCompTime = reqCompTime;
	}

	// Property accessors

	public String getRequestUniId() {
		return this.requestUniId;
	}

	public void setRequestUniId(String requestUniId) {
		this.requestUniId = requestUniId;
	}

	public String getMeterSerialNumber() {
		return this.meterSerialNumber;
	}

	public void setMeterSerialNumber(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCheckCommand() {
		return this.checkCommand;
	}

	public void setCheckCommand(String checkCommand) {
		this.checkCommand = checkCommand;
	}

	public String getRequestStatus() {
		return this.requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getSetValue() {
		return this.setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}

	public String getGetValue() {
		return this.getValue;
	}

	public void setGetValue(String getValue) {
		this.getValue = getValue;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getReqSetTime() {
		return this.reqSetTime;
	}

	public void setReqSetTime(Timestamp reqSetTime) {
		this.reqSetTime = reqSetTime;
	}

	public Timestamp getReqStartTime() {
		return this.reqStartTime;
	}

	public void setReqStartTime(Timestamp reqStartTime) {
		this.reqStartTime = reqStartTime;
	}

	public Timestamp getReqCompTime() {
		return this.reqCompTime;
	}

	public void setReqCompTime(Timestamp reqCompTime) {
		this.reqCompTime = reqCompTime;
	}

}