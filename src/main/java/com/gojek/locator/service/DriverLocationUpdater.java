package com.gojek.locator.service;

import com.gojek.locator.model.DriverLocation;
import com.gojek.locator.model.UpdateDriverLocationRequest;

public class DriverLocationUpdater implements Runnable{
	
	private DriverLocation request;
	public DriverLocationUpdater(DriverLocation request) {
		this.request = request;
	}
	
	@Override
	public void run() {
		
		
	}

}
