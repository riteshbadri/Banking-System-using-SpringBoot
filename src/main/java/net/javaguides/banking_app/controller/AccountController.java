package net.javaguides.banking_app.controller;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.dto.TransactionDto;
import net.javaguides.banking_app.dto.TransferFundDto;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get account by ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accDto);
    }

    // Deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> Deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.Deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    //Withdrawal REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> Withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accDto = accountService.Withdraw(id,amount);
        return ResponseEntity.ok(accDto);
    }

    // get all accounts REST API
    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> allAccounts = accountService.getAllAccounts();
        return ResponseEntity.ok(allAccounts);
    }

    // delete account REST API
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted!");
    }

    //build transfer REST API
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferFundDto transferFundDto) {
        accountService.transferFunds(transferFundDto);
        return ResponseEntity.ok("Funds transferred Successfully!");
    }

    // Get account transactions
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions(@PathVariable Long id) {
        List<TransactionDto> accountTransactions = accountService.getAccountTransactions(id);
        return ResponseEntity.ok(accountTransactions);
    }
}
