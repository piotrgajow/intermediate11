package pl.sda.intermediate11.bookstore;

import lombok.Getter;
import lombok.Setter;

import javax.validation.GroupSequence;

@Getter
@Setter
public class UserAddress {

    private String zipCode;
    private String city;
    private String country;
    private String street;

}
