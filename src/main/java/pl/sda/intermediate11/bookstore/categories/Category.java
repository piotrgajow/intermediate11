package pl.sda.intermediate11.bookstore.categories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private Integer id;
    private String title;
    private Integer parentId;


    public Category(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
