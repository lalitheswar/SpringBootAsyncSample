package com.al.app.process;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.dto.ProcessingStatus;

/**
 * @author lalith
 *
 */
public class ProcessingTask extends TimerTask {
	
	private Log log = SLF4JLogFactory.getLog("AsyncProcessingTask");
	
	private DeferredResult<ProcessingStatus> deferredResult;
	
	public ProcessingTask(DeferredResult<ProcessingStatus> deferredResult) {
		super();
		this.deferredResult = deferredResult;
	}

	@Override
	public void run() {
		log.info("Entering Async 'run' method");
		ProcessingStatus result = null;
		if(deferredResult.isSetOrExpired()) {
			log.info("DeferredResult is set. Cancelling further scheduling of task ...");
			this.cancel();
			return;
		} else {
			result = new ProcessingStatus(200, "Successful in processing request !!");
			deferredResult.setResult(result);
			log.info("Async method is successfully processed");
		}
		log.info("Exiting from Async 'run' method");
	}
}
