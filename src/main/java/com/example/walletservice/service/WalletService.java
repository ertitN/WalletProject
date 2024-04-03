package com.example.walletservice.service;


import com.example.walletservice.dto.WalletDTO;
import com.example.walletservice.exception.BadRequestException;
import com.example.walletservice.exception.CustomerNotFoundException;
import com.example.walletservice.exception.WalletAlreadyExistsException;
import com.example.walletservice.mapper.WalletMapper;
import com.example.walletservice.model.Customer;
import com.example.walletservice.model.WalletServiceTransactionLogger;
import com.example.walletservice.model.enumeration.Currency;
import com.example.walletservice.model.enumeration.TransactionType;
import com.example.walletservice.util.ClientRequestInfo;
import com.example.walletservice.util.ErrorMessageConstants;
import com.example.walletservice.util.WalletValidator;
import com.example.walletservice.model.Wallet;
import com.example.walletservice.repository.CustomerRepository;
import com.example.walletservice.repository.TransactionLoggerRepository;
import com.example.walletservice.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    private WalletRepository walletRepository;
    private CustomerRepository customerRepository;

    private WalletMapper walletMapper;

    @Autowired
    private ClientRequestInfo clientRequestInfo;

    @Autowired
    private TransactionLoggerRepository loggerRepository;

    @Autowired
    public void setWalletRepository(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setWalletMapper(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    public Optional<Wallet> saveWallet(WalletDTO walletDTO) {

        this.validateRequest(walletDTO);
        if (walletRepository.selectExistsWalletBySameCurrency(walletDTO.getCurrency(), walletDTO.getCustomerId())) {
            throw new WalletAlreadyExistsException("Wallet with currency type: " + walletDTO.getCurrency().getCurrencyName()
                    + " is already exist");
        }
        Wallet wallet = walletMapper.mapFromWalletDTOtoWallet(walletDTO);


        return Optional.of(walletRepository.save(wallet));
    }

    private void validateRequest(WalletDTO walletDTO) {
        WalletValidator.validateWalletBalance(walletDTO.getBalance());

    }

    public Customer findCustomerById(long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id + " id numarasına" +
                "sahip müşteri bulunamadı!!"));

        return customer;
    }

    @Transactional
    public Optional<Wallet> deposit(long customerId, String currencyName, double amount) {
        if (amount < 0) {
            throw new BadRequestException(ErrorMessageConstants.BALANCE_IS_MINUS);
        }
        Optional<Wallet> wallet = getWallet(customerId, currencyName);;
        wallet.get().setBalance(wallet.get().getBalance() + amount);
        walletRepository.save(wallet.get());
        this.saveTransactionToDatabase(wallet.get(),amount, TransactionType.DEPOSIT);
        return wallet;
    }

    public Optional<Wallet> getWallet(long customerId, String currencyName) {

        Customer customer = this.findCustomerById(customerId);
        Optional<Wallet> wallet = customer.getWalletList().
                stream().filter(w -> w.getCurrency().equals(Currency.valueOf(currencyName))).findFirst();

        if (wallet.isEmpty()) {
            throw new BadRequestException("Customer : " + customer.getFirstName() + " " + customer.getLastName() + "" +
                    " doesn't have wallet with " + currencyName);
        }
            return wallet;

    }


    @Transactional
    public Optional<Wallet> withdraw(long customerId, String currencyName, double amount) {

        Optional<Wallet> wallet = this.getWallet(customerId,currencyName);


        if (amount>wallet.get().getBalance()) {
            throw new BadRequestException(ErrorMessageConstants.NO_ENOUGH_BALANCE +" "+amount +" " +
                    wallet.get().getCurrency().getCurrencySign());
        }
        else{
            wallet.get().setBalance(wallet.get().getBalance()-amount);
            walletRepository.save(wallet.get());
            this.saveTransactionToDatabase(wallet.get(),amount, TransactionType.WITHDRAW);
        }

        return wallet;
    }

    private void saveTransactionToDatabase(Wallet wallet, double amount, TransactionType transactionType){
        WalletServiceTransactionLogger transactionLogger = new WalletServiceTransactionLogger();
        transactionLogger.setCustomerId(wallet.getCustomer().getId());
        transactionLogger.setTransactionAmount(amount);
        transactionLogger.setTransactionType(transactionType);
        if (transactionType.equals(TransactionType.DEPOSIT)) {
            transactionLogger.setBalanceBefore(wallet.getBalance()-amount);
        }
        else {
            transactionLogger.setBalanceBefore(wallet.getBalance()+amount);
        }
        transactionLogger.setBalanceAfter(wallet.getBalance());
        transactionLogger.setTransactionCurrency(wallet.getCurrency());
        transactionLogger.setTransactionDateTime(LocalDate.now());
        transactionLogger.setClientIpAdress(clientRequestInfo.getClientIpAdress());
        transactionLogger.setClientUrl(clientRequestInfo.getClientUrl());
        transactionLogger.setSessionActivityId(clientRequestInfo.getSessionActivityId());
        this.loggerRepository.save(transactionLogger);



    }

    public Optional<List<Wallet>> getWallets(Long customerId){
       Customer customer = findCustomerById(customerId);
       List<Wallet> walletList = customer.getWalletList();
        if (!walletList.isEmpty()){
                return Optional.of(walletList);
        }
        return Optional.of(walletRepository.findAll());
    }

    public Optional<Wallet> deleteWallet(long customerId,String currencyName) {
        Customer customer = this.findCustomerById(customerId);
        Optional<Wallet> wallet = this.getWallet(customerId,currencyName);
        return wallet;
    }

    public Page<List<WalletServiceTransactionLogger>> getAllTransactionsByDate(String transactionDate,
                                                                               Integer pageNumber, Integer pageSize,
                                                                               Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        WalletValidator.validateTransactionDate(transactionDate,formatter);
        LocalDate transactionDateResult = LocalDate.parse(transactionDate,formatter);
        if (pageNumber!= null && pageSize!=null){
            pageable = PageRequest.of(pageNumber,pageSize,Sort.by("id").descending());
        }

        return this.loggerRepository.findAllTransactionByTransactionDate(transactionDateResult,pageable);

    }
}
