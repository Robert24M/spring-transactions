package me.learnspring.springtransactions.controllers;

import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.model.TransferRequest;
import me.learnspring.springtransactions.services.TransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private final TransferService transferService;

    public AccountController(TransferService transferService) {
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
    public List<Account> getAllAccount() {
        return transferService.getAllAccounts();
    }
}
