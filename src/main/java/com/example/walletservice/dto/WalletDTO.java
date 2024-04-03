package com.example.walletservice.dto;



import com.example.walletservice.model.enumeration.Currency;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;


public class WalletDTO {
    private long id;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private double balance;

    @NotNull(message = "Currency is mandotary")
    private Currency currency;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @NotNull(message = "Customer id is mandotary")
    private int customerId;

    public WalletDTO() {

    }

    public WalletDTO(long id, double balance, Currency currency, int customerId) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.customerId = customerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "WalletDTO{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency  +
                ", customer id =" + customerId +
                '}';
    }
}
