package com.example.walletservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Login extends AbstractBaseEntity {
    @Column(name = "user_name", nullable = false,unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;
 /*
    @ManyToMany(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    private Set<LoginRole> roles = new HashSet<>();

  */

  ////  public Login(String username, String password) {
    //    this.username = username;
    //    this.password = password;
    //   roles.add(new LoginRole("USER"));
   // }
}
