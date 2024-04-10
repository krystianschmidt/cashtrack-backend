package de.krystianschmidt.cashtrack.plugins.rest;

import de.krystianschmidt.cashtrack.adapter.transaction.TransactionDto;
import de.krystianschmidt.cashtrack.adapter.transaction.TransactionDtoToTransactionMapper;
import de.krystianschmidt.cashtrack.adapter.transaction.TransactionToTransactionResourceMapper;
import de.krystianschmidt.cashtrack.domain.transaction.Transaction;
import de.krystianschmidt.cashtrack.domain.transaction.TransactionApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionApplication transactionApplication;
    private final TransactionDtoToTransactionMapper transactionDtoToTransactionMapper;
    private final TransactionToTransactionResourceMapper transactionToTransactionResourceMapper;

    @PostMapping
    ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto){
        Transaction newTransaction = transactionDtoToTransactionMapper.apply(transactionDto);
        transactionApplication.addTransaction(newTransaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<?> getAllTransactions(@RequestParam Integer month, @RequestParam Integer year){
        return ResponseEntity.ok(
                transactionApplication.getAllTransactions(YearMonth.of(year, month)).stream()
                        .map(transactionToTransactionResourceMapper)
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTransaction(@PathVariable("id") UUID transactionId){
        transactionApplication.removeTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
