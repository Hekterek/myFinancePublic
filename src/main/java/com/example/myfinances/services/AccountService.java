package com.example.myfinances.services;

import com.example.myfinances.mappers.AccountMapper;
import com.example.myfinances.models.Account;
import com.example.myfinances.models.dto.AccountDto;
import com.example.myfinances.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    final AccountRepository accountRepository;
    final UserService userService;

    public List<Account> findAllByUserId(Integer userId) {
        return accountRepository.findAllByUserId(userId);
    }

    public Optional<Account> getAccount(Integer id) {
        return accountRepository.findById(id);
    }

    public Account saveAccount(AccountDto accountDto, Principal principal) {
        Account newAccount = AccountMapper.accountDtoToEntity(accountDto, principal);
        newAccount.setUserId(userService.getLoggedUserId(principal));
        return accountRepository.save(newAccount);
    }
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

}







