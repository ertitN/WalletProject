package com.example.walletservice.model;

import com.example.walletservice.model.enumeration.Currency;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;


@Entity
public class Wallet extends AbstractBaseEntity {
    private double balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDate creationDate;

    @JsonBackReference
    @ManyToOne
    private Customer customer;

    public Wallet() {
    }

    public Wallet(double balance, Currency currency, LocalDate creationDate, Customer customer) {
        this.balance = balance;
        this.currency = currency;
        this.creationDate = creationDate;
        this.customer = customer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance +
                ", currency=" + currency +
                ", creationDate=" + creationDate +
                '}';
    }
}
