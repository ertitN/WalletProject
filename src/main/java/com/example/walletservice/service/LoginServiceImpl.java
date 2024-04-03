package com.example.walletservice.service;

import com.example.walletservice.dto.LoginDTO;
import com.example.walletservice.model.Login;
import com.example.walletservice.repository.LoginRepository;
import com.example.walletservice.security.CustomLoginDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        Login login = loginRepository.findByUsername(username);
        if (login == null) {
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new CustomLoginDetails(login);
    }

    public void save(LoginDTO loginDTO) {
        mapToDTO(this.loginRepository.save(
                new Login(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        ));
    }

    public static LoginDTO mapToDTO(Login login) {
        if (login != null) {
            return new LoginDTO(
                    login.getUsername(),
                    login.getPassword()
            );
        }
        return null;
    }
}