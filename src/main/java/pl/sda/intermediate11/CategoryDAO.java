package pl.sda.intermediate11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryDAO {
    private CategoryDAO() {
    }

    private static CategoryDAO instance;


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
    private void populateParentId(int currentDepth, Map<Integer, List<Category>> categoryMap) {
        List<Category> categories = categoryMap.get(currentDepth);
        if (categories == null) {
            return;
        }
        for (Category category : categories) {
            category.setParentId(currentDepth == 0 ? null : matchParentID(currentDepth, categoryMap, category));
        }
        populateParentId(currentDepth+1, categoryMap);
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
    public List<Category> getCategories(){
        URI uri = null;
        try {
            uri = this.getClass().getClassLoader().getResource("kategorie.txt")
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
        List<Category> categories = Lists.newArrayList();
        int counter = 1;
        for (String line : lines) {
            categories.add(new Category(counter++, line));
        }

        Map<Integer, List<Category>> categoryMap = Maps.newHashMap();
        for (Category category : categories) {
            int depth = category.getTitle().startsWith(" ") ? getDepth(category) : 0;
            if (categoryMap.containsKey(depth)) {
                categoryMap.get(depth).add(category);
            } else {
                categoryMap.put(depth, Lists.newArrayList(category));
            }
        }
        populateParentId(0, categoryMap);
        return categoryMap.values().stream()
                .flatMap(n->n.stream())
                .collect(Collectors.toList());
    }
}
