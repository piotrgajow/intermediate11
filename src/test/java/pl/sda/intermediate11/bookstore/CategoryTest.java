package pl.sda.intermediate11.bookstore;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import pl.sda.intermediate11.bookstore.categories.Category;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

class CategoryTest {

    @Test
    void populateCategories() throws URISyntaxException, IOException {
//        Assertions.assertEquals(4, categoryMap.values().stream()
//                .flatMap(e -> e.stream())
//                .filter(n -> new Integer(6).equals(n.getId()))
//                .findFirst()
//                .map(p -> p.getParentId())
//                .orElse(-1)
//                .intValue()
//        );
    }

    @Test
    void treeSetExample() {
        TreeSet<Category> comparables = Sets.newTreeSet(Comparator.comparing(Category::getTitle)
                .thenComparing(Category::getId));

        comparables.add(new Category(1, "n"));
        comparables.add(new Category(2, "b"));
        comparables.add(new Category(3, "a"));
        comparables.add(new Category(6, "d"));
        comparables.add(new Category(4, "d"));
        System.out.println(comparables);
    }
}