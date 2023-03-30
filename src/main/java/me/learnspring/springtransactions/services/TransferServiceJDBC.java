package me.learnspring.springtransactions.services;

import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferServiceJDBC implements TransferService{

    private final AccountRepository accountRepository;

    public TransferServiceJDBC(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void makeTransfer(
            long idSender,
            long idReceiver,
            BigDecimal amount) {

        Account sender = accountRepository.getAccountById(idSender);
        Account receiver = accountRepository.getAccountById(idReceiver);

        BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);

        accountRepository.changeBalance(sender.getId(), senderNewBalance);
        accountRepository.changeBalance(receiver.getId(), receiverNewBalance);

//        throw new RuntimeException("Something went wrong");
    } 

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public List<Account> getAccountsByName(String name) {
        return accountRepository.findAccountsByName(name);
    }


}
