package pl.sda.intermediate11.bookstore.categories;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.*;

public class CategoryDAO {
    private CategoryDAO() {
    }

    private static CategoryDAO instance;
    private List<Category> categoriesCache;


    public static CategoryDAO getInstance() {
        if (instance == null) {
            synchronized (CategoryDAO.class) {
                if (instance == null) {
                    instance = new CategoryDAO();
                }
            }
        }
        return instance;

    }

    public Optional<Category> findCategoryById(Integer parentId) {
        return getCategories()
                .stream()
                .filter(c -> c.getId().equals(parentId)).findFirst();

    }

    private void populateParentId(int currentDepth, Map<Integer, List<Category>> categoryMap) {
        List<Category> categories = categoryMap.get(currentDepth);
        if (categories == null) {
            return;
        }
        for (Category category : categories) {
            category.setParentId(currentDepth == 0 ? null : matchParentID(currentDepth, categoryMap, category));
        }
        populateParentId(currentDepth + 1, categoryMap);
    }

    private Integer matchParentID(int currentDepth, Map<Integer, List<Category>> categoryMap, Category category) {
        List<Category> potentialParentCategories = categoryMap.get(currentDepth - 1);
        Integer idOfChildWaitingForPapa = category.getId();
        return potentialParentCategories.stream()
                .filter(n -> n.getId() < idOfChildWaitingForPapa)
                .map(n -> n.getId())
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse(null);
    }

    private int getDepth(Category category) {
        return category.getTitle().split("\\S")[0].length();
    }

    public List<Category> getCategories() {
        if (isNotEmpty(this.categoriesCache)) {
            return this.categoriesCache;
        }
        List<String> lines = readLinesFromFile();
        List<Category> categories = prepareCategoriesList(lines);

        Map<Integer, List<Category>> categoryMap = populateCategoriesMap(categories);
        populateParentId(0, categoryMap);
        this.categoriesCache = categoryMap.values().stream()
                .flatMap(n -> n.stream())
                .collect(Collectors.toList());
        return this.categoriesCache;
    }

    private List<Category> prepareCategoriesList(List<String> lines) {
        List<Category> categories = Lists.newArrayList();
        int counter = 1;
        for (String line : lines) {
            categories.add(new Category(counter++, line));
        }
        return categories;
    }

    private List<String> readLinesFromFile() {
        URI uri = null;
        try {
            uri = this.getClass().getClassLoader().getResource("kategorie2.txt")
                    .toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(uri), Charset.forName("UNICODE"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private Map<Integer, List<Category>> populateCategoriesMap(List<Category> categories) {
        Map<Integer, List<Category>> categoryMap = Maps.newHashMap();
        for (Category category : categories) {
            int depth = (category.getTitle().startsWith(" ") || category.getTitle().startsWith("\t"))
                    ? getDepth(category) : 0;
            if (categoryMap.containsKey(depth)) {
                categoryMap.get(depth).add(category);
            } else {
                categoryMap.put(depth, Lists.newArrayList(category));
            }
        }
        return categoryMap;
    }


}
