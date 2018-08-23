package pl.sda.intermediate11.bookstore;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {
    @Autowired
    private UserDAO userDAO;

    public void login(UserLoginDTO userLoginDTO){
        Optional<User> userByEmail = userDAO.findUserByEmail(userLoginDTO.getLogin());
        if (!userByEmail.isPresent()) {
            throw new UserNotExistsException("Nie ma takiego użytkownika " + userLoginDTO.getLogin());
        }
        if (!DigestUtils.sha512Hex(userLoginDTO.getPassword()).equals(userByEmail.get().getPassword())){
            throw new PasswordDoesNotMatchException("Hasło nie pasuje");
        }
    }
}
