package com.example.walletservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends AbstractBaseEntity{
    private String firstName;
    private String lastName;
    private long ssid;
    private String email;


    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Wallet> walletList = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, long ssid, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssid = ssid;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getSsid() {
        return ssid;
    }

    public void setSsid(long ssid) {
        this.ssid = ssid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Wallet> getWalletList() {
        return walletList;
    }

    public void setWalletList(List<Wallet> walletList) {
        this.walletList = walletList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssid=" + ssid +
                ", email='" + email + '\'' +
                '}';
    }
}
