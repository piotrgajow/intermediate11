package pl.sda.intermediate11.bookstore.categories;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryDTO {

    private String id;
    private String text;
    private CategoryState state;
    private String parentCategoryId;
    private CategoryDTO parentCat;

    public String getParent() {
        return parentCategoryId == null ? "#" : parentCategoryId;
    }


}
