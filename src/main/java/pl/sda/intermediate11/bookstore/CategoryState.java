package pl.sda.intermediate11.bookstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryState {

    private boolean open;
    private boolean selected;
    private boolean disabled;
}
