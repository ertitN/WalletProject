package com.example.walletservice.service;

import com.example.walletservice.dto.CustomerDTO;
import com.example.walletservice.exception.BadRequestException;
import com.example.walletservice.mapper.CustomerMapper;
import com.example.walletservice.model.Customer;
import com.example.walletservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository mockRepo;

    @Mock
    CustomerMapper mockCustomerMapper;

    @InjectMocks
    CustomerService customerService;

    @Test
    void saveCustomer(){
        Customer customer = new Customer();

        //given
        when(mockRepo.selectExistSSid(Mockito.anyLong())).thenReturn(Boolean.FALSE);
        when(mockCustomerMapper.mapFromCustomerDTOtoCustomer(any())).thenReturn(customer);
        when(mockRepo.save(any())).thenReturn(customer);

        //when
            CustomerDTO customerDTO = new CustomerDTO();
        Customer actual = this.customerService.saveCustomer(customerDTO).get();


        //then

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(customer,actual),
                () -> assertEquals(customer.getSsid(),actual.getSsid())
        );
    }
    @Test
    void saveCustomer2(){
        Customer customer = new Customer();

        //given
        when(mockRepo.selectExistSSid(Mockito.anyLong())).thenReturn(Boolean.TRUE);

        //when
            CustomerDTO customerDTO = new CustomerDTO();
        Executable executable = ()-> this.customerService.saveCustomer(customerDTO).get();

        //then

        assertThrows(BadRequestException.class,executable);
    }
}