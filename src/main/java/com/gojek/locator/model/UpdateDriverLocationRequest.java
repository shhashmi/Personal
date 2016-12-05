package com.gojek.locator.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDriverLocationRequest {

	@JsonProperty("latitude")
	private Double latitude;
	
	@JsonProperty("longitude")
	private Double longitude;
	
	@JsonProperty("accuracy")
	private Double accuracy;
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
}
