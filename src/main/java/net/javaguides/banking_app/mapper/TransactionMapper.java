package net.javaguides.banking_app.mapper;

import net.javaguides.banking_app.dto.TransactionDto;
import net.javaguides.banking_app.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transDto = new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
        return transDto;
    }

}
