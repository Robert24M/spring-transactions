package me.learnspring.springtransactions.controllers;

import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.model.TransferRequest;
import me.learnspring.springtransactions.services.TransferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final TransferService transferService;

    public AccountController(@Qualifier("transferServiceSpringData") TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public void makeTransfer(
            @RequestBody TransferRequest transferRequest
    ) {
        transferService.makeTransfer(
                transferRequest.getIdSender(),
                transferRequest.getIdReceiver(),
                transferRequest.getAmount()
        );
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAllAccount(
            @RequestParam(required = false) String name
    ) {
        if (name == null) {
            return transferService.getAllAccounts();
        }else {
            return transferService.getAccountsByName(name);
        }
    }
}
