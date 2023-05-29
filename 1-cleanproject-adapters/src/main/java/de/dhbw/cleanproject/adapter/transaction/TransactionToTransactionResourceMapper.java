package de.dhbw.cleanproject.adapter.transaction;

import de.dhbw.cleanproject.adapter.category.CategoryResource;
import de.dhbw.cleanproject.adapter.category.CategoryToCategoryResourceMapper;
import de.dhbw.cleanproject.domain.transaction.Transaction;
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
