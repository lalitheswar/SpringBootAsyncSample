package com.al.app.service;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.dto.ProcessingStatus;

/**
 * @author lalith
 *
 */
public class ProcessingTask extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(ProcessingTask.class);

    private DeferredResult<ProcessingStatus> deferredResult;

    public ProcessingTask(DeferredResult<ProcessingStatus> deferredResult) {
        super();
        this.deferredResult = deferredResult;
    }

    @Override
    public void run() {
        logger.info("Entering Async 'run' method");
        ProcessingStatus result = null;
        if (deferredResult.isSetOrExpired()) {
            logger.info("DeferredResult is set. Cancelling further scheduling of task ...");
            this.cancel();
            return;
        } else {
            result = new ProcessingStatus(200, "Successful in processing request !!");
            deferredResult.setResult(result);
            logger.info("Async method is successfully processed");
        }
        logger.info("Exiting from Async 'run' method");
    }
}
