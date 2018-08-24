package pl.sda.intermediate11.bookstore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.sda.intermediate11.bookstore.users.entities.UserAddress;
import pl.sda.intermediate11.bookstore.users.dtos.UserRegistrationDTO;
import pl.sda.intermediate11.bookstore.users.services.UserValidationService;

import java.util.Map;

class UserValidationServiceTest {

    @Test
    void shouldNotPassUserWithBlankFirstName() {
        UserRegistrationDTO userRegistrationDTO = populateValidUser();
        userRegistrationDTO.setFirstName(" ");

        UserValidationService userValidationService = new UserValidationService();
        Map<String, String> errorsMap = userValidationService.validateUserData(userRegistrationDTO);

        Assertions.assertTrue(errorsMap.containsKey(UserValidationService.FIRST_NAME_VAL_RES));
    }

    @Test
    void shouldNotPassUserWithTooShortFirstName() {
        UserRegistrationDTO userRegistrationDTO = populateValidUser();
        userRegistrationDTO.setFirstName("Bn   ");

        UserValidationService userValidationService = new UserValidationService();
        Map<String, String> errorsMap = userValidationService.validateUserData(userRegistrationDTO);

        Assertions.assertTrue(errorsMap.containsKey(UserValidationService.FIRST_NAME_VAL_RES));
    }



    private UserRegistrationDTO populateValidUser() {
        UserAddress userAddress = new UserAddress();
        userAddress.setCity("New York");
        userAddress.setCountry("USA");
        userAddress.setStreet("Polska");
        userAddress.setZipCode("99-876");

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setFirstName("Anna");
        userRegistrationDTO.setLastName("Nowak");
        userRegistrationDTO.setBirthDate("1991-12-24");
        userRegistrationDTO.setPesel("19911224098");
        userRegistrationDTO.setEmail("a.nowak@pg.com");
        userRegistrationDTO.setPhone("666-666-666");
        userRegistrationDTO.setPassword("Antychryst666");
        userRegistrationDTO.setUserAddress(userAddress);

        return userRegistrationDTO;
    }
}