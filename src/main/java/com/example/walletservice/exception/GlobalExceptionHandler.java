package com.example.walletservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WalletAppErrorResponse> handleException(BadRequestException exception){
            WalletAppErrorResponse response = this.setWalletAppErrorResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
            return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler({WalletAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WalletAppErrorResponse> handleException(WalletAlreadyExistsException exception){
        WalletAppErrorResponse response = this.setWalletAppErrorResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<WalletAppErrorResponse> handleException(CustomerNotFoundException exception){
      WalletAppErrorResponse response = this.setWalletAppErrorResponse(HttpStatus.NOT_FOUND,exception.getMessage());
        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionDateTimeParseException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<WalletAppErrorResponse> handleException(TransactionDateTimeParseException exception){
        WalletAppErrorResponse response = this.setWalletAppErrorResponse(HttpStatus.NOT_ACCEPTABLE,exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
    }

    public WalletAppErrorResponse setWalletAppErrorResponse(HttpStatus httpStatus,String msg){
        WalletAppErrorResponse walletAppErrorResponse = new WalletAppErrorResponse();

        walletAppErrorResponse.setStatus(httpStatus.value());
        walletAppErrorResponse.setMessage(msg);
        walletAppErrorResponse.setTimeStamp(System.currentTimeMillis());

        return walletAppErrorResponse;

    }


}
