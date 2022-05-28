package com.hes.lis.mltd.vo;

import java.sql.Timestamp;

/**
 * 
 * @author psvr
 *
 */
public class VeryFirstCommunication implements java.io.Serializable {

	private static final long serialVersionUID = -1003644733333500553L;
	private String meterSerialNumber;
	private Timestamp commDatetime;

	// Constructors

	/** default constructor */
	public VeryFirstCommunication() {
	}

	/** minimal constructor */
	public VeryFirstCommunication(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}

	/** full constructor */
	public VeryFirstCommunication(String meterSerialNumber,
			Timestamp commDatetime) {
		this.meterSerialNumber = meterSerialNumber;
		this.commDatetime = commDatetime;
	}

	// Property accessors

	public String getMeterSerialNumber() {
		return this.meterSerialNumber;
	}

	public void setMeterSerialNumber(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}

	public Timestamp getCommDatetime() {
		return this.commDatetime;
	}

	public void setCommDatetime(Timestamp commDatetime) {
		this.commDatetime = commDatetime;
	}

}