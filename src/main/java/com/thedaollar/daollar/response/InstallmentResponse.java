package com.thedaollar.daollar.response;

import com.thedaollar.daollar.db.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InstallmentResponse extends Transaction {
    private int paidAmount;

    public InstallmentResponse(int id, String sender, String receiver, int totalAmount, int paidAmount) {
        super(id, sender, receiver, totalAmount);
        this.paidAmount = paidAmount;
    }
}
