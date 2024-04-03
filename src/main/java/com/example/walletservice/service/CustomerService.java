package com.example.walletservice.service;


import com.example.walletservice.dto.CustomerDTO;
import com.example.walletservice.exception.BadRequestException;
import com.example.walletservice.mapper.CustomerMapper;
import com.example.walletservice.model.Customer;
import com.example.walletservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService{
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    public CustomerMapper customerMapper;

    public Optional<Customer> saveCustomer(CustomerDTO customerDTO){
        boolean isExist = customerRepository.selectExistSSid(customerDTO.getSsid());
        if (isExist){
            throw new BadRequestException("Customer with SSid "+customerDTO.getSsid()+" is already exist.");
                    }

        // customerdto to cusstomer
       Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);



        return Optional.of(customerRepository.save(customer));

    }
}
