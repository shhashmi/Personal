package com.gojek.locator.error;

public enum ErrorMessage {

	InvalidLatitude(1000,"Latitude should be between +/- 90"),
	InvalidLongitude(1001,"Longitude should be between +/- 90"),
	InvalidAccuracy(1002,"Accuracy should be between 0-1"),
	InvalidDriver(10003,"Valid driver id is between 1-50000");
	
	private int errorCode;
	private String message;
	
	ErrorMessage(int errorCode,String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	
}
