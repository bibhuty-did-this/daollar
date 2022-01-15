package com.thedaollar.daollar.dao;

import com.thedaollar.daollar.db.Installment;
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
public class InstallmentDAO {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InstallmentDAO.class);

    private final List<Installment> installments = new ArrayList<>();

    @PostConstruct
    private void initialize() {
        var jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/Installments.json")) {
            var obj = (JSONObject) jsonParser.parse(reader);
            var transactionObjects = (JSONArray) obj.get("data");
            for (var transactionObject : transactionObjects) {
                var installmentJSON = (JSONObject) transactionObject;
                var installment = Installment.builder()
                        .id(Integer.parseInt(installmentJSON.get("id").toString()))
                        .parentId(Integer.parseInt(installmentJSON.get("parentId").toString()))
                        .paidAmount(Integer.parseInt(installmentJSON.get("paidAmount").toString()))
                        .build();
                logger.info("Installment saved is {}", installment);
                installments.add(installment);
            }

        } catch (Exception ex) {
            logger.error("Exception while initializing data ", ex);
        }
    }

    public Map<Integer, Integer> getTotalInstallmentsPaid(Set<Integer> transactionIds) {
        return installments
                .stream()
                .filter(installment -> transactionIds.contains(installment.getParentId()))
                .collect(
                        Collectors.groupingBy(
                                Installment::getParentId,
                                Collectors.summingInt(Installment::getPaidAmount)
                        )
                );
    }

    public List<Installment> getAllInstallmentsByParentId(int parentId) {
        return installments
                .stream()
                .filter(installment -> parentId == installment.getParentId())
                .collect(Collectors.toList());
    }
}