package com.al.app.biz.entity;

import java.util.UUID;

import lombok.Data;

@Data
public class TransactionResponseEntity {
    private UUID transactionID;
    private String status;
    private int statusCode;
}
