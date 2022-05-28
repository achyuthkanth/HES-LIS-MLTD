package com.hes.lis.mltd.vo;

import java.sql.Timestamp;

/**
 * 
 * @author psvr
 *
 */

public class MeterLoggerId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3024660595672224081L;
	private String meterSerialNumber;
	private Timestamp commDatetime;
	private String commPurpose;

	// Constructors

	/** default constructor */
	public MeterLoggerId() {
	}

	/** full constructor */
	public MeterLoggerId(String meterSerialNumber, Timestamp commDatetime,
			String commPurpose) {
		this.meterSerialNumber = meterSerialNumber;
		this.commDatetime = commDatetime;
		this.commPurpose = commPurpose;
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

	public String getCommPurpose() {
		return this.commPurpose;
	}

	public void setCommPurpose(String commPurpose) {
		this.commPurpose = commPurpose;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MeterLoggerId))
			return false;
		MeterLoggerId castOther = (MeterLoggerId) other;

		return ((this.getMeterSerialNumber() == castOther
				.getMeterSerialNumber()) || (this.getMeterSerialNumber() != null
				&& castOther.getMeterSerialNumber() != null && this
				.getMeterSerialNumber()
				.equals(castOther.getMeterSerialNumber())))
				&& ((this.getCommDatetime() == castOther.getCommDatetime()) || (this
						.getCommDatetime() != null
						&& castOther.getCommDatetime() != null && this
						.getCommDatetime().equals(castOther.getCommDatetime())))
				&& ((this.getCommPurpose() == castOther.getCommPurpose()) || (this
						.getCommPurpose() != null
						&& castOther.getCommPurpose() != null && this
						.getCommPurpose().equals(castOther.getCommPurpose())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getMeterSerialNumber() == null ? 0 : this
						.getMeterSerialNumber().hashCode());
		result = 37
				* result
				+ (getCommDatetime() == null ? 0 : this.getCommDatetime()
						.hashCode());
		result = 37
				* result
				+ (getCommPurpose() == null ? 0 : this.getCommPurpose()
						.hashCode());
		return result;
	}

}