package com.thedaollar.daollar.dao;

import com.thedaollar.daollar.db.Transaction;
import com.thedaollar.daollar.enums.Direction;
import com.thedaollar.daollar.response.TransactionResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TransactionDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TransactionDAO.class);

    private final List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    private void initialize() {
        var jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/Transactions.json")) {
            var obj = (JSONObject) jsonParser.parse(reader);
            var transactionObjects = (JSONArray) obj.get("data");
            for (var transactionObject : transactionObjects) {
                var transactionJSON = (JSONObject) transactionObject;
                var transaction =
                        Transaction.builder()
                                .id(Integer.parseInt(transactionJSON.get("id").toString()))
                                .sender(transactionJSON.get("sender").toString())
                                .receiver(transactionJSON.get("receiver").toString())
                                .totalAmount(Integer.parseInt(transactionJSON.get("totalAmount").toString()))
                                .build();
                logger.info("Transaction saved is {}", transaction);
                transactions.add(transaction);
            }

        } catch (Exception ex) {
            logger.error("Exception while initializing data ", ex);
        }
    }

    public List<TransactionResponse> getPaginatedData(int pageNo, Direction sortDirection) {
        var fromIndex = 2 * pageNo;
        var toIndex = Math.min(fromIndex + 2, transactions.size());
        if (fromIndex >= transactions.size()) return new ArrayList<>();
        var transactionList = transactions.stream().map(TransactionResponse::toTransactionResponse).collect(Collectors.toList());
        if (Direction.NONE.equals(sortDirection)) transactionList.sort(Comparator.comparing(Transaction::getId));
        if (Direction.DESC.equals(sortDirection)) Collections.reverse(transactionList);
        return transactionList.subList(fromIndex, toIndex);
    }

    public Transaction getById(int parentId) {
        return transactions
                .stream()
                .filter(transaction -> parentId == transaction.getId())
                .findFirst()
                .orElse(null);
    }
}
