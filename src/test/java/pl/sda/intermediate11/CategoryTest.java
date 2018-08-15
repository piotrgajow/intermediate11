package pl.sda.intermediate11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class CategoryTest {

    @Test
    void populateCategories() throws URISyntaxException, IOException {
        URI uri = this.getClass().getClassLoader().getResource("kategorie.txt")
                .toURI();
        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.forName("UNICODE"));
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
        populateInner(0, categoryMap);
    }

    private void populateInner(int currentDepth, Map<Integer, List<Category>> categoryMap) {
        List<Category> categories = categoryMap.get(currentDepth);
        for (Category category : categories) {
            category.setParentId(currentDepth == 0 ? null : matchParentID(currentDepth, categoryMap, category));
        }
        populateInner(currentDepth, categoryMap);
    }

    private Integer matchParentID(int currentDepth, Map<Integer, List<Category>> categoryMap, Category category) {
        List<Category> potentialParentCategories = categoryMap.get(currentDepth - 1);
        Integer idOfChildWaitingForPapa = category.getId();
        potentialParentCategories.stream()
                .filter(n -> n.getId() < idOfChildWaitingForPapa)
                .map(n -> n.getId())
                .sorted(Comparator.reverseOrder())
                .findFirst();
    }

    private int getDepth(Category category) {
        return category.getTitle().split("\\S")[0].length();
    }

}