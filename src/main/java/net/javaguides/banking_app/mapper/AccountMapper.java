package net.javaguides.banking_app.mapper;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        Account acc = new Account (
                accountDto.id(),
                accountDto.AccountHolderName(),
                accountDto.balance()
        );
        return acc;
    }

    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accDto;
    }
}
