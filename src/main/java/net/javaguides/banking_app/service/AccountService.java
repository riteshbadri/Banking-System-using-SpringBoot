package net.javaguides.banking_app.service;
import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.dto.TransactionDto;
import net.javaguides.banking_app.dto.TransferFundDto;
import net.javaguides.banking_app.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountService {

    AccountDto createAccount(AccountDto accDto);
    AccountDto getAccountById(Long id);
    AccountDto Deposit(Long id, double amount);
    AccountDto Withdraw(Long id, double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
    void transferFunds(TransferFundDto transferFundDto);
    List<TransactionDto> getAccountTransactions(Long id);
}
