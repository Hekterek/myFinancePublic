package com.example.myfinances.mappers;

import com.example.myfinances.models.Account;
import com.example.myfinances.models.dto.AccountDto;
import com.example.myfinances.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.security.Principal;

@RequiredArgsConstructor
public class AccountMapper {

    final UserService userService;

    public static Account accountDtoToEntity(AccountDto accountDto, Principal principal) {
        return Account.builder()
                .id(null)
                .name(accountDto.getName())
                .description(accountDto.getDescription())
                .icon(accountDto.getIcon())
                .color(accountDto.getColor())
                .amount(new BigDecimal(0.00))
                .includeTotal(accountDto.isIncludeTotal())
                .mainAccount(false)
                .build();
    }
}
