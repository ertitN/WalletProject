package com.example.walletservice.controller;

import com.example.walletservice.dto.JwtResponseDTO;
import com.example.walletservice.dto.LoginDTO;
import com.example.walletservice.security.JwtService;
import com.example.walletservice.security.SecuredController;
import com.example.walletservice.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private JwtService jwtService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(loginDTO.getUsername())).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody LoginDTO loginDTO) {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        loginDTO.setPassword(bCryptPasswordEncoder.encode(loginDTO.getPassword()));
        loginService.save(loginDTO);
    }
}
