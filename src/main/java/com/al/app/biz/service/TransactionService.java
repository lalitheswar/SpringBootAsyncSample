package com.al.app.biz.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.al.app.biz.entity.TransactionResponseEntity;
import com.al.app.biz.util.TransactionContextEnum;
import com.al.app.biz.util.TransactionToken;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionManager transactionManager = new TransactionManager();

    public TransactionToken getNewTransactionToken(TransactionContextEnum context) {
        TransactionToken transactionToken = new TransactionToken();
        transactionToken.setTransactionID(UUID.randomUUID());
        transactionToken.setContext(context);
        return transactionToken;
    }
    
    public CompletableFuture<TransactionResponseEntity> processTransaction(TransactionToken token, int count) {
        CompletableFuture<TransactionResponseEntity> future = new CompletableFuture<TransactionResponseEntity>();
        logger.info("Registering with manager for transactionID: " + token.getTransactionID());
        transactionManager.register(token.getTransactionID(), future);
        logger.info("EX: Producing message to MQ server");
        return future;
    }

    @Scheduled(fixedDelay = 100)
    void run() {
        List<UUID> keys = transactionManager.getCurrentKeys();
        for (UUID key : keys) {
            logger.info("EX: Consuming message from MQ server");
            transactionManager.onProcessSuccess(key, processTransactionMessage(key));
        }
    }

    private TransactionResponseEntity processTransactionMessage(UUID key) {
        logger.info("Processing message for transaction " + key);
        TransactionResponseEntity responseEntity = new TransactionResponseEntity();
        responseEntity.setStatus("Successful !!");
        responseEntity.setTransactionID(key);
        responseEntity.setStatusCode(200);
        return responseEntity;
    }
}
