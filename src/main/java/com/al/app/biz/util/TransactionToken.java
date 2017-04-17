package com.al.app.biz.util;

import java.util.UUID;

import lombok.Data;

@Data
public class TransactionToken {
    private UUID transactionID;
    private TransactionContextEnum context;
}
