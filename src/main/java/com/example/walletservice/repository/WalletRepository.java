package com.example.walletservice.repository;

import com.example.walletservice.model.Wallet;
import com.example.walletservice.model.enumeration.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Query("SELECT CASE WHEN COUNT(*) > 0  THEN TRUE " +
            " ELSE FALSE END FROM Wallet AS w WHERE w.currency=?1 AND w.customer.id=?2")
    boolean selectExistsWalletBySameCurrency(Currency currency, long customerId);

    Wallet deleteWalletByIdAndCurrency(int id,String currency);



}
