package com.example.myfinances.restControllers;

import com.example.myfinances.models.Account;
import com.example.myfinances.models.dto.AccountDto;
import com.example.myfinances.services.AccountService;
import com.example.myfinances.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLOutput;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/account")
@RestController
public class AccountRestController {

    final AccountService accountService;
    final UserService userService;

    @GetMapping
    public ResponseEntity<List<Account>>  getAll( Principal principal) {
        return ResponseEntity.ok(accountService.findAllByUserId(userService.getLoggedUserId(principal)));
    }

    @PostMapping
    public ResponseEntity<Account> saveAccount(@RequestBody AccountDto accountDto, Principal principal) {
        var newAccount = accountService.saveAccount(accountDto, principal);
        return ResponseEntity.ok(newAccount);
    }

    @PutMapping ("update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, Principal principal) {
        var userId = userService.getLoggedUserId(principal);
        if(userId.equals(account.getUserId())) {
            return ResponseEntity.ok(accountService.updateAccount(account));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id, Principal principal) {
        var userId = userService.getLoggedUserId(principal);
        var optionalAccount = accountService.getAccount(id);

        if(optionalAccount.isPresent() && optionalAccount.get().getUserId().equals(userId)) {
            accountService.deleteAccount(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
