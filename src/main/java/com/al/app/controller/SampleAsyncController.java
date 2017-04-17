package com.al.app.controller;

import java.util.Timer;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.al.app.biz.entity.TransactionResponseEntity;
import com.al.app.biz.service.TransactionService;
import com.al.app.biz.util.TransactionContextEnum;
import com.al.app.biz.util.TransactionToken;
import com.al.app.dto.ProcessingStatus;
import com.al.app.service.LongExecutionService;
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

    @Autowired
    private LongExecutionService longExecutionService;
    
    @Autowired
    private TransactionService transactionService;

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

    @RequestMapping("/nio/three")
    public DeferredResult<ProcessingStatus> three() {
        DeferredResult<ProcessingStatus> deferredResult = longExecutionService.getData("/nio/three");
        return deferredResult;
    }
    
    
    
    @RequestMapping("/nio/five")
    public DeferredResult<TransactionResponseEntity> five() {
        DeferredResult<TransactionResponseEntity> deferredResult = new DeferredResult<TransactionResponseEntity>();
        
        // Get a new transaction token
        TransactionToken token = transactionService.getNewTransactionToken(TransactionContextEnum.RESERVE);
        
        // make a call to process transaction
        CompletableFuture<TransactionResponseEntity> future = transactionService.processTransaction(token, 2);
        
        // add callback to set the deferred result value 
        future.thenAccept(e -> {
            deferredResult.setResult(e);
        });
        
        // return the deferredResult
        return deferredResult;
    }
    
}
