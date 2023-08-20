package com.example.myfinances.services;

import com.example.myfinances.models.Transaction;
import com.example.myfinances.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    final UserService userService;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Transaction saveNewTransaction(Transaction newTransaction, Principal principal) {
        var userId = userService.getLoggedUserId(principal);
        newTransaction.setUserId(userId);
        return transactionRepository.save(newTransaction);
    }

}
