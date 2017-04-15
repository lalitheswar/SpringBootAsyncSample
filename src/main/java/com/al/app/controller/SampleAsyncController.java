package com.al.app.controller;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.dto.ProcessingStatus;
import com.al.app.service.ProcessingTask;
import com.al.app.service.SpringAsyncSampleService;

/**
 * @author lalith
 *
 */
@RestController
public class SampleAsyncController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(SampleAsyncController.class);

    @Autowired
    private SpringAsyncSampleService asyncSampleService;

    @RequestMapping("/nio/one")
    public DeferredResult<ProcessingStatus> one() {
        DeferredResult<ProcessingStatus> deferredResult = new DeferredResult<ProcessingStatus>();
        logger.info("Calling spring async task");
        asyncSampleService.processRequest(deferredResult);
        logger.info("Call to async method is invoked");
        return deferredResult;
    }

    /*
     * FIXME: Not for production use. Improve the following line
     */
    private final Timer timer = new Timer();

    @RequestMapping("/nio/two")
    public DeferredResult<ProcessingStatus> two() {
        DeferredResult<ProcessingStatus> deferredResult = new DeferredResult<ProcessingStatus>();
        logger.info("Creating a new async timer task");
        ProcessingTask task = new ProcessingTask(deferredResult);
        timer.schedule(task, 10, 50);
        logger.info("Timer task is scheduled. Returning DeferredResult");
        return deferredResult;
    }
}
