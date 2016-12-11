package com.gojek.locator.validator;

import com.gojek.locator.exception.InvalidAccuracyException;
import com.gojek.locator.exception.InvalidDriverException;
import com.gojek.locator.exception.InvalidLatitudeException;
import com.gojek.locator.exception.InvalidLongitudeException;
import com.gojek.locator.model.UpdateDriverLocationRequest;
import com.gojek.locator.utils.Constants;

public class RequestValidator {

	public static void validateUpdateDriverLocationRequest(int driverId,UpdateDriverLocationRequest request) {
		if(driverId<1 || driverId>50000) {
			throw new InvalidDriverException();
		}
		if(request.getAccuracy()!=null && (request.getAccuracy()<Constants.MIN_ACCURACY || request.getAccuracy()>Constants.MAX_ACCURACY)) {
			throw new InvalidAccuracyException();
		}
		
		if(request.getLatitude() == null || request.getLatitude()<Constants.MIN_LATITUDE|| request.getLatitude()>Constants.MAX_LATITUDE) {
			throw new InvalidLatitudeException();
		}
		
		if( request.getLatitude() == null || request.getLongitude()<Constants.MIN_LONGITUDE || request.getLongitude()>Constants.MAX_LONGITUDE) {
			throw new InvalidLongitudeException();
		}
		
	}
	
	public static void validateGetDriverRequest(Double latitude,Double longitude) {
		if(latitude == null || latitude<Constants.MIN_LATITUDE || latitude>Constants.MAX_LATITUDE) {
			throw new InvalidLatitudeException();
		}
		if(longitude == null || longitude< Constants.MIN_LONGITUDE  || longitude > Constants.MAX_LONGITUDE) {
			throw new InvalidLongitudeException();
		}
	}
}
