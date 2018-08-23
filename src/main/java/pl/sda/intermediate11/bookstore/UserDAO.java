package pl.sda.intermediate11.bookstore;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserDAO {

    private List<User> users = new ArrayList<>();

    public Optional<User> findUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public void addUser(User user) {
        users.add(user);
    }
}
