package me.learnspring.springtransactions.services;

import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.repositories.AccountRepositorySpringData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransferServiceSpringDataUnitTest {

    @DisplayName("Test the amount is transferred " +
            "from one account to another if no exception occurs.")
    @Test
    public void moneyTransferHappyFlow() {
        AccountRepositorySpringData accountRepository = mock(AccountRepositorySpringData.class);
        TransferServiceSpringData transferService = new TransferServiceSpringData(accountRepository);

        Account sender = new Account();
        sender.setId(1);
        sender.setBalance(new BigDecimal(1000));

        Account destination = new Account();
        destination.setId(2);
        destination.setBalance(new BigDecimal(1000));

        given(accountRepository.findById(sender.getId()))
                .willReturn(Optional.of(sender));

        given(accountRepository.findById(destination.getId()))
                .willReturn(Optional.of(destination));

        transferService.makeTransfer(
                sender.getId(),
                destination.getId(),
                new BigDecimal(100)
        );

        verify(accountRepository)
                .changeBalance(1, new BigDecimal(900));
        verify(accountRepository)
                .changeBalance(2, new BigDecimal(1100));
    }
}
