package com.example.myfinances.models.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class AccountDto {
    private String name;
    private String description;
    private String icon;
    private String color;
    private boolean includeTotal;
    private Integer userId;
}
