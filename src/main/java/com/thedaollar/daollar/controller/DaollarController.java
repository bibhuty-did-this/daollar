package com.thedaollar.daollar.controller;

import com.thedaollar.daollar.manager.DaollarManager;
import com.thedaollar.daollar.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/daollar")
public class DaollarController {

    @Autowired
    private DaollarManager daollarManager;

    @RequestMapping(method = RequestMethod.GET, path = "/get-transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getTransactions(@ModelAttribute TransactionRequest request) {
        return new ResponseEntity<>(daollarManager.getTransactions(request.getPageNo(), request.getDirection()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-installments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getInstallments(@RequestParam int parentId) {
        return new ResponseEntity<>(daollarManager.getInstallments(parentId), HttpStatus.OK);
    }
}
