package com.example.myfinances.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String icon;
    private String color;
    private BigDecimal amount;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean includeTotal;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean mainAccount;
    @Column(updatable=false, nullable = false)
    private Integer userId;
}
