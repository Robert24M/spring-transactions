package me.learnspring.springtransactions.services;

import me.learnspring.springtransactions.exceptions.AccountNotFoundException;
import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.repositories.AccountRepositorySpringData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransferServiceSpringDataUnitTest {

    @Mock
    private AccountRepositorySpringData accountRepository;

    @InjectMocks
    private TransferServiceSpringData transferService;

    @DisplayName("Test the amount is transferred " +
            "from one account to another if no exception occurs.")
    @Test
    public void moneyTransferHappyFlow() {

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

    @Test
    public void moneyTransferDestinationAccountNotFoundFlow() {
        Account sender = new Account();
        sender.setId(1);
        sender.setBalance(new BigDecimal(1000));

        given(accountRepository.findById(1L))
                .willReturn(Optional.of(sender));
        given(accountRepository.findById(2L))
                .willReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> transferService.makeTransfer(1, 2, new BigDecimal(100)));

        verify(accountRepository, never())
                .changeBalance(anyLong(), any());
    }
}
