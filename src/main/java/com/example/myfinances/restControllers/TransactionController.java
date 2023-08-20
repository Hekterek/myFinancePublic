package com.example.myfinances.restControllers;

import com.example.myfinances.models.Transaction;
import com.example.myfinances.services.TransactionService;
import com.example.myfinances.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/transaction")
@RestController
public class TransactionController {

    final TransactionService transactionService;


    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(Principal principal, @RequestBody Transaction newTransaction) {
        return ResponseEntity.ok(transactionService.saveNewTransaction(newTransaction, principal));
    }

}
