package com.al.app.fw;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author lalith
 *
 * @param <T>
 */
public class DeferredResultManager<T> {
    private final ConcurrentMap<String, DeferredResult<T>> deferredResultHolder = new ConcurrentHashMap<String, DeferredResult<T>>();
    
    /**
     * This method calling interface should make sure of uniqueness of key
     * 
     * @param key
     * @param deferredResult
     */
    public void register(String key, DeferredResult<T> deferredResult) {
        if (deferredResultHolder.containsKey(key)) {
            throw new RuntimeException("Manager already contains an object with key: " + key);
        }
        deferredResultHolder.put(key, deferredResult);
    }

    public ArrayList<String> getCurrentKeys() {
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(deferredResultHolder.keySet());
        return keys;
    }

    public void onProcessSuccess(String key, T object) {
        DeferredResult<T> deferredResult = deferredResultHolder.remove(key);
        if (deferredResult == null) {
            // Something weird has happened
            throw new RuntimeException("Alert !! for key: " + key);
        }
        deferredResult.setResult(object);
    }

    public void onProcessFailure(String key, T object) {
        // Do something for process failure case
    }

}
