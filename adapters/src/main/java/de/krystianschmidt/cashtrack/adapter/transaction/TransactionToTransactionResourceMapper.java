package de.krystianschmidt.cashtrack.adapter.transaction;

import de.krystianschmidt.cashtrack.adapter.category.CategoryResource;
import de.krystianschmidt.cashtrack.adapter.category.CategoryToCategoryResourceMapper;
import de.krystianschmidt.cashtrack.domain.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TransactionToTransactionResourceMapper implements Function<Transaction, TransactionResource> {
    
    private final CategoryToCategoryResourceMapper categoryToCategoryResourceMapper;
    
        @Override
        public TransactionResource apply(Transaction transaction) {
            CategoryResource categoryResource = categoryToCategoryResourceMapper.apply(transaction.getCategory());

            return TransactionResource.builder()
                    .id(transaction.getId())
                    .description(transaction.getDescription())
                    .timestamp(transaction.getTimestamp())
                    .amount(transaction.getAmount())
                    .category(categoryResource)
                    .build();
        }
}
