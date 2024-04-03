package com.example.walletservice.controller;

import com.example.walletservice.dto.CustomerDTO;
import com.example.walletservice.model.Customer;
import com.example.walletservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
     CustomerService mockCustomerService;

    @InjectMocks
    CustomerController customerController;


    @Test
    void saveCustomer() {
        //given

        Customer customer = new Customer();
        customer.setSsid(111111L);
        Optional<Customer> expected = Optional.of(customer);
        when(mockCustomerService.saveCustomer(Mockito.any())).thenReturn(expected);

        //when
        CustomerDTO dto = new CustomerDTO();
        Customer actual = this.customerController.saveCustomer(dto).getBody();

        //then

        assertAll(
                () -> assertNotNull(actual),
                ()-> assertEquals(expected.get(),actual),
                ()->assertEquals(customer.getSsid(),actual.getSsid())
        );

}
    void saveCustomer2() {
        //given

        Customer customer = new Customer();
        customer.setSsid(111111L);
        Optional<Customer> expected = Optional.of(customer);
        when(mockCustomerService.saveCustomer(Mockito.any())).thenReturn(expected);

        //when
        CustomerDTO dto = new CustomerDTO();
        Customer actual = this.customerController.saveCustomer(dto).getBody();

        //then

        assertAll(
                () -> assertNotNull(actual),
                ()-> assertEquals(expected.get(),actual),
                ()->assertEquals(customer.getSsid(),actual.getSsid())
        );

    }
}