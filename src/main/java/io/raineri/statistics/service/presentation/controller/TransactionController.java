package io.raineri.statistics.service.presentation.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import io.raineri.statistics.service.presentation.dto.RequestTransaction;
import io.raineri.statistics.service.presentation.mapper.TransactionMapper;
import io.raineri.statistics.service.application.usecase.contract.AddTransactionImpl;
import io.raineri.statistics.service.application.usecase.contract.RemoveTransactionImpl;

@RestController
public class TransactionController {
    private final AddTransactionImpl addTransaction;
    private final RemoveTransactionImpl removeTransaction;

    public TransactionController(AddTransactionImpl addTransaction, RemoveTransactionImpl removeTransaction) {
        this.addTransaction = addTransaction;
        this.removeTransaction = removeTransaction;
    }

    @PostMapping("/transacao")
    public void store(@Validated @RequestBody RequestTransaction request) {
        this.addTransaction.execute(TransactionMapper.toEntity(request));
    }

    @DeleteMapping("/transacao")
    public void delete() {
        this.removeTransaction.execute();
    }

}
