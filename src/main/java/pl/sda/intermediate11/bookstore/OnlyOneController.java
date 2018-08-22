package pl.sda.intermediate11.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/register")
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
    public String registerForm(Map<String, Object> model){
        model.put("form", new UserRegistrationDTO());
        model.put("countries", CountryEnum.values());
        return "registerForm";
    }

}
