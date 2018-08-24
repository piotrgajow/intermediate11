package pl.sda.intermediate11.bookstore.users.services;

import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.users.dtos.UserLoggedInDto;

@Service
public class UserContextHolder {

    private static UserLoggedInDto userLoggedInDto;

    protected static void logInUser(String email) {
        userLoggedInDto = new UserLoggedInDto(email);
    }

    public String getUserLoggedIn() {
        if (userLoggedInDto == null) {
            return null;
        }else {
            return userLoggedInDto.getLogin();
        }
    }


}
