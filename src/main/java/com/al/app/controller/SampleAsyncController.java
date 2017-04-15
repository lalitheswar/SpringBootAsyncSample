package com.al.app.controller;

import java.util.Timer;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.dto.ProcessingStatus;
import com.al.app.process.ProcessingTask;

/**
 * @author lalith
 *
 */
@RestController
public class SampleAsyncController {
	
	@Autowired
	private Log log;
	
	/*
	 * FIXME: Not for production use. Improve the following line
	 */
	private final Timer timer = new Timer();
	
	@RequestMapping("/nio/one")
	public DeferredResult<ProcessingStatus> one() {
		DeferredResult<ProcessingStatus> deferredResult = new DeferredResult<ProcessingStatus>();
		log.info("Creating a new async task");
		ProcessingTask task = new ProcessingTask(deferredResult);
		timer.schedule(task, 10, 50);
		log.info("Task is scheduled. Returning DeferredResult");
		return deferredResult;
	}
}
