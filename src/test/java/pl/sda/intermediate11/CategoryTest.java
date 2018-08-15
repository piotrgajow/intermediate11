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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
//            if (category.getTitle().startsWith(" "))
            int depth = category.getTitle().split("\\S")[0].length();
            if (categoryMap.containsKey(depth)){
                categoryMap.get(depth).add(category);
            }else { 
                categoryMap.put(depth,Lists.newArrayList(category));
            }
        }
        System.out.println();

    }
}