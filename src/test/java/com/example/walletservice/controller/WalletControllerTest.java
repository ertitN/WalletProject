package com.example.walletservice.controller;

import com.example.walletservice.dto.WalletDTO;
import com.example.walletservice.model.Customer;
import com.example.walletservice.model.Wallet;
import com.example.walletservice.model.enumeration.Currency;
import com.example.walletservice.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @Mock
    WalletService mockWalletService;

    @InjectMocks
    WalletController walletController;

    @Test
    void saveWallet() {
        //given
        Wallet wallet = new Wallet();
        Optional<Wallet> expected = Optional.of(wallet);
        when(mockWalletService.saveWallet(any())).thenReturn(expected);

        //when

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBalance(100.0);
        walletDTO.setCustomerId(2);
        walletDTO.setCurrency(Currency.TRY);
       Wallet actual =  this.walletController.saveWallet(walletDTO).getBody();
        //then

        assertEquals(actual,expected.get());
    }

    @Test
    void getAllWallet(){
        //given
        Wallet wallet1 = new Wallet();
        wallet1.setCurrency(Currency.TRY);
        Wallet wallet2 = new Wallet();
        wallet2.setCurrency(Currency.USD);
        List<Wallet> walletList = Arrays.asList(wallet1, wallet2);
        Optional<List<Wallet>> expected = Optional.of(walletList);

        when(mockWalletService.getWallets(anyLong())).thenReturn(expected);
        //when

        List<Wallet> actual = this.walletController.getWallets(1L).getBody();
        //then
        assertAll(
                () -> assertTrue(actual.size() == 2),
                () -> assertEquals(actual.get(0).getCurrency(), Currency.TRY),
                () -> assertEquals(actual.get(1).getCurrency(), Currency.USD)
        );



    }

    @Test
    void getWallet(){

        //given
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.TRY);
        Optional<Wallet> expected = Optional.of(wallet);
        when(mockWalletService.getWallet(anyLong(),eq("TRY"))).thenReturn(expected);


        //when
        Wallet actual = this.walletController.getWallet(1,"TRY").getBody();
        // then
        assertAll(
                ()-> assertNotNull(actual),
                ()-> assertEquals(actual.getCurrency(),Currency.TRY)
        );
    }

    @Test
    void depositToWallet(){

        // given
            Wallet expected = new Wallet();
            expected.setCurrency(Currency.TRY);
            expected.setBalance(500);
            Customer customer = new Customer();
            expected.setCustomer(customer);
        when(this.mockWalletService.deposit(anyLong(),eq("TRY"),anyDouble())).thenReturn(Optional.of(expected));


        // when
        Wallet actual = this.walletController.depositToWallet(2L,"TRY",200).getBody();

        //then

                assertEquals(500,actual.getBalance());


    }


    @Test
    void withdrawFromWallet(){
        //given
        Wallet expected = new Wallet();
        expected.setBalance(1000);

        when(mockWalletService.withdraw(anyLong(),eq("EUR"),anyDouble())).thenReturn(Optional.of(expected));

        //when

        Wallet actual = this.walletController.withdraw(2L,"EUR",300).getBody();

        //then
        assertAll(
                ()-> assertNotNull(actual),
                ()-> assertEquals(expected.getBalance(),actual.getBalance())
        );


    }



}