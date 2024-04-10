package de.krystianschmidt.cashtrack.adapter.transaction;

import de.krystianschmidt.cashtrack.adapter.category.CategoryDtoToCategoryMapper;
import de.krystianschmidt.cashtrack.domain.category.Category;
import de.krystianschmidt.cashtrack.domain.transaction.Transaction;
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
