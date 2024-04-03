package com.example.walletservice.repository;

import com.example.walletservice.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login,Long> {
    Login findByUsername(String username);
}
