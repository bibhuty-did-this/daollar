package com.thedaollar.daollar.response;

import com.thedaollar.daollar.db.Transaction;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionResponse extends Transaction {
    private int totalPaidAmount;

    public TransactionResponse(int id, String sender, String receiver, int totalAmount) {
        super(id, sender, receiver, totalAmount);
    }
}
