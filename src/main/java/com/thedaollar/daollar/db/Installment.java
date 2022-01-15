package com.thedaollar.daollar.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Installment {
    private int id;
    private int parentId;
    private int paidAmount;
}
