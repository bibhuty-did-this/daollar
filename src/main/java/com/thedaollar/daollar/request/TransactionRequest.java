package com.thedaollar.daollar.request;

import com.thedaollar.daollar.enums.Direction;
import lombok.Data;

@Data
public class TransactionRequest {
    private int pageNo;
    private Direction direction = Direction.NONE;
}
