package pl.sda.intermediate11.bookstore.products;

import lombok.Data;

@Data
public class Product {
    private String title;
    private Integer id;

    public Product(String title, Integer id) {
        this.title = title;
        this.id = id;
    }
}
