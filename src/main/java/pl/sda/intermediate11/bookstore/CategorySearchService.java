package pl.sda.intermediate11.bookstore;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategorySearchService {
    private CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public List<CategoryDTO> filterCategories(String searchText) {
        return categoryDAO.getCategories().stream()
                //.filter(cat -> cat.getTitle().equalsIgnoreCase(searchText.trim()))
                .map(c -> buildCategoryDTO(c))
                .peek(c -> c.setParentCat(findParent(c)))
                .peek(c -> setStateAndOpenParents(c, searchText))
                .collect(Collectors.toList());
    }

    private void setStateAndOpenParents(CategoryDTO categoryDTO, String searchText) {
        if (searchText != null && categoryDTO.getText().equalsIgnoreCase(searchText.trim())) {
//            categoryDTO.setCategoryState(new CategoryState());
            categoryDTO.getCategoryState().setSelected(true);
            categoryDTO.getCategoryState().setOpen(true);
            openParents(categoryDTO);
        }
    }

    private void openParents(CategoryDTO categoryDTO) {
        CategoryDTO parentCat = categoryDTO.getParentCat();
        if (parentCat == null) {
            return;
        }

        parentCat.getCategoryState().setOpen(true);
        openParents(parentCat);

    }

    private CategoryDTO findParent(CategoryDTO child) {
        if (child.getParentCategoryId() == null) {
            return null;
        }
        return categoryDAO.findCategoryById(Integer.valueOf(child.getParentCategoryId()))
                .map(c -> buildCategoryDTO(c))
                .orElse(null);

    }

    private CategoryDTO buildCategoryDTO(Category c) {
        return CategoryDTO.builder()
                .text(c.getTitle().trim())
                .id(c.getId().toString())
//                        .parentCategoryId(c.getParentId() == null ? null : c.getParentId()
//                                .toString())
                .parentCategoryId(Optional.ofNullable(c.getParentId()).map(e -> e.toString())
                        .orElse(null))
                .build();
    }
}
