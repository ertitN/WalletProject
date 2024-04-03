package com.example.walletservice.mapper;
import com.example.walletservice.dto.CustomerDTO;
import com.example.walletservice.model.Customer;
import org.mapstruct.Mapper;


@Mapper
public interface CustomerMapper {

    Customer mapFromCustomerDTOtoCustomer(CustomerDTO customerDTO);
    CustomerDTO mapFromCustomertoCustomerDTO(Customer customer);
}
