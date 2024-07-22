package net.javaguides.banking_app.service.impl;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.dto.TransactionDto;
import net.javaguides.banking_app.dto.TransferFundDto;
import net.javaguides.banking_app.entity.Account;
import net.javaguides.banking_app.entity.Transaction;
import net.javaguides.banking_app.exception.AccountException;
import net.javaguides.banking_app.mapper.AccountMapper;
import net.javaguides.banking_app.mapper.TransactionMapper;
import net.javaguides.banking_app.repository.AccountRepository;
import net.javaguides.banking_app.repository.TransactionRepository;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
    private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accDto) {
        Account account = AccountMapper.mapToAccount(accDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto Deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Invalid Account ID!"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId()); // we can use id instead of account.getId because both same
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto Withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Invalid Account!"));
        if(amount>account.getBalance()) {
            throw new AccountException("Insufficient Balance!");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> allAccounts = accountRepository.findAll();
        return allAccounts.stream().map(AccountMapper::mapToAccountDto).toList();
    }

    @Override
    public void deleteAccount(Long id) {
        Account acc = accountRepository.findById(id).orElseThrow(() -> new AccountException("Invalid Account!"));
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFunds(TransferFundDto transferFundDto) {
        //retrieve the from and to accounts from accountRepository
        Account fromAccount = accountRepository
                .findById(transferFundDto.fromAccountId())
                .orElseThrow(() -> new AccountException("Invalid Account!"));
        if(fromAccount.getBalance() < transferFundDto.amount()) {
            throw new AccountException("Insufficient Balance!");
        }

        Account toAccount = accountRepository
                .findById(transferFundDto.toAccountId())
                .orElseThrow(()-> new AccountException("Invalid Account!"));

        //debit/take amount from fromAccount
        fromAccount.setBalance(fromAccount.getBalance() - transferFundDto.amount());
        //credit/give amount to ToAccount
        toAccount.setBalance(toAccount.getBalance() + transferFundDto.amount());
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAccountId(transferFundDto.fromAccountId());
        transaction.setAmount(transferFundDto.amount());
        transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long id) {
        List<Transaction> accountTransactions = transactionRepository.findByAccountIdOrderByTimestampDesc(id);
        return accountTransactions.stream().map(TransactionMapper::mapToTransactionDto).toList();
    }
}
