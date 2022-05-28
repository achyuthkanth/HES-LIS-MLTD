package com.hes.lis.mltd.vo;

/**
 * 
 * @author psvr
 *
 */

public class MeterLogger implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1272583611963434046L;
	private MeterLoggerId id;
	private String commDetails;

	// Constructors

	/** default constructor */
	public MeterLogger() {
	}

	/** minimal constructor */
	public MeterLogger(MeterLoggerId id) {
		this.id = id;
	}

	/** full constructor */
	public MeterLogger(MeterLoggerId id, String commDetails) {
		this.id = id;
		this.commDetails = commDetails;
	}

	// Property accessors

	public MeterLoggerId getId() {
		return this.id;
	}

	public void setId(MeterLoggerId id) {
		this.id = id;
	}

	public String getCommDetails() {
		return this.commDetails;
	}

	public void setCommDetails(String commDetails) {
		this.commDetails = commDetails;
	}

}