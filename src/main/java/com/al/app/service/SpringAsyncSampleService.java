package com.al.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.dto.ProcessingStatus;

@Service
public class SpringAsyncSampleService {

    private static final Logger logger = LoggerFactory.getLogger(SpringAsyncSampleService.class);

    @Async
    public void processRequest(DeferredResult<ProcessingStatus> deferredResult) {
        logger.info("Entering spring async method");
        if (!deferredResult.isSetOrExpired()) {
            ProcessingStatus processingStatus = new ProcessingStatus(200,
                    "Successfully processed from async annotated method");
            deferredResult.setResult(processingStatus);
        }
        logger.info("Exiting from spring async method");
    }

}
