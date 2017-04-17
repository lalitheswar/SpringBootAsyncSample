package com.al.app.biz.service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.al.app.biz.entity.TransactionResponseEntity;

public class TransactionManager {
    private final ConcurrentMap<UUID, CompletableFuture<TransactionResponseEntity>> futureMap = new ConcurrentHashMap<UUID, CompletableFuture<TransactionResponseEntity>>();
    
    public void register(UUID key, CompletableFuture<TransactionResponseEntity> future) {
        if (futureMap.containsKey(key)) {
            throw new RuntimeException("Manager already contains an object with key: " + key);
        }
        futureMap.put(key, future);
    }

    public ArrayList<UUID> getCurrentKeys() {
        ArrayList<UUID> keys = new ArrayList<UUID>();
        keys.addAll(futureMap.keySet());
        return keys;
    }

    public void onProcessSuccess(UUID key, TransactionResponseEntity object) {
        CompletableFuture<TransactionResponseEntity> future = futureMap.remove(key);
        if (future == null) {
            // Something weird has happened
            throw new RuntimeException("Alert !! for key: " + key);
        }
        future.complete(object);
    }

    public void onProcessFailure(UUID key, TransactionResponseEntity object) {
        // Do something for process failure case
    }

    
}
