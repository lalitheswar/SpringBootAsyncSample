package com.al.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.dto.ProcessingStatus;
import com.al.app.fw.DeferredResultManager;

@Service
public class LongExecutionService {
    private static final Logger logger = LoggerFactory.getLogger(LongExecutionService.class);
    
    private final DeferredResultManager<ProcessingStatus> deferredResultManager = new DeferredResultManager<ProcessingStatus>();

    public DeferredResult<ProcessingStatus> getData(String param) {
        DeferredResult<ProcessingStatus> deferredResult = new DeferredResult<ProcessingStatus>();
        String key = param + ":" + System.nanoTime();
        logger.info("Registering with manager for key: " + key);
        deferredResultManager.register(key, deferredResult);
        return deferredResult;
    }

    @Scheduled(fixedDelay = 10000)
    void run() {
        List<String> keys = deferredResultManager.getCurrentKeys();
        logger.info("Current number of keys to process: " + keys.size());
        for (String key : keys) {
            deferredResultManager.onProcessSuccess(key, getStatus(key));
        }
    }

    private ProcessingStatus getStatus(String key) {
        logger.info("Processing getStatus for key: " + key);
        return new ProcessingStatus(200, "Successfully processed for request with key: " + key);
    }
}
