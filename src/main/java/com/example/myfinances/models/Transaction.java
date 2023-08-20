package com.example.myfinances.models;

import com.example.myfinances.enums.transactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private transactionType type;
    @ManyToOne
    private Account from;
    @ManyToOne
    private Account to;
    private BigDecimal amount;
    @Column(columnDefinition = "date")
    private String date;
    @Column(updatable=false, nullable = false)
    private Integer userId;
}
