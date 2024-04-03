package com.example.walletservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

public class CustomerDTO {
    private long id;

    @NotBlank(message = "First name is mandotary")
    private String firstName;

    @NotBlank(message = "Last name is mandotary")
    private String lastName;

    @NotNull(message = "ssid is mandotary")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long ssid;

    @NotBlank
    @Email(message = "e-mail format is wrong")
    private String email;

    public CustomerDTO() {

    }

    public CustomerDTO(long id, String firstName, String lastName, long ssid, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssid = ssid;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssid=" + ssid +
                ", email='" + email + '\'' +
                '}';
    }
}
