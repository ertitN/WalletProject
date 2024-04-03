package com.example.walletservice.service;
import com.example.walletservice.dto.WalletDTO;
import com.example.walletservice.exception.BadRequestException;
import com.example.walletservice.exception.WalletAlreadyExistsException;
import com.example.walletservice.mapper.WalletMapper;
import com.example.walletservice.model.Customer;
import com.example.walletservice.model.Wallet;
import com.example.walletservice.model.WalletServiceTransactionLogger;
import com.example.walletservice.model.enumeration.Currency;
import com.example.walletservice.repository.CustomerRepository;
import com.example.walletservice.repository.TransactionLoggerRepository;
import com.example.walletservice.repository.WalletRepository;
import com.example.walletservice.util.ClientRequestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @Mock
    WalletRepository mockWalletRepository;
    @Mock
    WalletMapper walletMapper;

    @Mock
    CustomerRepository mockCustomerRepository;

    @Mock
    private ClientRequestInfo mockClientRequestInfo;
    @Mock
    private TransactionLoggerRepository mockTransactionalLogger;



    @InjectMocks
    WalletService walletService;

    @Test
    void saveWallet() {
        // given

        Wallet expected = new Wallet();
        expected.setCurrency(Currency.TRY);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("FName");
        customer.setLastName("SName");
        expected.setCustomer(customer);
      when(mockWalletRepository.selectExistsWalletBySameCurrency(any(),anyLong())).thenReturn(Boolean.FALSE);
      when(walletMapper.mapFromWalletDTOtoWallet(any())).thenReturn(expected);
      when(mockWalletRepository.save(any())).thenReturn(expected);

      // when

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setCustomerId(2);
        walletDTO.setCurrency(Currency.TRY);
        walletDTO.setBalance(0.0);
        Wallet actual = this.walletService.saveWallet(walletDTO).get();
// then
       assertAll(
               ()-> assertEquals(expected,actual)
       );




    }

    @Test
    void save_wallet_throws_WalletAlreadyExistsException(){
        //given
        Wallet expected = new Wallet();
        expected.setCurrency(Currency.TRY);
        Customer customer = new Customer();
        customer.setId(2L);
        expected.setCustomer(customer);
        when(mockWalletRepository.selectExistsWalletBySameCurrency(any(),anyLong())).thenReturn(Boolean.TRUE);

        //when
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setCustomerId(2);
        walletDTO.setCurrency(Currency.TRY);
        walletDTO.setBalance(0.0);
        Executable executable = () -> this.walletService.saveWallet(walletDTO).get();

        //then

        assertThrows(WalletAlreadyExistsException.class,executable);

    }

    // deposit test
    @Test
    void deposit_wallet(){
        // given
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.TRY);
        List<Wallet> walletList = Arrays.asList(wallet);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setWalletList(walletList);
        customer.setFirstName("FName");
        customer.setLastName("SName");
        wallet.setCustomer(customer);

        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(mockClientRequestInfo.getClientIpAdress()).thenReturn("test");
        when(mockClientRequestInfo.getClientUrl()).thenReturn("test");
        when(mockClientRequestInfo.getSessionActivityId()).thenReturn("test");
        when(mockWalletRepository.save(any())).thenReturn(wallet);
        WalletServiceTransactionLogger transactionLogger = new WalletServiceTransactionLogger();
        when(mockTransactionalLogger.save(any())).thenReturn(transactionLogger);

        //when

        Wallet actual = walletService.deposit(2L,"TRY",100).get();

        //then

        assertAll(
                ()-> assertNotNull(actual),
                ()-> assertEquals(100.0,actual.getBalance())
        );



    }

    @Test
    void deposit_wallet_throws_badRequestException(){

        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.EUR);
        List<Wallet> walletList = Arrays.asList(wallet);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setWalletList(walletList);
        wallet.setCustomer(customer);
        wallet.setBalance(100.0);


        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // when
        Executable executable = () -> this.walletService.deposit(2L, "TRY", 100.0).get();

        // then
        assertThrows(BadRequestException.class, executable);
    }

    @Test
    void withdraw_wallet(){

        // given
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.TRY);
        List<Wallet> walletList = Arrays.asList(wallet);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setWalletList(walletList);
        customer.setFirstName("FName");
        customer.setLastName("SName");
        wallet.setCustomer(customer);
        wallet.setBalance(100);

        //when

        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(mockWalletRepository.save(any())).thenReturn(wallet);
        WalletServiceTransactionLogger transactionLogger = new WalletServiceTransactionLogger();
        when(mockTransactionalLogger.save(any())).thenReturn(transactionLogger);
        //then

        Wallet actual = walletService.withdraw(2L,"TRY",20).get();


        assertAll(
                ()-> assertNotNull(actual),
                ()->assertEquals(80.0,actual.getBalance())
        );




    }



}