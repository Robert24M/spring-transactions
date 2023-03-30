package me.learnspring.springtransactions.services;

import me.learnspring.springtransactions.model.Account;

import java.math.BigDecimal;

public interface TransferService {
    void makeTransfer(
            long idSender,
            long idReceiver,
            BigDecimal amount);

    Iterable<Account> getAllAccounts();
}
