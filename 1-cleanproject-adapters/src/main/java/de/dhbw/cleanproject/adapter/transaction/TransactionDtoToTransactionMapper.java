package de.dhbw.cleanproject.adapter.transaction;

import de.dhbw.cleanproject.adapter.category.CategoryDtoToCategoryMapper;
import de.dhbw.cleanproject.domain.category.Category;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TransactionDtoToTransactionMapper implements Function<TransactionDto, Transaction> {

    private final CategoryDtoToCategoryMapper categoryDtoToCategoryMapper;

    @Override
    public Transaction apply(TransactionDto transactionDto) {
        Category category = categoryDtoToCategoryMapper.apply(transactionDto.getCategory());
        return Transaction.builder()
                .amount(transactionDto.getAmount())
                .category(category)
                .description(transactionDto.getDescription())
                .timestamp(transactionDto.getTimestamp())
                .build();
    }
}
