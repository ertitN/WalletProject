package com.example.walletservice.controller;

import com.example.walletservice.dto.CustomerDTO;
import com.example.walletservice.security.SecuredController;
import com.example.walletservice.util.ClientRequestInfo;
import com.example.walletservice.model.Customer;
import com.example.walletservice.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {
    private final CustomerService customerService;
    private final ClientRequestInfo clientRequestInfo;




    
    @PostMapping("/save-customer")
    public ResponseEntity<Customer>  saveCustomer(@RequestBody @Valid CustomerDTO customer){
       log.info(String.valueOf(clientRequestInfo));
        Optional<Customer> resultOptional =  customerService.saveCustomer(customer);
        if (resultOptional.isPresent()){
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
