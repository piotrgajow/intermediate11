package pl.sda.intermediate11;

public class OnlyOneController {
    private CategorySearchService categorySearchService;

    //dependency injection-wstrzykiwanie zależności
    public OnlyOneController() {
        this.categorySearchService = new CategorySearchService();
    }

}
