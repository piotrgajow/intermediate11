package pl.sda.intermediate11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class CategoryTest {

    @Test
    void populateCategories() throws URISyntaxException, IOException {

        Assertions.assertEquals(4, categoryMap.values().stream()
                .flatMap(e -> e.stream())
                .filter(n -> new Integer(6).equals(n.getId()))
                .findFirst()
                .map(p -> p.getParentId())
                .orElse(-1)
                .intValue()
        );


    }







}