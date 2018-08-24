package pl.sda.intermediate11.bookstore.users.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = -644574092117683217L;

    private String firstName;
    private String lastName;
    private String zipCode;
    private String city;
    private String country;
    private String street;
    private String birthDate;
    private String pesel;
    private String email;
    private String password;
    private String phone;
    private boolean preferEmails;



}
