package pl.sda.intermediate11.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sda.intermediate11.bookstore.categories.CategoryDTO;
import pl.sda.intermediate11.bookstore.categories.CategorySearchService;
import pl.sda.intermediate11.bookstore.users.entities.CountryEnum;
import pl.sda.intermediate11.bookstore.users.dtos.UserLoginDTO;
import pl.sda.intermediate11.bookstore.users.dtos.UserRegistrationDTO;
import pl.sda.intermediate11.bookstore.users.services.UserLoginService;
import pl.sda.intermediate11.bookstore.users.services.UserRegistrationService;
import pl.sda.intermediate11.bookstore.users.services.UserValidationService;

import java.util.List;
import java.util.Map;

@Controller//singleton
public class OnlyOneController {

    //dependency injection-wstrzykiwanie zależności

    @Autowired//spring wstawi tutaj referencję
    private UserValidationService userValidationService;
    @Autowired
    private CategorySearchService categorySearchService;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserLoginService userLoginService;


    @RequestMapping("/")
    public String welcome() {
        return "index";
    }

    @GetMapping("/cats")
    public String categories(Map<String, Object> model, @RequestParam(required = false) String searchText) {
        List<CategoryDTO> categoryDTOS = categorySearchService.filterCategories(searchText);
        model.put("catsdata", categoryDTOS); //To zostanie wyslane na front
        return "catspage"; //takiego htmla bedzie szukac nasza aplikacja
    }

    @PostMapping(value = "/register") //POST - wysłanie danych
    public String registerEffect(@ModelAttribute UserRegistrationDTO userRegistrationDTO, Map<String, Object> model) {
        Map<String, String> errorsMap = userValidationService.validateUserData(userRegistrationDTO);
        model.put("countries", CountryEnum.values());
        model.put("form", userRegistrationDTO);
        if (errorsMap.isEmpty()) {
            userRegistrationService.registerUser(userRegistrationDTO);
            return "registerEffect";
        } else {
            model.putAll(errorsMap);
            return "registerForm";
        }
    }

    @GetMapping(value = "/register")
    public String registerForm(Map<String, Object> model) {
        model.put("form", new UserRegistrationDTO());
        model.put("countries", CountryEnum.values());
        return "registerForm";
    }

    @GetMapping(value = "/login")
    public String login(Map<String, Object> model) {
        model.put("form", new UserLoginDTO());
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginEffect(@ModelAttribute UserLoginDTO userLoginDTO, Map<String, Object> model) {
        userLoginService.login(userLoginDTO);
        return "index";
    }
}
