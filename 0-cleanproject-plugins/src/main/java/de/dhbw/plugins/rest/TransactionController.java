package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.adapter.transaction.TransactionDto;
import de.dhbw.cleanproject.adapter.transaction.TransactionDtoToTransactionMapper;
import de.dhbw.cleanproject.adapter.transaction.TransactionToTransactionResourceMapper;
import de.dhbw.cleanproject.domain.transaction.Transaction;
import de.dhbw.cleanproject.domain.transaction.TransactionApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
                transactionApplication.getAllTransactions(month, year).stream()
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
