package com.example.walletservice.controller;


import com.example.walletservice.dto.WalletDTO;
import com.example.walletservice.model.Wallet;
import com.example.walletservice.model.WalletServiceTransactionLogger;
import com.example.walletservice.security.SecuredController;
import com.example.walletservice.service.WalletService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallet")
@SecurityRequirement(name = "bearerAuth")
public class WalletController  {


    @Autowired
    public WalletService walletService;


    @PostMapping("/save-wallet")
    public ResponseEntity<Wallet> saveWallet(@RequestBody @Valid WalletDTO walletDTO){

       Optional<Wallet> resultOptional =  walletService.saveWallet(walletDTO);

        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    @PutMapping("/deposit/{customerId}/{currency}/{amount}")
    public ResponseEntity<Wallet> depositToWallet(@PathVariable long customerId,
                                                  @PathVariable("currency") String currencyName,
                                                  @PathVariable double amount){

        Optional<Wallet> resultOptional =  walletService.deposit(customerId,currencyName,amount);


        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    @PutMapping("/withdraw/{customerId}/{currency}/{amount}")
    public ResponseEntity<Wallet> withdraw(@PathVariable long customerId,
                                                  @PathVariable("currency") String currencyName,
                                                  @PathVariable double amount){

        Optional<Wallet> resultOptional =  walletService.withdraw(customerId,currencyName,amount);

        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }


    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getWallets(@RequestParam(required = false) Long customerId){

        Optional<List<Wallet>> walletList = walletService.getWallets(customerId);
            return new ResponseEntity<>(walletList.get(), HttpStatus.OK);

    }

    @GetMapping("/get-wallet/{customerId}/{currency}")
    public ResponseEntity<Wallet> getWallet(@PathVariable long customerId, @PathVariable("currency") String currencyName){
        Optional<Wallet> resultOptional = this.walletService.getWallet(customerId, currencyName);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/get-transactions-by-date")
    public ResponseEntity<Page<List<WalletServiceTransactionLogger>>> getAllTransactionsByDate
            (
              @Parameter(description="transaction query for wallet usage", example = "21/03/2024",required = true)
              @RequestParam String transactionDate,
              @RequestParam(required = false) Integer pageNumber,
              @RequestParam(required = false) Integer pageSize,
              @RequestParam(required = false) @PageableDefault(page = 0,size = 10)Pageable pageable){

        return new ResponseEntity<>(this.walletService.getAllTransactionsByDate(transactionDate,pageNumber,pageSize,pageable),HttpStatus.OK);
    }






}
