package com.example.walletservice.model;


import com.example.walletservice.model.enumeration.Currency;
import com.example.walletservice.model.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WalletServiceTransactionLogger{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private double balanceBefore;
    private double balanceAfter;
    private double transactionAmount;
    @Enumerated(EnumType.STRING)
    private Currency transactionCurrency;
    private LocalDate transactionDateTime;
    private String clientIpAdress;
    private String clientUrl;
    private String sessionActivityId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


}
