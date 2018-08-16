package pl.sda.intermediate11;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryDTO {

    private String id;
    private String text;
    private CategoryState categoryState;
    private String parentCategoryId;
    private CategoryDTO parentCat;


}
