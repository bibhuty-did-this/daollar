package com.thedaollar.daollar.db;

import com.thedaollar.daollar.response.TransactionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private int id;
    private String sender;
    private String receiver;
    private int totalAmount;

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getSender(),
                transaction.getReceiver(),
                transaction.getTotalAmount()
        );
    }
}
