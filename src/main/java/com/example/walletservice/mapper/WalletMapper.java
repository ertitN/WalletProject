package com.example.walletservice.mapper;


import com.example.walletservice.dto.WalletDTO;
import com.example.walletservice.model.Wallet;
import com.example.walletservice.service.WalletService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


@Mapper
public abstract class WalletMapper {
    protected WalletService walletService;

    @Autowired
    public void setWalletService(@Lazy WalletService walletService) {
        this.walletService = walletService;
    }

    @Mapping(target = "customer",expression = "java(walletService.findCustomerById(walletDTO.getCustomerId()))")
    @Mapping(target = "creationDate",expression = "java(java.time.LocalDate.now())")
    public abstract Wallet mapFromWalletDTOtoWallet(WalletDTO walletDTO);
   public abstract WalletDTO mapFromWalletToWalletDTO(Wallet wallet);


}
