package com.gojek.locator.configuration;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.gojek.locator.model.UpdateDriverLocationRequest;

@Configuration
public class SpringConfiguration {

	@Value("${com.gojek.locator.executor.corepoolsize}")
	private int corePoolSize; 
	
	@Bean("com.gojek.driverupdate.requestqueue")
	public LinkedBlockingQueue<UpdateDriverLocationRequest> getDriverUpdateQueue() {
		return new LinkedBlockingQueue<UpdateDriverLocationRequest>();
	}
	
	@Bean("com.gojek.driverupdate.threadpool")
	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setQueueCapacity(51000);
		executor.setMaxPoolSize(20);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		
		
		return executor;
	}
	
}
