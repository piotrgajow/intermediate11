package pl.sda.intermediate11;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class OnlyOneController {
    private CategorySearchService categorySearchService;

    //dependency injection-wstrzykiwanie zależności
    public OnlyOneController() {
        this.categorySearchService = new CategorySearchService();
    }

    @GetMapping("/cats")
    public String categories(Map<String, Object> model, @RequestParam(required = false) String searchText) {
        List<CategoryDTO> categoryDTOS = categorySearchService.filterCategories(searchText);
        model.put("catsdata", categoryDTOS); //To zostanie wyslane na front
        return "catspage"; //takiego htmla bedzie szukac nasza aplikacja
    }

}
