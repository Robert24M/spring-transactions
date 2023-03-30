package me.learnspring.springtransactions.services;

import me.learnspring.springtransactions.exceptions.AccountNotFoundException;
import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.repositories.AccountRepositorySpringData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferServiceSpringData implements TransferService {

    AccountRepositorySpringData accountRepository;

    public TransferServiceSpringData(AccountRepositorySpringData accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void makeTransfer(
            long idSender,
            long idReceiver,
            BigDecimal amount) {

        Account sender = accountRepository.findById(idSender).orElseThrow(AccountNotFoundException::new);
        Account receiver = accountRepository.findById(idReceiver).orElseThrow(AccountNotFoundException::new);

        BigDecimal senderNewBalance = sender.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);

        accountRepository.changeBalance(sender.getId(), senderNewBalance);
        accountRepository.changeBalance(receiver.getId(), receiverNewBalance);

//        throw new RuntimeException("Something went wrong");
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

}
