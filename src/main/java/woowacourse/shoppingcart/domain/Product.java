package woowacourse.shoppingcart.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Product {

    @Include
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }
}
