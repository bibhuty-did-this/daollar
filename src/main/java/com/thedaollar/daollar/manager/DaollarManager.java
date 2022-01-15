package com.thedaollar.daollar.manager;

import com.thedaollar.daollar.dao.InstallmentDAO;
import com.thedaollar.daollar.dao.TransactionDAO;
import com.thedaollar.daollar.enums.Direction;
import com.thedaollar.daollar.response.InstallmentResponse;
import com.thedaollar.daollar.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DaollarManager {

    @Autowired
    private InstallmentDAO installmentDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    public List<TransactionResponse> getTransactions(int pageNo, Direction sortDirection) {
        var paginatedData = transactionDAO.getPaginatedData(pageNo, sortDirection);
        var transactionIds = paginatedData.stream().map(TransactionResponse::getId).collect(Collectors.toSet());
        var installmentsPaidMap = installmentDAO.getTotalInstallmentsPaid(transactionIds);
        for (var paginatedDatum : paginatedData)
            paginatedDatum.setTotalPaidAmount(installmentsPaidMap.getOrDefault(paginatedDatum.getId(), 0));
        return paginatedData;
    }

    public List<InstallmentResponse> getInstallments(int parentId) {
        var transactionData = transactionDAO.getById(parentId);
        return Optional.ofNullable(installmentDAO.getAllInstallmentsByParentId(parentId))
                .stream()
                .flatMap(Collection::stream)
                .map(installment -> new InstallmentResponse(installment.getId(), transactionData.getSender(), transactionData.getReceiver(), transactionData.getTotalAmount(), installment.getPaidAmount()))
                .sorted(Comparator.comparingInt(InstallmentResponse::getId))
                .collect(Collectors.toList());
    }
}
